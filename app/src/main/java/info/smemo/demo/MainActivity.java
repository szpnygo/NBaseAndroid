package info.smemo.demo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;

import info.smemo.nbase.activity.NPhotoActivity;
import info.smemo.nbase.base.NBaseAction;
import info.smemo.nbase.base.NEasyAction;
import info.smemo.nbase.util.FrescoUtil;
import info.smemo.nbase.util.LogHelper;

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
                getHttp();
            }
        });
    }

    private void getHttp() {
//        NEasyAction.get(InfoBean.class, "http://api.smemo.info/api.php/v2/info/info?type=devloper", new NBaseAction.HttpResponseListener<InfoBean>() {
//            @Override
//            public void success(InfoBean infoBean) {
//                LogHelper.e(TAG, Thread.currentThread().getName() + ":" + infoBean.msg);
//            }
//
//            @Override
//            public void error(int code, String message) {
//
//            }
//        });
        HashMap<String, Object> map = new HashMap<>();
        map.put("ss", "ss");

        NEasyAction.postList(FundBean.class, "https://api.smemo.info/fund.php/index/getFundList", map, new NBaseAction.HttpResponseListener<List<FundBean>>() {
            @Override
            public void success(List<FundBean> fundBeen) {
                LogHelper.e(TAG, "" + fundBeen.size());
            }

            @Override
            public void error(int code, String message) {

            }
        });
    }

    @Override
    public void takePhotoSuccess(@NonNull Uri imageFile, @Nullable String path) {
        super.takePhotoSuccess(imageFile, path);
        FrescoUtil.loadImageWithFixSize(mImageView, imageFile, 200, 200);
    }

}
