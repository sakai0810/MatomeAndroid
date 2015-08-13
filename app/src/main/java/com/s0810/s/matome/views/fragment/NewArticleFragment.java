package com.s0810.s.matome.views.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.s0810.s.matome.R;
import com.s0810.s.matome.models.ArticleEntity;
import com.s0810.s.matome.network.NewArticleFetcher;
import com.s0810.s.matome.network.NewArticleFetcherCallBack;
import com.s0810.s.matome.views.activity.ArticleWebActivity;
import com.s0810.s.matome.views.adapter.ArticleAdapter;
import com.s0810.s.matome.views.listener.EndlessRecyclerOnScrollListener;

import java.util.List;

/**
 * 新着記事一覧のフラグメント
 */
public class NewArticleFragment extends android.support.v4.app.Fragment implements NewArticleFetcherCallBack, ArticleAdapter.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private NewArticleFetcher fetcher;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    /**
     * 表示する記事リスト
     */
    private List<ArticleEntity> articleList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newarticle_list,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.new_article_recycler_view);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.red, R.color.green, R.color.blue, R.color.yellow);

        //Fetcherの作成及び通信開始
        initFetcherIfNeed();
        fetcher.executeInitialFetch();

        return view;
    }


    private void initFetcherIfNeed() {
        if (fetcher == null) {
            fetcher = new NewArticleFetcher(this.getActivity().getApplicationContext());
            fetcher.listener = this;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //アダプタの設定
        articleAdapter = new ArticleAdapter(this.getActivity());
        articleAdapter.setOnItemClickListener(this);

        //recyclerViewの設定
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(articleAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //更に読み込むリスナーの設定及び更に読み込む実装
        //TODO:更に読み込む中に引っ張って更新するとフラグがオフにならずにバグる
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                articleAdapter.setIsLoadMoreFailed(false);
                fetcher.executeAddFetch();
            }
        };
        recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
    }
    /**
     * フェッチが成功しました
     * @param responseList レスポンスのList
     */
    @Override
    public void onNewArticleFetchSuccess(List<ArticleEntity> responseList) {
        this.articleList = responseList;
        articleAdapter.updateArticleList(articleList);
        swipeRefreshLayout.setRefreshing(false);
        articleAdapter.setIsLoadMoreFailed(false);
        endlessRecyclerOnScrollListener.setCanLoad();
    }


    /**
     * フェッチが失敗しました
     * @param error エラー内容
     */
    @Override
    public void onNewArticleFetchError(VolleyError error) {
        swipeRefreshLayout.setRefreshing(false);
        articleAdapter.setIsLoadMoreFailed(true);
        if (this.getView() != null) {
            Snackbar.make(this.getView(), R.string.error_network_snackbar, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * アイテムがタップされました
     * @param adapter
     * @param position
     * @param articleEntity
     */
    @Override
    public void onArticleClick(ArticleAdapter adapter, View view, int position, ArticleEntity articleEntity) {
        Intent intent = new Intent(this.getActivity(), ArticleWebActivity.class);
        intent.putExtra("url", articleEntity.getArticleUrl());
        View fromView = view.findViewById(R.id.article_image_view);
        Activity activity = this.getActivity();
        startActivity(intent);
//        ActivityCompat.startActivity(activity,intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, fromView, "image").toBundle());
    }

    /**
     * 更に読み込むがタップされました
     * @param adapter
     */
    @Override
    public void onLoadClick(ArticleAdapter adapter, View view) {
        fetcher.executeAddFetch();
        articleAdapter.setIsLoadMoreFailed(false);
    }

    /**
     * スワイプにより更新されました
     */
    @Override
    public void onRefresh() {
        initFetcherIfNeed();
        fetcher.executeInitialFetch();
    }


}
