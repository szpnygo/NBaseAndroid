package info.smemo.nbase.http;

/**
 * Created by neo on 16/6/16.
 */
public class BaseHttpBean {

    //Cookie Header
    //String cookie = "Cookie", "usi=test1;usk=test2");

//    public static final int HTTP_POST = 1;
//    public static final int HTTP_GET = 2;
//    public static final int HTTP_ERROR = 3;
//
//    public String HTTP_URL;
//
//    public HashMap<String, String> http_header;
//    public HashMap<String, Object> http_data;
//    public HashMap<String, String> http_get_data;
//
//    Class<? extends BaseHttpBean> clazz = this.getClass();
//
//    private HttpUtil.HttpResponseListener mHttpResponseListener;
//
//    public BaseHttpBean() {
//        super();
//    }
//
//    public void request(@NonNull HttpUtil.BaseHttpListener listener) {
//        request(true, listener);
//    }
//
//    public BaseHttpBean request(Boolean isCookie, @NonNull HttpUtil.BaseHttpListener listener) {
//        if (listener instanceof HttpUtil.HttpResponseListener) {
//            requestWithHttpUtil(isCookie, (HttpUtil.HttpResponseListener) listener);
//        } else if (listener instanceof NBaseAction.HttpActionListener) {
////            requestWithEasyAction(isCookie, (NBaseAction.HttpActionListener) listener);
//        }
//        return this;
//    }
//
//    private BaseHttpBean requestWithHttpUtil(Boolean isCookie, @NonNull HttpUtil.HttpResponseListener httpResponseListener) {
//        if (getHttpType() == HTTP_POST) {
//            getHttpPostData();
//            HttpUtil.post(HTTP_URL, http_data, http_header, isCookie, null, httpResponseListener);
//        } else if (getHttpType() == HTTP_GET) {
//            getHttpGetData();
//            HttpUtil.get(HTTP_URL, http_get_data, isCookie, null, httpResponseListener);
//        } else {
//            httpResponseListener.failure("Your Http url is null");
//        }
//        return this;
//    }
//
//    public <T> BaseHttpBean requestWithEasyAction(Class returnClazz, @NonNull NBaseAction.HttpResponseListener<List<T>> httpActionListener) {
//        if (getHttpType() == HTTP_POST) {
//            getHttpPostData();
//            NEasyAction.postList(returnClazz, HTTP_URL, http_data, http_header, true, null, httpActionListener);
//        }
//        return this;
//    }
//
//    public void getHttpPostData() {
//        if (http_data == null)
//            http_data = new HashMap<>();
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            HttpUnusedParam unusedParam = field.getAnnotation(HttpUnusedParam.class);
//            if (unusedParam == null) {
//                HttpRequestName httpRequestName = field.getAnnotation(HttpRequestName.class);
//                String key;
//                if (httpRequestName == null) {
//                    key = field.getName();
//                } else {
//                    key = httpRequestName.value();
//                }
//                try {
//                    if (!field.getType().equals("com.android.tools.fd.runtime.IncrementalChange") && !key.equals("$change")) {
//                        http_data.put(key, field.get(this));
//                    }
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public void getHttpGetData() {
//        if (http_get_data == null)
//            http_get_data = new HashMap<>();
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            HttpUnusedParam unusedParam = field.getAnnotation(HttpUnusedParam.class);
//            if (unusedParam == null) {
//                HttpRequestName httpRequestName = field.getAnnotation(HttpRequestName.class);
//                String key;
//                if (httpRequestName == null) {
//                    key = field.getName();
//                } else {
//                    key = httpRequestName.value();
//                }
//                try {
//                    if (!field.getType().equals("com.android.tools.fd.runtime.IncrementalChange")) {
//                        http_get_data.put(key, String.valueOf(field.get(this)));
//                    }
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public int getHttpType() {
//        HttpPostTarget postTarget = clazz.getAnnotation(HttpPostTarget.class);
//        if (postTarget != null) {
//            HTTP_URL = postTarget.value();
//            return HTTP_POST;
//        }
//        HttpGetTarget getTarget = clazz.getAnnotation(HttpGetTarget.class);
//        if (getTarget != null) {
//            HTTP_URL = getTarget.value();
//            return HTTP_GET;
//        }
//        return HTTP_ERROR;
//    }
//
//    public void setHeader(String key, String value) {
//        if (http_header == null)
//            http_header = new HashMap<>();
//        http_header.put(key, value);
//    }
//
//    public void setCookie(String key, String value) {
//        if (http_header == null)
//            http_header = new HashMap<>();
//        String cookie = http_header.get("Cookie") == null ? "" : http_header.get("Cookie");
//        cookie = cookie + key + ":" + value + ";";
//        http_header.put("Cookie", cookie);
//    }
}
