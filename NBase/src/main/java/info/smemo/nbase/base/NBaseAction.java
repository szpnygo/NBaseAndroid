package info.smemo.nbase.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import info.smemo.nbase.http.HttpUtil;
import info.smemo.nbase.util.StringUtil;
import okhttp3.Response;

/**
 * Created by neo on 16/6/12.
 */
public class NBaseAction {

    public static void get(@NonNull final String url, @NonNull HttpActionListener listener) {
        get(url, null, listener);
    }

    public static void get(@NonNull final String url, @Nullable final HashMap<String, String> map, @NonNull HttpActionListener listener) {
        get(url, map, true, listener);
    }

    public static void get(@NonNull final String url, @Nullable final HashMap<String, String> map, @NonNull final boolean isCookie, @NonNull final HttpActionListener listener) {
        get(url, map, isCookie, listener, defaultAction);
    }

    public static void get(@NonNull final String url, @Nullable final HashMap<String, String> map, @NonNull final boolean isCookie, @NonNull final HttpActionListener listener, final ParseDataAction action) {
        HttpUtil.get(url, map, isCookie, new HttpUtil.HttpResponseListener() {
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
