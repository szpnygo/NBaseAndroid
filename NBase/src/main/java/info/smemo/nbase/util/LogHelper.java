package info.smemo.nbase.util;

import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import info.smemo.nbase.app.AppConstant;

/**
 * Created by neo on 16/6/8.
 */
public class LogHelper implements AppConstant {

    public static boolean isPrintLog = !isLogDebug;

    public static void d(String tag, Class cls, String message) {
        if (isPrintLog)
            return;
        d(tag, cls.getSimpleName() + " >>>>>>> " + message);
    }

    public static void d(Class cls, String message) {
        if (isPrintLog)
            return;
        d(TAG, cls.getSimpleName() + " >>>>>>> " + message);
    }

    public static void d(String tag, String message) {
        if (isPrintLog)
            return;
        Log.d(tag, Thread.currentThread().getName() + " >>>>>>> " + message);
    }

    public static void i(String tag, Class cls, String message) {
        if (isPrintLog)
            return;
        i(tag, cls.getSimpleName() + " >>>>>>> " + message);
    }

    public static void i(Class cls, String message) {
        if (isPrintLog)
            return;
        i(TAG, cls.getSimpleName() + " >>>>>>> " + message);
    }

    public static void i(String tag, String message) {
        if (isPrintLog)
            return;
        Logger.i(message);
    }

    public static void w(String tag, Class cls, String message) {
        if (isPrintLog)
            return;
        w(tag, cls.getSimpleName() + " >>>>>>> " + message);
    }

    public static void w(Class cls, String message) {
        if (isPrintLog)
            return;
        w(TAG, cls.getSimpleName() + " >>>>>>> " + message);
    }

    public static void w(String tag, String message) {
        if (isPrintLog)
            return;
        Logger.w(message);
    }

    public static void e(String tag, Class cls, String message) {
        if (isPrintLog)
            return;
        e(tag, cls.getSimpleName() + " >>>>>>> " + message);
    }

    public static void e(Class cls, String message) {
        if (isPrintLog)
            return;
        e(TAG, cls.getSimpleName() + " >>>>>>> " + message);
    }

    public static void e(String tag, String message) {
        if (isPrintLog)
            return;
        Logger.e(message);
        saveLog(" [ " + TimeUtil.getTime() + " ] " + message);
    }

    public static void saveLog(String message) {
        if (!isAllowLogFile) {
            return;
        }
        File file = new File(LOG_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        Calendar c = Calendar.getInstance();
        String logName = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH) + ".log";
        File logFile = new File(file, logName);
        try {
            FileUtils.writeTextFile(logFile, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
