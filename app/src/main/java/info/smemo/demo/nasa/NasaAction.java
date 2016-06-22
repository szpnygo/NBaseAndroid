package info.smemo.demo.nasa;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import info.smemo.nbase.base.NBaseAction;
import okhttp3.CacheControl;

/**
 * Created by neo on 16/6/22.
 */
public class NasaAction extends NBaseAction {

    public static String API_KEY = "P1041aiTXP8IWShSvjy1XHDHz6jYDdbJWwP3O79m";

    public static void getApod(final HttpActionDataListener<NasaApodBean> dataListener) {
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(12, TimeUnit.HOURS)
                .maxStale(12, TimeUnit.HOURS)
                .build();
        get(NasaApodBean.class, "https://api.nasa.gov/planetary/apod?api_key=" + API_KEY, dataListener)
                .setHttpDataDirectlyAction()
                .setMainListener(new HttpDataListener() {
                    @Override
                    public void success(String response) {
                        Gson gson = new Gson();
                        NasaApodBean bean = gson.fromJson(response, NasaApodBean.class);
                        dataListener.success(bean);
                    }

                    @Override
                    public void error(int code, String message) {
                        dataListener.error(code, message, isMainThread());
                    }
                })
                .setCacheControl(cacheControl).execute();
    }


}
