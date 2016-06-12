package info.smemo.nbase.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import info.smemo.nbase.http.HttpUtil;
import info.smemo.nbase.util.StringUtil;
import okhttp3.CacheControl;
import okhttp3.Response;

/**
 * Created by neo on 16/6/12.
 */
public class NBaseAction {

    public static void get(@NonNull String url, @NonNull HttpActionListener listener) {
        get(url, null, listener);
    }

    public static void get(@NonNull String url, @Nullable HashMap<String, String> map, @NonNull HttpActionListener listener) {
        get(url, map, true, listener);
    }

    public static void get(@NonNull String url, @Nullable HashMap<String, String> map, @NonNull boolean isCookie, @NonNull HttpActionListener listener) {
        get(url, map, isCookie, listener, defaultAction);
    }

    public static void get(@NonNull String url, @Nullable HashMap<String, String> map, @NonNull boolean isCookie, @NonNull HttpActionListener listener, ParseDataAction action) {
        get(url, map, isCookie, null, listener, action);
    }

    public static void get(@NonNull String url, @Nullable HashMap<String, String> map, @NonNull boolean isCookie, @Nullable CacheControl cacheControl, @NonNull final HttpActionListener listener, final ParseDataAction action) {
        HttpUtil.get(url, map, isCookie, cacheControl, new HttpUtil.HttpResponseListener() {
            @Override
            public void success(@NonNull Response response) {
                action.parse(response, listener);
            }

            @Override
            public void failure(String message) {
                listener.failure(message);
            }
        });
    }

    public static void post(@NonNull String url, @Nullable HashMap<String, Object> map, @NonNull final HttpActionListener listener) {
        post(url, map, null, listener);
    }

    public static void post(@NonNull String url, @Nullable HashMap<String, Object> map, @Nullable HashMap<String, String> headers, @NonNull final HttpActionListener listener) {
        post(url, map, headers, true, listener);
    }

    public static void post(@NonNull String url, @Nullable HashMap<String, Object> map, @Nullable HashMap<String, String> headers, @NonNull boolean isCookie, @NonNull final HttpActionListener listener) {
        post(url, map, headers, true, null, listener);
    }

    public static void post(@NonNull String url, @Nullable HashMap<String, Object> map, @Nullable HashMap<String, String> headers, @NonNull boolean isCookie, @Nullable CacheControl cacheControl, @NonNull final HttpActionListener listener) {
        post(url, map, headers, isCookie, cacheControl, listener, defaultAction);
    }

    public static void post(@NonNull String url, @Nullable HashMap<String, Object> map, @Nullable HashMap<String, String> headers, @NonNull boolean isCookie, @Nullable CacheControl cacheControl, @NonNull final HttpActionListener listener, final ParseDataAction action) {
        HttpUtil.post(url, map, headers, isCookie, cacheControl, new HttpUtil.HttpResponseListener() {
            @Override
            public void success(@NonNull Response response) {
                action.parse(response, listener);
            }

            @Override
            public void failure(String message) {
                listener.failure(message);
            }
        });
    }


    private static ParseDataAction defaultAction = new ParseDataAction() {

        @Override
        public void parse(Response response, HttpActionListener listener) {
            try {
                if (!response.isSuccessful()) {
                    listener.error(response.code(), response.toString());
                    return;
                }
                String bodyStr = response.body().string();
                if (StringUtil.isEmpty(bodyStr)) {
                    listener.error(response.code(), "Response Body is empty");
                    return;
                }
                JSONObject object = new JSONObject(bodyStr);
                int code = object.getInt("code");
                if (code == 0) {
                    listener.success(response, bodyStr);
                } else {
                    listener.error(code, object.getString("message"));
                }
            } catch (JSONException e) {
                listener.failure(e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                listener.failure(e.getMessage());
                e.printStackTrace();
            }
        }
    };

    public interface HttpActionListener {

        void success(Response response, String body);

        void error(int code, String message);

        void failure(String message);

    }

    public interface ParseDataAction {

        void parse(Response response, HttpActionListener listener);

    }

}
