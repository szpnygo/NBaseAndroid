package info.smemo.demo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import info.smemo.nbase.activity.NPhotoActivity;
import info.smemo.nbase.util.FrescoUtil;

public class MainActivity extends NPhotoActivity {

    SimpleDraweeView mImageView;

    private TextView mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (TextView) findViewById(R.id.button);
        mImageView = (SimpleDraweeView) findViewById(R.id.imageView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(false);
            }
        });
    }

    @Override
    public void takePhotoSuccess(@NonNull Uri imageFile, @Nullable String path) {
        super.takePhotoSuccess(imageFile, path);
        FrescoUtil.loadImageWithFixSize(mImageView, imageFile, 200, 200);
    }

}
