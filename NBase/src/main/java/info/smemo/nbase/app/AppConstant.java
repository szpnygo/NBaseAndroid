package info.smemo.nbase.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by neo on 16/6/7.
 */
public interface AppConstant extends AppConfig {

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
}
