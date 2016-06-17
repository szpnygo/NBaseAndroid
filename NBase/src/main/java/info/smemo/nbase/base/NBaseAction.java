package info.smemo.nbase.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import info.smemo.nbase.bean.CommonJson;
import info.smemo.nbase.bean.CommonJsonList;
import info.smemo.nbase.http.HttpBuilder;
import info.smemo.nbase.http.HttpUtil;
import info.smemo.nbase.util.ThreadUtil;
import okhttp3.CacheControl;

/**
 * Created by neo on 16/6/17.
 */
public class NBaseAction extends HttpUtil {

    public static <T> void get(@NonNull Class<T> clazz, @NonNull String url,
                               @Nullable HttpActionDataListener<T> dataListener) {
        get(clazz, url, null, null, true, null, null, dataListener);
    }


    public static <T> void get(@NonNull Class<T> clazz, @NonNull String url,
                               @Nullable HttpActionListListener<List<T>> listListener) {
        get(clazz, url, null, null, true, null, null, listListener);
    }


    public static <T> void get(@NonNull Class<T> clazz, @NonNull String url, @Nullable HashMap<String, String> map,
                               @Nullable HttpActionDataListener<T> dataListener) {
        get(clazz, url, map, null, true, null, null, dataListener);
    }

    public static <T> void get(@NonNull Class<T> clazz, @NonNull String url, @Nullable HashMap<String, String> map,
                               @Nullable HttpActionListListener<List<T>> listListener) {
        get(clazz, url, map, null, true, null, null, listListener);
    }


    public static <T> void get(@NonNull Class<T> clazz, @NonNull String url, @Nullable HashMap<String, String> map, @Nullable final HttpDataAction dataAction,
                               @Nullable HttpActionDataListener<T> dataListener) {
        get(clazz, url, map, null, true, null, dataAction, dataListener);
    }

    public static <T> void get(@NonNull Class<T> clazz, @NonNull String url, @Nullable HashMap<String, String> map, @Nullable final HttpDataAction dataAction,
                               @Nullable HttpActionListListener<List<T>> listListener) {
        get(clazz, url, map, null, true, null, dataAction, listListener);
    }


    public static <T> void get(@NonNull Class<T> clazz, @NonNull String url, @Nullable HashMap<String, String> map, @Nullable HashMap<String, String> header,
                               @Nullable HttpDataAction dataAction,
                               @Nullable HttpActionDataListener<T> dataListener) {
        get(clazz, url, map, header, true, null, dataAction, dataListener);
    }

    public static <T> void get(@NonNull Class<T> clazz, @NonNull String url, @Nullable HashMap<String, String> map, @Nullable HashMap<String, String> header,
                               @Nullable HttpDataAction dataAction,
                               @Nullable HttpActionListListener<List<T>> listListener) {
        get(clazz, url, map, header, true, null, dataAction, listListener);
    }


    public static <T> void get(@NonNull Class<T> clazz, @NonNull String url, @Nullable HashMap<String, String> map, @Nullable HashMap<String, String> header, @Nullable boolean isCookie,
                               @Nullable HttpDataAction dataAction,
                               @Nullable HttpActionDataListener<T> dataListener) {
        get(clazz, url, map, header, isCookie, null, dataAction, dataListener);
    }

    public static <T> void get(@NonNull Class<T> clazz, @NonNull String url, @Nullable HashMap<String, String> map, @Nullable HashMap<String, String> header, @Nullable boolean isCookie,
                               @Nullable HttpDataAction dataAction,
                               @Nullable HttpActionListListener<List<T>> listListener) {
        get(clazz, url, map, header, isCookie, null, dataAction, listListener);
    }


    public static <T> void get(@NonNull Class<T> clazz, @NonNull String url, @Nullable HashMap<String, String> map, @Nullable HashMap<String, String> header, @Nullable boolean isCookie, @Nullable CacheControl cacheControl,
                               @Nullable HttpDataAction dataAction,
                               @Nullable HttpActionDataListener<T> dataListener) {
        request(clazz, HttpType.GET, url, map, null, header, isCookie, cacheControl, dataAction, dataListener, null);
    }

    public static <T> void get(@NonNull Class<T> clazz, @NonNull String url, @Nullable HashMap<String, String> map, @Nullable HashMap<String, String> header, @Nullable boolean isCookie, @Nullable CacheControl cacheControl,
                               @Nullable HttpDataAction dataAction,
                               @Nullable HttpActionListListener<List<T>> listListener) {
        request(clazz, HttpType.GET, url, map, null, header, isCookie, cacheControl, dataAction, null, listListener);
    }


    public static <T> void request(@NonNull final Class<T> clazz, @NonNull HttpType httpType, @NonNull String url,
                                   @Nullable HashMap<String, String> map, @Nullable HashMap<String, String> datas, @Nullable HashMap<String, String> header, @Nullable boolean isCookie,
                                   @Nullable CacheControl cacheControl, @Nullable final HttpDataAction dataAction,
                                   @Nullable final HttpActionDataListener<T> dataListener,
                                   @Nullable final HttpActionListListener<List<T>> listListener) {
        HttpBuilder builder = HttpUtil.newBuilder();
        builder.setHttpType(httpType)
                .setUrl(url)
                .setHeaderMap(header)
                .setGetMap(map)
                .setGetMap(datas)
                .setCookie(isCookie)
                .setCacheControl(cacheControl)
                .setHttpDataAction(dataAction)
                .setListener(new HttpDataListener() {
                    @Override
                    public void success(String response) {
                        try {

                            if (dataListener != null) {
                                final CommonJson<T> commonJson = fromJson(response, clazz);
                                ThreadUtil.newThreadMain(new ThreadUtil.ThreadRunnableMain() {
                                    @Override
                                    public void inMain() {
                                        dataListener.success(commonJson.data);
                                    }
                                });

                            } else if (listListener != null) {
                                final CommonJsonList<T> commonJsonList = fromJsonList(response, clazz);
                                ThreadUtil.newThreadMain(new ThreadUtil.ThreadRunnableMain() {
                                    @Override
                                    public void inMain() {
                                        listListener.success(commonJsonList.data);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            if (null != dataListener)
                                dataListener.error(ERROR_DATA_ERROR, e.getMessage(), false);
                            if (null != listListener)
                                listListener.error(ERROR_DATA_ERROR, e.getMessage(), false);
                        }
                    }

                    @Override
                    public void error(int code, String message) {
                        if (null != dataListener) dataListener.error(code, message, true);
                        if (null != listListener) listListener.error(code, message, true);
                    }
                }).request();
    }

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


    public interface HttpActionDataListener<T> {
        void success(T response);

        //UI线程
        void error(int code, String message, boolean inMain);
    }

    public interface HttpActionListListener<T> {
        void success(T response);

        //UI线程
        void error(int code, String message, boolean inMain);
    }


}
