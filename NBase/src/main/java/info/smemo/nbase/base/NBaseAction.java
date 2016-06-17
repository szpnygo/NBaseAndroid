package info.smemo.nbase.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

import info.smemo.nbase.app.AppConstant;
import info.smemo.nbase.bean.CommonJson;
import info.smemo.nbase.bean.CommonJsonList;
import info.smemo.nbase.http.HttpUtil;
import info.smemo.nbase.util.StringUtil;
import okhttp3.CacheControl;

/**
 * Created by neo on 16/6/12.
 */
public class NBaseAction implements AppConstant {

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
            public void success(@NonNull String response) {
                action.parse(response, listener);
            }

            @Override
            public void failure(String message) {
                listener.failure(ERROR_NETWORK_ERROR, message);
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
            public void success(@NonNull String response) {
                action.parse(response, listener);
            }

            @Override
            public void failure(String message) {
                listener.failure(ERROR_NETWORK_ERROR, message);
            }
        });
    }


    private static ParseDataAction defaultAction = new ParseDataAction() {

        @Override
        public void parse(@NonNull String response, @NonNull HttpActionListener listener) {
            try {
                if (StringUtil.isEmpty(response)) {
                    listener.error(ERROR_DATA_ERROR, "Response Body is empty Http");
                    return;
                }

                JSONObject object = new JSONObject(response);
                int code = object.getInt("code");
                if (code == 0) {
                    listener.success(response);
                } else {
                    listener.error(code, object.getString("message"));
                }
            } catch (JSONException e) {
                listener.failure(ERROR_DATA_ERROR, e.getMessage());
                e.printStackTrace();
            }
        }
    };

    public static <T> T fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(CommonJson.class, clazz);
        return gson.fromJson(json, objectType);
    }

    public static <T> T fromJsonList(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(CommonJsonList.class, clazz);
        return gson.fromJson(json, objectType);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    public interface HttpActionListener extends HttpUtil.BaseHttpListener {

        void success(@Nullable String body);

        void error(int code, String message);

        void failure(int code, @Nullable String message);

    }

    public interface ParseDataAction {

        void parse(@NonNull String response, @NonNull HttpActionListener listener);

    }

    public interface HttpResponseListener<T> extends HttpUtil.BaseHttpListener {

        void success(T t);

        void error(int code, String message);

    }

}
