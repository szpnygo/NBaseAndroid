package info.smemo.demo.nasa;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import info.smemo.demo.R;
import info.smemo.demo.databinding.ActivityScrollingBinding;
import info.smemo.nbase.base.NBaseAction;
import info.smemo.nbase.base.NBaseCompatActivity;
import info.smemo.nbase.util.AnnotationView;
import info.smemo.nbase.util.LogHelper;

public class NasaApodActivity extends NBaseCompatActivity {

    private ActivityScrollingBinding binding;

    @AnnotationView(R.id.fab)
    private FloatingActionButton fab;

    @Override
    protected void onCreateDataBinding() {
        super.onCreateDataBinding();
        binding = createContentView(R.layout.activity_scrolling);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (mToolbar != null)
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        NasaAction.getApod(new NBaseAction.HttpActionDataListener<NasaApodBean>() {
            @Override
            public void success(NasaApodBean response) {
                binding.setApodBean(response);
            }

            @Override
            public void error(int code, String message, boolean inMain) {
                LogHelper.e("code:" + code + " msg" + message);
            }
        });

    }
}
