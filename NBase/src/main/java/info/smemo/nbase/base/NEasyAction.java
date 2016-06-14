package info.smemo.nbase.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import info.smemo.nbase.bean.CommonJson;
import info.smemo.nbase.bean.CommonJsonList;
import info.smemo.nbase.http.HttpUtil;
import info.smemo.nbase.util.StringUtil;
import okhttp3.CacheControl;
import okhttp3.Response;

/**
 * Created by neo on 16/6/14.
 */
public class NEasyAction extends NBaseAction {

    public static <T> void get(@NonNull Class clazz, @NonNull String url, @NonNull HttpResponseListener<T> listener) {
        get(clazz, url, null, listener);
    }

    public static <T> void get(@NonNull Class clazz, @NonNull String url, @Nullable HashMap<String, String> map, @NonNull HttpResponseListener<T> listener) {
        get(clazz, url, map, true, listener);
    }

    public static <T> void get(@NonNull Class clazz, @NonNull String url, @Nullable HashMap<String, String> map, @NonNull boolean isCookie, @NonNull HttpResponseListener<T> listener) {
        get(clazz, url, map, isCookie, null, listener);
    }

    public static <T> void get(@NonNull final Class clazz, @NonNull String url, @Nullable HashMap<String, String> map, @NonNull boolean isCookie, @Nullable CacheControl cacheControl,
                               @NonNull final HttpResponseListener<T> listener) {
        HttpUtil.get(url, map, isCookie, cacheControl, new HttpUtil.HttpResponseListener() {
            @Override
            public void success(@NonNull Response response) {
                try {
                    if (!response.isSuccessful()) {
                        listener.error(response.code(), response.toString());
                        return;
                    }

                    String bodyStr = response.body().string();
                    if (StringUtil.isEmpty(bodyStr)) {
                        listener.error(ERROR_DATA_ERROR, "Response Body is empty Http code:" + response.code());
                        return;

                    }

                    CommonJson<T> commonJson = fromJson(bodyStr, clazz);
                    listener.success(commonJson.data);

                } catch (IOException e) {
                    listener.error(ERROR_DATA_ERROR, e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(String message) {
                listener.error(ERROR_NETWORK_ERROR, message);
            }
        });
    }

    public static <T> void getList(@NonNull Class clazz, @NonNull String url, @NonNull HttpResponseListener<List<T>> listener) {
        getList(clazz, url, null, listener);
    }

    public static <T> void getList(@NonNull Class clazz, @NonNull String url, @Nullable HashMap<String, String> map, @NonNull HttpResponseListener<List<T>> listener) {
        getList(clazz, url, map, true, listener);
    }

    public static <T> void getList(@NonNull Class clazz, @NonNull String url, @Nullable HashMap<String, String> map, @NonNull boolean isCookie, @NonNull HttpResponseListener<List<T>> listener) {
        getList(clazz, url, map, isCookie, null, listener);
    }

    public static <T> void getList(@NonNull final Class clazz, @NonNull String url, @Nullable HashMap<String, String> map, @NonNull boolean isCookie, @Nullable CacheControl cacheControl,
                                   @NonNull final HttpResponseListener<List<T>> listener) {
        HttpUtil.get(url, map, isCookie, cacheControl, new HttpUtil.HttpResponseListener() {
            @Override
            public void success(@NonNull Response response) {
                try {
                    if (!response.isSuccessful()) {
                        listener.error(response.code(), response.toString());
                        return;
                    }

                    String bodyStr = response.body().string();
                    if (StringUtil.isEmpty(bodyStr)) {
                        listener.error(ERROR_DATA_ERROR, "Response Body is empty Http code:" + response.code());
                        return;

                    }

                    CommonJsonList<T> commonJson = fromJsonList(bodyStr, clazz);
                    listener.success(commonJson.data);

                } catch (IOException e) {
                    listener.error(ERROR_DATA_ERROR, e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(String message) {
                listener.error(ERROR_NETWORK_ERROR, message);
            }
        });
    }


    public static <T> void post(@NonNull Class clazz, @NonNull String url, @Nullable HashMap<String, Object> map, @NonNull final HttpResponseListener<T> listener) {
        post(clazz, url, map, null, listener);
    }

    public static <T> void post(@NonNull Class clazz, @NonNull String url, @Nullable HashMap<String, Object> map, @Nullable HashMap<String, String> headers, @NonNull final HttpResponseListener<T> listener) {
        post(clazz, url, map, headers, true, listener);
    }

    public static <T> void post(@NonNull Class clazz, @NonNull String url, @Nullable HashMap<String, Object> map, @Nullable HashMap<String, String> headers, @NonNull boolean isCookie, @NonNull final HttpResponseListener<T> listener) {
        post(clazz, url, map, headers, true, null, listener);
    }

    public static <T> void post(@NonNull final Class clazz, @NonNull String url, @Nullable HashMap<String, Object> map, @Nullable HashMap<String, String> headers, @NonNull boolean isCookie, @Nullable CacheControl cacheControl, @NonNull final HttpResponseListener<T> listener) {
        HttpUtil.post(url, map, headers, isCookie, cacheControl, new HttpUtil.HttpResponseListener() {
            @Override
            public void success(@NonNull Response response) {
                try {
                    if (!response.isSuccessful()) {
                        listener.error(response.code(), response.toString());
                        return;
                    }

                    String bodyStr = response.body().string();
                    if (StringUtil.isEmpty(bodyStr)) {
                        listener.error(ERROR_DATA_ERROR, "Response Body is empty Http code:" + response.code());
                        return;

                    }

                    CommonJson<T> commonJson = fromJson(bodyStr, clazz);
                    listener.success(commonJson.data);

                } catch (IOException e) {
                    listener.error(ERROR_DATA_ERROR, e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(String message) {
                listener.error(ERROR_NETWORK_ERROR, message);
            }
        });
    }

    public static <T> void postList(@NonNull Class clazz, @NonNull String url, @Nullable HashMap<String, Object> map, @NonNull final HttpResponseListener<List<T>> listener) {
        postList(clazz, url, map, null, listener);
    }

    public static <T> void postList(@NonNull Class clazz, @NonNull String url, @Nullable HashMap<String, Object> map, @Nullable HashMap<String, String> headers, @NonNull final HttpResponseListener<List<T>> listener) {
        postList(clazz, url, map, headers, true, listener);
    }

    public static <T> void postList(@NonNull Class clazz, @NonNull String url, @Nullable HashMap<String, Object> map, @Nullable HashMap<String, String> headers, @NonNull boolean isCookie, @NonNull final HttpResponseListener<List<T>> listener) {
        postList(clazz, url, map, headers, true, null, listener);
    }

    public static <T> void postList(@NonNull final Class clazz, @NonNull String url, @Nullable HashMap<String, Object> map, @Nullable HashMap<String, String> headers, @NonNull boolean isCookie, @Nullable CacheControl cacheControl, @NonNull final HttpResponseListener<List<T>> listener) {
        HttpUtil.post(url, map, headers, isCookie, cacheControl, new HttpUtil.HttpResponseListener() {
            @Override
            public void success(@NonNull Response response) {
                try {
                    if (!response.isSuccessful()) {
                        listener.error(response.code(), response.toString());
                        return;
                    }

                    String bodyStr = response.body().string();
                    if (StringUtil.isEmpty(bodyStr)) {
                        listener.error(ERROR_DATA_ERROR, "Response Body is empty Http code:" + response.code());
                        return;

                    }

                    CommonJsonList<T> commonJson = fromJsonList(bodyStr, clazz);
                    listener.success(commonJson.data);

                } catch (IOException e) {
                    listener.error(ERROR_DATA_ERROR, e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(String message) {
                listener.error(ERROR_NETWORK_ERROR, message);
            }
        });
    }

}
