package info.smemo.nbase.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by neo on 16/7/4.
 */
public class NBaseConfig implements AppConstant {

    private static NBaseConfig single = new NBaseConfig();

    public static NBaseConfig getInstance() {
        if (single == null)
            single = new NBaseConfig();
        return single;
    }

    public NBaseConfig() {

    }

    /**
     * 是否打印LOG
     */
    public boolean isLogDebug = true;

    /**
     * 是否允许写LOG到文件
     */
    public boolean isAllowLogFile = true;

    /**
     * 标签
     */
    public String nbaseTag = "NBaseAndroid";

    /**
     * SharedPreferences File Name
     */
    public String SP_NAME = "NBaseAndroid";

    /**
     * 缓存日志大小
     */
    public int MAX_HTTP_CACHE_SIZE = 20 * MB;

    /**
     * HTTP超时时间
     */
    public int HTTP_CONNECT_TIMEOUT = 15;
    public int HTTP_WRITE_TIMEOUT = 20;
    public int HTTP_READ_TIMEOUR = 60;

    /**
     * 日志地址
     */
    public String LOG_PATH = Environment.getExternalStorageDirectory() + File.separator + "NBaseAndroid" + File.separator + "Log" + File.separator;

    /**
     * 照片缓存路径
     */
    public String PHOTO_CACHE_PATH = Environment.getExternalStorageDirectory() + File.separator + "NBaseAndroid" + File.separator + "nbaseandroid" + File.separator + "img";

    public int LOG_METHOD_COUNT = 2;
    public int LOG_METHOD_OFFSET = 1;

    public NBaseConfig setLogDebug(boolean logDebug) {
        isLogDebug = logDebug;
        return this;
    }

    public NBaseConfig setAllowLogFile(boolean allowLogFile) {
        isAllowLogFile = allowLogFile;
        return this;
    }

    public NBaseConfig setNbaseTag(String nbaseTag) {
        this.nbaseTag = nbaseTag;
        return this;
    }

    public NBaseConfig setSP_NAME(String SP_NAME) {
        this.SP_NAME = SP_NAME;
        return this;
    }

    public NBaseConfig setMAX_HTTP_CACHE_SIZE(int MAX_HTTP_CACHE_SIZE) {
        this.MAX_HTTP_CACHE_SIZE = MAX_HTTP_CACHE_SIZE;
        return this;
    }

    public NBaseConfig setHTTP_CONNECT_TIMEOUT(int HTTP_CONNECT_TIMEOUT) {
        this.HTTP_CONNECT_TIMEOUT = HTTP_CONNECT_TIMEOUT;
        return this;
    }

    public NBaseConfig setHTTP_WRITE_TIMEOUT(int HTTP_WRITE_TIMEOUT) {
        this.HTTP_WRITE_TIMEOUT = HTTP_WRITE_TIMEOUT;
        return this;
    }

    public NBaseConfig setHTTP_READ_TIMEOUR(int HTTP_READ_TIMEOUR) {
        this.HTTP_READ_TIMEOUR = HTTP_READ_TIMEOUR;
        return this;
    }

    public NBaseConfig setLOG_PATH(String LOG_PATH) {
        this.LOG_PATH = LOG_PATH;
        return this;
    }

    public NBaseConfig setPHOTO_CACHE_PATH(String PHOTO_CACHE_PATH) {
        this.PHOTO_CACHE_PATH = PHOTO_CACHE_PATH;
        return this;
    }

    public NBaseConfig setLogMethodCount(int logMethodCount) {
        LOG_METHOD_COUNT = logMethodCount;
        return this;
    }

    public NBaseConfig setLogMethodOffset(int logMethodOffset) {
        LOG_METHOD_OFFSET = logMethodOffset;
        return this;
    }
}
