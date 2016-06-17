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
                httpTest4();
            }
        });
    }

    private void getHttp() {
        httpTest1();
        String url = "https://wxfl.ztgame.com/king/index.php/Player/Games/getGames.html";


    }


    /**
     * 测试GET请求单条数据
     */
    private void httpTest1() {
        NBaseAction.get(InfoBean.class, "http://api.smemo.info/api.php/v2/info/info?type=devloper", new NBaseAction.HttpActionDataListener<InfoBean>() {
            @Override
            public void success(InfoBean response) {
                LogHelper.e(TAG, response.msg);
            }

            @Override
            public void error(int code, String message, boolean inMain) {
                LogHelper.e(TAG, "error " + message + ":" + code);
            }
        }).execute();
    }

    /**
     * 测试GET请求多条数据
     */
    private void httpTest2() {
        NBaseAction.get(FundBean.class, "https://wxfl.ztgame.com/king/index.php/Player/Games/getGames.html", new NBaseAction.HttpActionListListener<List<FundBean>>() {
            @Override
            public void success(List<FundBean> response) {
                LogHelper.e(TAG, response.size() + "length");
            }

            @Override
            public void error(int code, String message, boolean inMain) {
                LogHelper.e(TAG, "error " + message + ":" + code);
            }
        }).execute();
    }

    /**
     * 测试POST请求单挑数据
     */
    private void httpTest3() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", 6);
        NBaseAction.post(FundBean.class, "https://wxfl.ztgame.com/king/index.php/Player/Games/getGames.html", data, new NBaseAction.HttpActionListListener<List<FundBean>>() {
            @Override
            public void success(List<FundBean> response) {
                LogHelper.e(TAG, response.size() + "length");
            }

            @Override
            public void error(int code, String message, boolean inMain) {
                LogHelper.e(TAG, "error " + message + ":" + code);
            }
        }).execute();
    }

    /**
     * 测试POST请求多条数据
     */
    private void httpTest4() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", 6);
        NBaseAction.post(InfoBean.class, "http://api.smemo.info/api.php/v2/info/info", data, new NBaseAction.HttpActionDataListener<InfoBean>() {
            @Override
            public void success(InfoBean response) {
                LogHelper.e(TAG, response.msg);
            }

            @Override
            public void error(int code, String message, boolean inMain) {
                LogHelper.e(TAG, "error " + message + ":" + code);
            }
        }).addQuery("type", "devloper").execute();

    }


    @Override
    public void takePhotoSuccess(@NonNull Uri imageFile, @Nullable String path) {
        super.takePhotoSuccess(imageFile, path);
        FrescoUtil.loadImageWithFixSize(mImageView, imageFile, 200, 200);
    }

}
