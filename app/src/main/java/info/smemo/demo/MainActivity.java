package info.smemo.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import info.smemo.nbase.base.NBaseActivity;
import info.smemo.nbase.http.HttpUtil;

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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtil.get("");
                    }
                }).start();

            }
        });
    }


}
