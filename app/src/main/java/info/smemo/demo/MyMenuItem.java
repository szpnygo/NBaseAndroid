package info.smemo.demo;

import android.content.Intent;

/**
 * Created by neo on 16/6/22.
 */
public class MyMenuItem {

    public String title;
    public Intent mIntent;

    public MyMenuItem(String title, Intent intent) {
        this.title = title;
        mIntent = intent;
    }
}
