package info.smemo.nbase.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by neo on 16/6/7.
 */
public class BaseApplication extends Application {

    protected static BaseApplication sBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        if (null == sBaseApplication) {
            sBaseApplication = this;
        }
        //inti fresco
        Fresco.initialize(getApplicationContext());
    }

    public static BaseApplication getInstance() {
        return sBaseApplication;
    }
}
