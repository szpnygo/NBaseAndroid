package info.smemo.nbase.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import info.smemo.nbase.app.AppConstant;
import info.smemo.nbase.util.LogHelper;
import info.smemo.nbase.util.StringUtil;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

    private static OkHttpClient getClient() {
        if (client != null) {
            return client;
        }
        return new OkHttpClient.Builder()
                .cookieJar(NCookieManager.getInstance().getCookieJar())
                .build();
    }

    /**
     * GET请求
     *
     * @param url      请求地址
     * @param map      数据
     * @param isCookie 是否开启Cookie
     * @param listener 回调
     */
    public static void get(@NonNull final String url, @Nullable final HashMap<String, String> map, @NonNull final boolean isCookie, @NonNull final HttpResponseListener listener) {
        Observable.create(new Observable.OnSubscribe<HttpUrl>() {

            @Override
            public void call(Subscriber<? super HttpUrl> subscriber) {
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

                if (!isCookie) {
                    NCookieManager.getInstance().deleteCookie(httpUrl);
                }
                subscriber.onNext(httpUrl);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<HttpUrl, Response>() {
                    @Override
                    public Response call(HttpUrl url) {
                        //do request
                        Request request = new Request.Builder().url(url).build();
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

    public interface HttpResponseListener {

        void success(@NonNull Response response);

        void failure(String message);

    }

}
