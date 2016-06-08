package info.smemo.nbase.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

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
