package info.smemo.nbase.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

import info.smemo.nbase.app.AppConstant;
import info.smemo.nbase.app.AppManager;

/**
 * Created by neo on 16/6/7.
 */
public class BaseActivity extends Activity implements AppConstant{

    protected ProgressDialog mProgressDialog;
    protected final BaseHandler mBaseHandler = new BaseHandler(this);

    private static final int SHOW_PROGRESS_DIALOG = 0x110001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add activity to activity manager
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        destoryProgressDialog();
        mProgressDialog = null;
    }

    /**
     * show a progress dialog
     *
     * @param title progress notice message
     */
    protected void showProgressDialog(String title) {
        if (null == mProgressDialog) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setMessage(title);
        }
        mProgressDialog.setMessage(title);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    protected void showProgressDialogInThread(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        Message message = new Message();
        message.what = SHOW_PROGRESS_DIALOG;
        message.setData(bundle);
        mBaseHandler.sendMessage(message);
    }

    /**
     * dismiss progress dialog
     */
    protected void dismissProgressDialog() {
        BaseActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                destoryProgressDialog();
            }
        });
    }

    private void destoryProgressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                showProgressDialog(msg.getData().getString("title"));
                break;
        }
    }

    protected static class BaseHandler extends Handler {

        private final WeakReference<BaseActivity> mBaseActivityWeakReference;

        public BaseHandler(BaseActivity baseActivity) {
            super();
            mBaseActivityWeakReference = new WeakReference<>(baseActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseActivity baseActivity = mBaseActivityWeakReference.get();
            if (null != baseActivity) {
                baseActivity.handleMessage(msg);
            }
        }
    }

}
