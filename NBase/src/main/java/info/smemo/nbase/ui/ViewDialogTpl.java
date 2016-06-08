package info.smemo.nbase.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

import info.smemo.nbase.R;

/**
 * Created by neo on 16/6/8.
 */
public abstract class ViewDialogTpl extends MaterialDialog {

    private View dialogView;
    private int viewID;
    protected boolean setView = false;

    public ViewDialogTpl(Context context) {
        super(context);
        this.setCanceledOnTouchOutside(true);
        setSetView(true);
        setCanceledOnTouchOutside(true);
        setBackgroundResource(R.drawable.transparent);
    }

    abstract protected void initView(View view);

    protected void setContentView(@LayoutRes int id) {
        this.viewID = id;
        loadView();
    }

    public void setSetView(boolean setView) {
        this.setView = setView;
    }

    protected void loadView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        dialogView = inflater.inflate(viewID, null);
        initView(dialogView);
        if (setView) {
            this.setView(dialogView);
        } else {
            this.setContentView(dialogView);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    public boolean isShowing() {
        if (mAlertDialog == null)
            return false;
        return mAlertDialog.isShowing();
    }
}
