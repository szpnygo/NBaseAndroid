package info.smemo.nbase.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import info.smemo.nbase.app.AppConstant;
import info.smemo.nbase.base.NBaseApplication;
import info.smemo.nbase.util.LogHelper;
import info.smemo.nbase.util.StringUtil;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by neo on 16/6/17.
 */
public class HttpUtil implements AppConstant {

    public static OkHttpClient client = getClient();

    public static MediaType MEDIA_TYPE = MediaType.parse("multipart/form-data");

    public enum HttpType {
        POST,
        GET,
    }

    public enum ThreadType {
        IO,
        THREAD,
        MAIN
    }

    private static OkHttpClient getClient() {
        if (client != null) {
            return client;
        }
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.cookieJar(NCookieManager.getInstance().getCookieJar());
        if (NBaseApplication.getContext() != null)
            builder.cache(new Cache(NBaseApplication.getContext().getCacheDir(), MAX_HTTP_CACHE_SIZE));
        builder.connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(HTTP_READ_TIMEOUR, TimeUnit.SECONDS);
        return builder.build();
    }

    public static void request(HttpBuilder builder) {
        request(builder.httpType, builder.url, builder.postMap, builder.getMap, builder.headerMap,
                builder.isCookie, builder.cacheControl, builder.returnThread, builder.httpDataAction == null ? defaultGetData : builder.httpDataAction,
                builder.listener);
    }

    /**
     * 网络请求
     *
     * @param type         类型
     * @param url          地址
     * @param postMap      post数据
     * @param getMap       get数据
     * @param headerMap    header数据
     * @param isCookie     是否cookie
     * @param cacheControl 缓存管理
     * @param returnThread 回调线程 主线程
     * @param listener     回调
     */
    public static void request(final @NonNull HttpType type, final @NonNull String url, final @Nullable HashMap<String, Object> postMap,
                               final @Nullable HashMap<String, String> getMap, final @Nullable HashMap<String, String> headerMap,
                               final boolean isCookie, final @Nullable CacheControl cacheControl,
                               final @NonNull ThreadType returnThread,
                               final @NonNull HttpDataAction httpDataAction,
                               final @NonNull HttpDataListener listener) {
        request(type, url, postMap, getMap, headerMap, isCookie, cacheControl, returnThread, new HttpResponseListener() {
            @Override
            public void success(@NonNull final String response) {
                if (StringUtil.isEmpty(response)) {
                    listener.error(ERROR_DATA_EMPTY_ERROR, "没有返回内容");
                    return;
                }
                httpDataAction.getData(response, listener);
            }

            @Override
            public void failure(String message) {
                listener.error(ERROR_NETWORK_ERROR, message);
            }
        });
    }


