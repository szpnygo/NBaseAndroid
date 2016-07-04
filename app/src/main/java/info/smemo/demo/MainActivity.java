package info.smemo.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import info.smemo.demo.down.GameDownActivity;
import info.smemo.demo.nasa.NasaApodActivity;
import info.smemo.demo.ztgame.GameListActivity;
import info.smemo.nbase.adapter.NBaseBindingAdapter;
import info.smemo.nbase.app.NBaseConfig;
import info.smemo.nbase.base.NBaseCompatActivity;
import info.smemo.nbase.util.AnnotationView;

@AnnotationView(R.layout.activity_main)
public class MainActivity extends NBaseCompatActivity {

    @AnnotationView(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        NBaseConfig.getInstance().setNbaseTag("NBaseDemo").setAllowLogFile(false);

        ArrayList<MyMenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MyMenuItem("NASA APOD", new Intent(this, NasaApodActivity.class)));
        menuItems.add(new MyMenuItem("ZT GAME LIST", new Intent(this, GameListActivity.class)));
        menuItems.add(new MyMenuItem("DOWN GAME", new Intent(this, GameDownActivity.class)));

        NBaseBindingAdapter adapter = new NBaseBindingAdapter<>(menuItems, info.smemo.demo.BR.menuItem, R.layout.listitem_menu);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        adapter.setListener(new NBaseBindingAdapter.OnAdapterClickListener<MyMenuItem>() {
            @Override
            public void onClick(View view, int position, MyMenuItem object) {
                startActivity(object.mIntent);
            }
        });
    }


}
