package info.smemo.nbase.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
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
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by neo on 16/6/8.
 */
public class HttpUtil implements AppConstant {

    public static OkHttpClient client = getClient();

    public static MediaType MEDIA_TYPE = MediaType.parse("multipart/form-data");

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

    /**
     * GET请求
     *
     * @param url      请求地址
     * @param map      数据
     * @param isCookie 是否开启Cookie
     * @param listener 回调
     */
    public static void get(@NonNull final String url, @Nullable final HashMap<String, String> map, @NonNull final boolean isCookie, @Nullable final CacheControl cacheControl, @NonNull final HttpResponseListener listener) {
        Observable.create(new Observable.OnSubscribe<Request>() {

            @Override
            public void call(Subscriber<? super Request> subscriber) {
                //url can't be empty
                if (StringUtil.isEmpty(url)) {
                    listener.failure("Http url is empty");
                    return;
                }
                //get http url and check
                HttpUrl requestUrl = HttpUrl.parse(url);
                if (null == requestUrl) {
                    listener.failure("HttpUrl[" + url + "] has error");
                    return;
                }
                LogHelper.i(TAG_HTTP, "network:Get Http Request Url:" + url);

                //make http url query parameter
                HttpUrl.Builder builder = requestUrl.newBuilder();
                if (null != map) {
                    Set<Map.Entry<String, String>> entrySet = map.entrySet();
                    for (Map.Entry<String, String> entry : entrySet) {
                        builder.addQueryParameter(entry.getKey(), entry.getValue());
                    }
                }

                HttpUrl httpUrl = builder.build();
                Request.Builder requestBuilder = new Request.Builder().url(httpUrl);

                //make cache control
                if (null != cacheControl)
                    requestBuilder.cacheControl(cacheControl);
                Request request = requestBuilder.build();

                //make request cookie
                if (!isCookie) {
                    NCookieManager.getInstance().deleteCookie(httpUrl);
                }
                subscriber.onNext(request);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<Request, Response>() {
                    @Override
                    public Response call(Request request) {
                        Response response;
                        try {
                            response = client.newCall(request).execute();
                            return response;
                        } catch (Exception e) {
                            throw Exceptions.propagate(e);
                        }
                    }
                })
                .onErrorReturn(new Func1<Throwable, Response>() {

                    @Override
                    public Response call(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.failure(throwable.getMessage());
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Response>() {

                    @Override
                    public void call(Response response) {
                        if (response != null)
                            listener.success(response);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        LogHelper.i(TAG_HTTP, "Http Get Request[" + url + "] Completed");
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.failure(throwable.getMessage());
                    }
                })
                .subscribe();
    }

    /**
     * POST 请求
     *
     * @param url          请求地址
     * @param map          数据
     * @param headers      header头
     * @param isCookie     是否开启cookie
     * @param cacheControl 缓存控制
     * @param listener     监听
     */
    public static void post(@NonNull final String url, @Nullable final HashMap<String, Object> map, @Nullable final HashMap<String, String> headers, @NonNull final boolean isCookie, @Nullable final CacheControl cacheControl, @NonNull final HttpResponseListener listener) {
        Observable.create(new Observable.OnSubscribe<Request>() {

            @Override
            public void call(Subscriber<? super Request> subscriber) {
                //url can't be empty
                if (StringUtil.isEmpty(url)) {
                    listener.failure("Http url is empty");
                    return;
                }
                //get http url and check
                HttpUrl requestUrl = HttpUrl.parse(url);
                if (null == requestUrl) {
                    listener.failure("HttpUrl[" + url + "] has error");
                    return;
                }
                LogHelper.i(TAG_HTTP, "network:Post Http Request Url:" + url);

                if (!isCookie) {
                    NCookieManager.getInstance().deleteCookie(HttpUrl.parse(url));
                }

                Request.Builder builder = new Request.Builder();
                builder.url(requestUrl).post(createRequestBody(map));

                //make post header
                if (null != headers) {
                    Set<Map.Entry<String, String>> entrySet = headers.entrySet();
                    for (Map.Entry<String, String> entry : entrySet) {
                        builder.addHeader(entry.getKey(), entry.getValue());
                    }
                }
                if (null != cacheControl)
                    builder.cacheControl(cacheControl);

                subscriber.onNext(builder.build());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<Request, Response>() {
                    @Override
                    public Response call(Request request) {
                        //do request
                        Response response;
                        try {
                            response = client.newCall(request).execute();
                            return response;
                        } catch (Exception e) {
                            throw Exceptions.propagate(e);
                        }
                    }
                })
                .onErrorReturn(new Func1<Throwable, Response>() {

                    @Override
                    public Response call(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.failure(throwable.getMessage());
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Response>() {

                    @Override
                    public void call(Response response) {
                        if (response != null)
                            listener.success(response);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        LogHelper.i(TAG_HTTP, "Http Post Request[" + url + "] Completed");
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.failure(throwable.getMessage());
                    }
                })
                .subscribe();
    }

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
                }
            }
        }
        return builder.build();
    }

    public interface HttpResponseListener {

        void success(@NonNull Response response);

        void failure(String message);

    }

}
