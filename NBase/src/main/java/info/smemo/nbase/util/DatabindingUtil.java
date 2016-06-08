package info.smemo.nbase.util;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DatabindingUtil {

    @BindingAdapter("android:setImageUri")
    public static void setImageUri(SimpleDraweeView view, String url) {
        view.setImageURI(Uri.parse(url));
    }

    @BindingAdapter({"android:timeFormat", "android:timeValue"})
    public static void formatTime(TextView view, String format, String time) {
        String timeFormat = StringUtil.isEmpty(format) ? "yyyy-MM-dd" : format;
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat, Locale.CHINA);
        view.setText(dateFormat.format(Long.valueOf(time)));
    }
}
