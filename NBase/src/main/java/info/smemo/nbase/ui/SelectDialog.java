package info.smemo.nbase.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import info.smemo.nbase.BR;
import info.smemo.nbase.R;
import info.smemo.nbase.app.AppConstant;
import info.smemo.nbase.base.NBaseBindingAdapter;
import info.smemo.nbase.bean.SelectBean;

/**
 * Created by neo on 16/6/8.
 */
public class SelectDialog extends ViewDialogTpl implements AppConstant {

    private ArrayList<SelectBean> selectBeanArrayList;
    public NBaseBindingAdapter<SelectBean> selectAdapter;
    public String selectValue = "";

    public SelectDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_select);
    }

    @Override
    protected void initView(View view) {
        selectBeanArrayList = new ArrayList<>();
        selectAdapter = new NBaseBindingAdapter<>(selectBeanArrayList, BR.selectBean, R.layout.listitem_select);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(selectAdapter);

        selectAdapter.setListener(new NBaseBindingAdapter.OnAdapterClickListener() {
            @Override
            public void onClick(View view, int position, Object object) {

            }
        });
    }

    public void setOnItemClickListener(final OnItemClickListner onItemClickListener) {
        selectAdapter.setListener(new NBaseBindingAdapter.OnAdapterClickListener() {
            @Override
            public void onClick(View view, int position, Object object) {
                SelectBean bean = (SelectBean) object;
                selectValue = bean.title;
                if (null != onItemClickListener)
                    onItemClickListener.onClick(bean.title, bean, position);
            }
        });
    }


    public void setSelectList(ArrayList<SelectBean> beans) {
        selectBeanArrayList.clear();
        for (SelectBean bean : beans)
            selectBeanArrayList.add(bean);
        selectAdapter.notifyDataSetChanged();
    }

    public void setStringList(ArrayList<String> beans) {
        if (beans == null || beans.size() == 0) {
            selectBeanArrayList.clear();
            selectAdapter.notifyDataSetChanged();
            selectValue = "";
            return;
        }
        selectBeanArrayList.clear();
        for (String bean : beans)
            selectBeanArrayList.add(new SelectBean(bean));
        selectAdapter.notifyDataSetChanged();
    }

    public String getSelectValue() {
        return selectValue;
    }

    public interface OnItemClickListner {

        void onClick(String value, SelectBean object, int position);
    }

}