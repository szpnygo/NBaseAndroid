package info.smemo.nbase.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import info.smemo.nbase.app.AppConstant;
import info.smemo.nbase.app.NBaseConfig;

/**
 * Created by neo on 16/6/7.
 */
public class NBaseApplication extends Application {

    protected static NBaseApplication sBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        if (null == sBaseApplication) {
            sBaseApplication = this;
        }
        Logger.init(NBaseConfig.getInstance().nbaseTag)
                .methodCount(1)
                .methodOffset(1)
                .logLevel(NBaseConfig.getInstance().isLogDebug ? LogLevel.FULL : LogLevel.NONE);
        //inti fresco
        Fresco.initialize(getApplicationContext());
    }

    public static NBaseApplication getInstance() {
        return sBaseApplication;
    }

    public static Context getContext() {
        return getInstance().getApplicationContext();
    }
}
