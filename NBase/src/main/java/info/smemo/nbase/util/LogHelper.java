package info.smemo.nbase.util;

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
    public static int METHOD_COUNT = 2;
    public static int METHOD_OFFSET = 1;

    public static void d(String tag, String message) {
        if (isPrintLog)
            return;
        Logger.init(tag).methodOffset(METHOD_OFFSET).methodCount(METHOD_COUNT);
        Logger.d(tag, message);
    }

    public static void d(String message) {
        if (isPrintLog)
            return;
        Logger.init(AppConstant.TAG).methodOffset(METHOD_OFFSET).methodCount(METHOD_COUNT);
        Logger.d(message);
    }

    public static void i(String tag, String message) {
        if (isPrintLog)
            return;
        Logger.init(tag).methodOffset(METHOD_OFFSET).methodCount(METHOD_COUNT);
        Logger.i(message);
    }

    public static void i(String message) {
        if (isPrintLog)
            return;
        Logger.init(AppConstant.TAG).methodOffset(METHOD_OFFSET).methodCount(METHOD_COUNT);
        Logger.i(message);
    }

    public static void w(String tag, String message) {
        if (isPrintLog)
            return;
        Logger.init(tag).methodOffset(METHOD_OFFSET).methodCount(METHOD_COUNT);
        Logger.w(message);
    }

    public static void w(String message) {
        if (isPrintLog)
            return;
        Logger.init(AppConstant.TAG).methodOffset(METHOD_OFFSET).methodCount(METHOD_COUNT);
        Logger.w(message);
    }

    public static void e(String tag, String message) {
        if (isPrintLog)
            return;
        Logger.init(tag).methodOffset(METHOD_OFFSET).methodCount(METHOD_COUNT);
        Logger.e(message);
        saveLog(" [ " + TimeUtil.getTime() + " ] " + message);
    }

    public static void e(String message) {
        if (isPrintLog)
            return;
        Logger.init(AppConstant.TAG).methodOffset(METHOD_OFFSET).methodCount(METHOD_COUNT);
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
