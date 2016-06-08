package info.smemo.nbase.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.smemo.nbase.app.AppConstant;
import info.smemo.nbase.util.LogHelper;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by neo on 16/6/8.
 */
public class HttpUtil implements AppConstant {

    public static OkHttpClient client = getClient();

    private static OkHttpClient getClient() {
        if (client != null)
            return client;
        return new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {

                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
    }

    public static void get(String url) {
//        HttpUrl regUrl = new HttpUrl.Builder()
//                .scheme("https")
//                .host("api.smemo.info")
//                .addPathSegments("api.php/v2")
//                .addPathSegment("Backup")
//                .addPathSegment("lists")
//                .addQueryParameter("test", "test")
//                .build();

        HttpUrl regUrl = HttpUrl.parse("https://api.smemo.info/api.php/v2/Backup/lists").newBuilder()
                .addQueryParameter("test", "test")
                .build();
        Request request = new Request.Builder().url(regUrl).build();
        try {
            Response response = client.newCall(request).execute();
            LogHelper.e(TAG, response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