    /**
     * 网络请求
     *
     * @param type         请求类型
     * @param url          请求地址
     * @param postMap      post数据
     * @param getMap       get数据
     * @param headerMap    header数据
     * @param isCookie     是否cookie
     * @param cacheControl 缓存管理
     * @param returnThread 回调线程 主线程
     * @param listener     回调
     */
    public static void request(final @NonNull HttpType type, final @NonNull String url, final @Nullable HashMap<String, Object> postMap,
                               final @Nullable HashMap<String, String> getMap, final @Nullable HashMap<String, String> headerMap,
                               final boolean isCookie, final @Nullable CacheControl cacheControl,
                               final @NonNull ThreadType returnThread,
                               final @NonNull HttpResponseListener listener) {
        Observable.create(new Observable.OnSubscribe<Request>() {
            @Override
            public void call(Subscriber<? super Request> subscriber) {
                HttpUrl httpUrl = HttpUrl.parse(url);
                if (null == httpUrl) {
                    throw Exceptions.propagate(new Throwable("HttpUrl[" + url + "] has error"));
                }
                LogHelper.i(TAG_HTTP, "Http Request url:" + url);
                Request request = getRequest(type, httpUrl, postMap, getMap, headerMap, isCookie, cacheControl);
                subscriber.onNext(request);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<Request, String>() {
                    @Override
                    public String call(Request request) {
                        Response response;
                        try {
                            response = client.newCall(request).execute();
                            if (response == null) {
                                throw Exceptions.propagate(new Throwable("response is null"));
                            }
                            if (!response.isSuccessful()) {
                                throw Exceptions.propagate(new Throwable("response is not successful code :" + response.code()));
                            }
                            return response.body().string();
                        } catch (IOException e) {
                            throw Exceptions.propagate(e);
                        }

                    }
                })
                .observeOn(getThread(ThreadType.MAIN))
                .onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.failure(throwable.getMessage());
                        return "NBaseAndroidError:" + throwable.getMessage();
                    }
                })
                .observeOn(getThread(returnThread))
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String response) {
                        if (!response.startsWith("NBaseAndroidError:")) {
                            LogHelper.i(TAG_HTTP, "Http Request[" + url + "] Response:" + response);
                            listener.success(response);
                        }
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throw Exceptions.propagate(throwable);
                    }
                })
                .subscribe();
    }

    /**
     * 生成request
     *
     * @param type         请求类型
     * @param httpUrl      请求地址
     * @param postMap      post提示教书
     * @param getMap       get提交数据
     * @param headerMap    header数据
     * @param isCookie     是否使用cookie
     * @param cacheControl 缓存控制
     * @return
     */
    private static Request getRequest(@NonNull HttpType type, @NonNull HttpUrl httpUrl, @Nullable HashMap<String, Object> postMap,
                                      @Nullable HashMap<String, String> getMap, @Nullable HashMap<String, String> headerMap,
                                      @NonNull boolean isCookie, @Nullable CacheControl cacheControl) {
        //control cookie
        if (!isCookie)
            NCookieManager.getInstance().deleteCookie(httpUrl);

        HttpUrl.Builder httpBuilder = httpUrl.newBuilder();

        //add query parameter
        if (null != getMap) {
            Set<Map.Entry<String, String>> entrySet = getMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                httpBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        Request.Builder builder = new Request.Builder().url(httpUrl);
        //add header
        if (null != headerMap) {
            Set<Map.Entry<String, String>> entrySet = headerMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        //add post
        if (type == HttpType.POST) {
            builder.post(createRequestBody(postMap));
        }

        if (null != cacheControl)
            builder.cacheControl(cacheControl);

        return builder.build();
    }

    /**
     * 默认数据解析
     */
    public static HttpUtil.HttpDataAction defaultGetData = new HttpUtil.HttpDataAction() {

        @Override
        public void getData(String response, HttpUtil.HttpDataListener listener) {
            try {
                JSONObject object = new JSONObject(response);
                int code = object.getInt("code");
                if (code == 0) {
                    listener.success(response);
                } else {
                    listener.error(code, object.getString("message"));
                }
            } catch (JSONException e) {
                listener.error(ERROR_DATA_ERROR, e.getMessage());
            }
        }
    };


    /**
     * 返回线程类型
     *
     * @param type
     * @return
     */
    private static Scheduler getThread(ThreadType type) {
        switch (type) {
            case IO:
                return Schedulers.io();
            case THREAD:
                return Schedulers.newThread();
            case MAIN:
                return AndroidSchedulers.mainThread();
            default:
                return AndroidSchedulers.mainThread();
        }
    }

    /**
     * 生成请求数据体
     *
     * @param map
     * @return
     */
    public static RequestBody createRequestBody(@Nullable HashMap<String, Object> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        if (null != map) {
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                Object object = entry.getValue();
                if (object instanceof String) {
                    builder.addFormDataPart(entry.getKey(), (String) object);
                } else if (object instanceof File) {
                    builder.addFormDataPart(entry.getKey(), entry.getKey(), RequestBody.create(MEDIA_TYPE, (File) object));
                } else if (object instanceof Integer || object instanceof Long || object instanceof Double || object instanceof Float) {
                    builder.addFormDataPart(entry.getKey(), String.valueOf(object));
                }
            }
        }
        return builder.build();
    }


    public static HttpBuilder newBuilder() {
        return new HttpBuilder();
    }

    public interface HttpResponseListener extends BaseHttpListener {

        void success(@NonNull String response);

        void failure(String message);

    }

    public interface HttpDataAction {

        void getData(String response, HttpDataListener listener);

    }

    public interface HttpDataListener {

        void success(String response);

        void error(int code, String message);

    }

    public interface BaseHttpListener {

    }

}
