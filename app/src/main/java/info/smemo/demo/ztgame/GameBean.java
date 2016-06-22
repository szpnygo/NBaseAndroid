package info.smemo.demo.ztgame;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import info.smemo.demo.BR;

/**
 * Created by neo on 16/6/22.
 */
public class GameBean extends BaseObservable {

    public String title;
    public String type;
    public String website;
    public String img;
    public String detail;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public String getWebsite() {
        return website;
    }


    public void setWebsite(String website) {
        this.website = website;
        notifyPropertyChanged(BR.website);
    }

    @Bindable
    public String getImg() {
        return img;
    }


    public void setImg(String img) {
        this.img = img;
        notifyPropertyChanged(BR.img);
    }

    @Bindable
    public String getDetail() {
        return detail;
    }


    public void setDetail(String detail) {
        this.detail = detail;
        notifyPropertyChanged(BR.detail);
    }
}
