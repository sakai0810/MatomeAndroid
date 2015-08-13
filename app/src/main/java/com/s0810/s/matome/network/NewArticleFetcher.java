package com.s0810.s.matome.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.s0810.s.matome.models.ArticleEntity;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by owner on 2015/08/04.
 */
public class NewArticleFetcher {


    //新着記事一覧URL
    static final String newArticleUrl = "http://mamamatome.appspot.com/light?&count=30";

    //次に読み込むべきページ番号
    private Integer pageNext = 1;

    //受信した記事リスト
    private List<ArticleEntity> newArticleList;

    //リクエストキュー
    private RequestQueue requestQueue;
    //コンテキスト
    private Context context;

    //Fetcherのリスナー
    public NewArticleFetcherCallBack listener;

    public NewArticleFetcher(Context context) {
        newArticleList = new ArrayList<ArticleEntity>();
        this.context = context;
    }

    /**
     * ページ番号を指定してフェッチを開始します
     */
    public void executeFetch(final int page) {

        //まず全てキャンセル
        cancelAllFetch();

        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(new JsonArrayRequest(Request.Method.GET, urlWithPageQuery(), "", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //成功
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<ArticleEntity>>(){}.getType();
                List<ArticleEntity> list = gson.fromJson(response.toString(), collectionType);
                newArticleList.addAll(list);
                listener.onNewArticleFetchSuccess(newArticleList);
                pageNext = page + 1;
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //エラー
                listener.onNewArticleFetchError(error);
            }
        }));

        requestQueue.start();
    }

    /**
     * 全ての通信をキャンセルします。
     */
    public void cancelAllFetch() {

        if (requestQueue == null) return;

        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    /**
     * 初回（ページ番号1）のフェッチを開始します
     */
    public void executeInitialFetch() {
        pageNext = 1;
        newArticleList.clear();
        executeFetch(pageNext);
    }

    /**
     * 更に読み込むを実行します
     */
    public void executeAddFetch() {
        executeFetch(pageNext);
    }


    /**
     * ページ番号からURLを生成します。
     * @return 生成したURL
     */
    private String urlWithPageQuery() {
        String retURL = newArticleUrl + "&page=" + pageNext.toString();
        return retURL;
    }

}

