package info.smemo.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import info.smemo.nbase.base.NBaseActivity;
import info.smemo.nbase.bean.SelectBean;
import info.smemo.nbase.ui.SelectDialog;
import info.smemo.nbase.util.SystemUtil;

public class MainActivity extends NBaseActivity {

    private TextView mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (TextView) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                list.add("test");
                list.add("test2");
                SelectDialog dialog = new SelectDialog(MainActivity.this);
                dialog.setStringList(list);
                dialog.setOnItemClickListener(new SelectDialog.OnItemClickListner() {
                    @Override
                    public void onClick(String value, SelectBean object, int position) {
                        SystemUtil.toastMessage(MainActivity.this, value);
                    }
                });
                dialog.show();
            }
        });
    }

}
