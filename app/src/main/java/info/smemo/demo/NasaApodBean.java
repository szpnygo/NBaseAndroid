package info.smemo.demo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by neo on 16/6/22.
 */
public class NasaApodBean extends BaseObservable {

    public String date;
    public String explanation;
    public String hdurl;
    public String media_type;
    public String service_version;
    public String title;
    public String url;

    @Bindable
    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
        notifyPropertyChanged(info.smemo.demo.BR.explanation);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(info.smemo.demo.BR.title);
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(info.smemo.demo.BR.url);
    }
}
