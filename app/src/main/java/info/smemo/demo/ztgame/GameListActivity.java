package info.smemo.demo.ztgame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import info.smemo.demo.BR;
import info.smemo.demo.R;
import info.smemo.nbase.adapter.NBaseBindingAdapter;
import info.smemo.nbase.base.NBaseAction;
import info.smemo.nbase.base.NBaseCompatActivity;
import info.smemo.nbase.util.AnnotationView;
import info.smemo.nbase.util.LogHelper;

/**
 * Created by neo on 16/6/22.
 */
@AnnotationView(R.layout.activity_game)
public class GameListActivity extends NBaseCompatActivity {

    @AnnotationView(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    private ArrayList<GameBean> gameList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBar(R.id.toolbar).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final NBaseBindingAdapter adapter = new NBaseBindingAdapter<>(gameList, BR.gameBean, R.layout.listitem_game);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);

        NBaseAction.get(GameBean.class, "https://wxfl.ztgame.com/king/index.php/Player/Games/getGames.html", new NBaseAction.HttpActionListListener<List<GameBean>>() {
            @Override
            public void success(List<GameBean> response) {
                for (GameBean bean : response)
                    gameList.add(bean);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void error(int code, String message, boolean inMain) {
                LogHelper.e("code:" + code + " msg:" + message);
            }
        }).execute();

    }


}
