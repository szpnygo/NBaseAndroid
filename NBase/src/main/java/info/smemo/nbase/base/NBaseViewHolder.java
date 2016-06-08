package info.smemo.nbase.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by neo on 16/6/8.
 */
public class NBaseViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding mBinding;

    public NBaseViewHolder(View itemView) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
    }

    public ViewDataBinding getBinding() {
        return mBinding;
    }
}
