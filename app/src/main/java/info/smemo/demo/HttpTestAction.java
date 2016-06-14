package info.smemo.demo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import info.smemo.nbase.base.NBaseAction;
import info.smemo.nbase.bean.CommonJson;
import info.smemo.nbase.bean.CommonJsonList;
import okhttp3.Response;

/**
 * Created by neo on 16/6/14.
 */
public class HttpTestAction extends NBaseAction {

    public static void list(final HttpResponseListener<ArrayList<FundBean>> responseListener) {

        get("https://api.smemo.info/fund.php/index/getFundList", new HttpActionListener() {
            @Override
            public void success(@NonNull Response response, @Nullable String body) {
                CommonJsonList<FundBean> commonJsonList = fromJsonList(body, FundBean.class);
                responseListener.success(commonJsonList.data);

            }

            @Override
            public void error(int code, String message) {
                responseListener.error(code, message);
            }

            @Override
            public void failure(int code, @Nullable String message) {
                responseListener.error(code, message);
            }

        });

    }

    public static void info(final HttpResponseListener<InfoBean> responseListener) {

        get("http://api.smemo.info/api.php/v2/info/info?type=devloper", new HttpActionListener() {
            @Override
            public void success(@NonNull Response response, @Nullable String body) {

                CommonJson<InfoBean> commonJson = fromJson(body, InfoBean.class);
                responseListener.success(commonJson.data);

            }

            @Override
            public void error(int code, String message) {
                responseListener.error(code, message);
            }

            @Override
            public void failure(int code, @Nullable String message) {
                responseListener.error(code, message);
            }
        });

    }


}
