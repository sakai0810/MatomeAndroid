package com.s0810.s.matome.views.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.s0810.s.matome.R;
import com.s0810.s.matome.views.adapter.ArticleAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstUserArticleTypeFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private List articleList;
    private ArticleAdapter articleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArticleAdapter.ArticleItemType articleItemType;

    public FirstUserArticleTypeFragment() {
        // Required empty public constructor
    }


    public void setArticleList(List articleList) {
        this.articleList = articleList;
    }

    public void setArticleItemType(ArticleAdapter.ArticleItemType itemType) {
        this.articleItemType = itemType;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_user_big_image_article_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.new_article_recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        //アダプタの設定
        articleAdapter = new ArticleAdapter(this.getActivity());
        articleAdapter.setArticleItemType(articleItemType);
        articleAdapter.updateArticleList(articleList);

        //recyclerViewの設定
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(articleAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
}
