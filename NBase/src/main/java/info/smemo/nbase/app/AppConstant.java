package info.smemo.nbase.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by neo on 16/6/7.
 */
public interface AppConstant extends AppConfig {

    int KB = 1024;
    int MB = 1024 * KB;
    int GB = 1024 * MB;

    /**
     * 标签
     */
    String TAG = "NBaseAndroid";
    String TAG_HTTP = TAG + "_Http";

    /**
     * SharedPreferences File Name
     */
    String SP_NAME = "NBaseAndroid";

    /**
     * 日志地址
     */
    String LOG_PATH = Environment.getExternalStorageDirectory() + File.separator + "NBaseAndroid" + File.separator + "Log" + File.separator;

    /**
     * 缓存日志大小
     */
    int MAX_HTTP_CACHE_SIZE = 20 * MB;

    /**
     * HTTP超时时间
     */
    int HTTP_CONNECT_TIMEOUT = 15;
    int HTTP_WRITE_TIMEOUT = 20;
    int HTTP_READ_TIMEOUR = 60;

    /**
     * 照片缓存路径
     */
    String PHOTO_CACHE_PATH = Environment.getExternalStorageDirectory() + File.separator + "NBaseAndroid" + File.separator + "nbaseandroid" + File.separator + "img";

}
