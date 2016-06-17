package info.smemo.nbase.http;

import java.util.HashMap;

import info.smemo.nbase.app.AppConstant;
import info.smemo.nbase.util.StringUtil;
import okhttp3.CacheControl;

/**
 * Created by neo on 16/6/17.
 */
public class HttpBuilder implements AppConstant {

    HttpUtil.HttpType httpType;
    String url;
    HashMap<String, Object> postMap;
    HashMap<String, String> getMap;
    HashMap<String, String> headerMap;
    boolean isCookie;
    CacheControl cacheControl;
    HttpUtil.HttpDataListener listener;
    HttpUtil.HttpDataAction httpDataAction;
    HttpUtil.ThreadType returnThread;


    public HttpBuilder() {
        httpType = HttpUtil.HttpType.POST;
        url = "";
        postMap = null;
        getMap = null;
        headerMap = null;
        isCookie = true;
        cacheControl = null;
        listener = null;
        httpDataAction = null;
        returnThread = HttpUtil.ThreadType.THREAD;
    }

    public HttpBuilder setHttpType(HttpUtil.HttpType httpType) {
        this.httpType = httpType;
        return this;
    }

    public HttpBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public HttpBuilder setPostMap(HashMap<String, Object> postMap) {
        this.postMap = postMap;
        return this;
    }

    public HttpBuilder setGetMap(HashMap<String, String> getMap) {
        this.getMap = getMap;
        return this;
    }

    public HttpBuilder setHeaderMap(HashMap<String, String> headerMap) {
        this.headerMap = headerMap;
        return this;
    }

    public HttpBuilder setCookie(boolean cookie) {
        isCookie = cookie;
        return this;
    }

    public HttpBuilder setCacheControl(CacheControl cacheControl) {
        this.cacheControl = cacheControl;
        return this;
    }

    public HttpBuilder setListener(HttpUtil.HttpDataListener listener) {
        this.listener = listener;
        return this;
    }

    public HttpBuilder setHttpDataAction(HttpUtil.HttpDataAction httpDataAction) {
        this.httpDataAction = httpDataAction;
        return this;
    }

    public void request(HttpUtil.HttpDataListener listener) {
        this.setListener(listener);
        doRequest();
    }

    public void request() {
        doRequest();
    }

    private void doRequest() {
        if (listener == null)
            throw new RuntimeException("没有设置setListener");
        if (StringUtil.isEmpty(url))
            throw new RuntimeException("请求url为空");
        HttpUtil.request(this);
    }


}
