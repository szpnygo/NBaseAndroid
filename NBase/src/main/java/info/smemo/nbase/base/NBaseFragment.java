package info.smemo.nbase.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.lang.ref.WeakReference;

import info.smemo.nbase.app.AppConstant;

/**
 * Created by neo on 16/6/7.
 */
public class NBaseFragment extends Fragment implements AppConstant{

    protected FragmentManager mFragmentManager;

    protected ProgressDialog mProgressDialog;
    protected final BaseHandler mBaseHandler = new BaseHandler(this);

    private static final int SHOW_PROGRESS_DIALOG = 0x110001;
    private static final int DISMISS_PROGRESS_DIALOG = 0X110002;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragmentManager = getChildFragmentManager();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
            mProgressDialog = new ProgressDialog(getContext());
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
        Message message = new Message();
        message.what = DISMISS_PROGRESS_DIALOG;
        mBaseHandler.sendMessage(message);
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
            case DISMISS_PROGRESS_DIALOG:
                destoryProgressDialog();
                break;
        }
    }

    protected static class BaseHandler extends Handler {

        private final WeakReference<NBaseFragment> mBaseFragmentWeakReference;

        public BaseHandler(NBaseFragment baseFragment) {
            super();
            mBaseFragmentWeakReference = new WeakReference<>(baseFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NBaseFragment baseFragment = mBaseFragmentWeakReference.get();
            if (null != baseFragment) {
                baseFragment.handleMessage(msg);
            }
        }
    }

}
