package info.smemo.demo;

import info.smemo.nbase.http.BaseHttpBean;
import info.smemo.nbase.http.annotation.HttpGetParameter;
import info.smemo.nbase.http.annotation.HttpPostTarget;
import info.smemo.nbase.http.annotation.HttpUnusedParam;

/**
 * Created by neo on 16/6/16.
 */
@HttpPostTarget("https://wxfl.ztgame.com/king/index.php/Player/Games/getGames.html")
public class HttpTestBean extends BaseHttpBean {

    @HttpGetParameter("id")
    public String gameid;

    @HttpGetParameter
    public int num;

    @HttpUnusedParam
    public String postText2;

    public HttpTestBean(String gameid) {
        this.gameid = gameid;
    }
}
