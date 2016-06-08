package info.smemo.nbase.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import info.smemo.nbase.BR;

/**
 * Created by neo on 16/6/8.
 */
public class SelectBean extends BaseObservable {

    public String title;
    public Object id;
    public Object bean;

    public SelectBean() {
    }

    public SelectBean(String title) {
        this.title = title;
    }

    public SelectBean(String title, Object id) {
        this.title = title;
        this.id = id;
    }

    public SelectBean(String title, Object id, Object bean) {
        this.title = title;
        this.id = id;
        this.bean = bean;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
}
