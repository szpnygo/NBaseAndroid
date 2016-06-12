package info.smemo.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import info.smemo.nbase.base.NBaseAction;
import info.smemo.nbase.base.NBaseActivity;
import info.smemo.nbase.util.LogHelper;
import okhttp3.Response;

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
                NBaseAction.get("https://api.smemo.info/api.php/v2/Backup/lists", new NBaseAction.HttpActionListener() {
                    @Override
                    public void success(Response response, String body) {
                        LogHelper.e(TAG, body);
                    }

                    @Override
                    public void error(int code, String message) {
                        LogHelper.e(TAG, message);
                    }

                    @Override
                    public void failure(String message) {
                        LogHelper.e(TAG, message);
                    }
                });

            }
        });
    }


}
