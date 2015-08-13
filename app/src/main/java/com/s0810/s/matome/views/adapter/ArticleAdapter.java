package com.s0810.s.matome.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.s0810.s.matome.R;
import com.s0810.s.matome.models.ArticleEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by owner on 2015/07/27.
 */
public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private enum ViewType {
        VIEW_TYPE_ARTICLE,
        VIEW_TYPE_LOADING,
    }

    private List<ArticleEntity> articleList;
    private OnItemClickListener onItemClickListener;
    private RecyclerView recyclerView;
    private Context context;

    //更に読み込むが失敗したかどうか
    private Boolean isLoadMoreFailed = false;

    /**
     * 記事のViewHolder
     */
    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView descriptionTextView;
        public ImageView imageView;

        public ArticleViewHolder(View v){
            super(v);
            titleTextView = (TextView)v.findViewById(R.id.article_title_text_view);
            descriptionTextView = (TextView)v.findViewById(R.id.article_description_text_view);
            imageView = (ImageView)v.findViewById(R.id.article_image_view);
        }
    }

    /**
     * 読み込み中のViewHolder
     */
    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout failedLayout;
        public LinearLayout loadingLayout;
        public LoadingViewHolder(View v) {
            super(v);
            failedLayout = (LinearLayout)v.findViewById(R.id.error_load_more_layout);
            loadingLayout = (LinearLayout)v.findViewById(R.id.load_more_progress_layout);
        }
    }

    public ArticleAdapter(Context context) {
        this.context = context;
    }

    /**
     * 記事リストを更新します。更新すると直ちにnotifyDataSetChangedが発行されます。
     * @param articleList 更新する新しい記事リスト
     */
    public void updateArticleList(List<ArticleEntity> articleList) {
        this.articleList = articleList;
        notifyDataSetChanged();
    }

    /**
     * 更に読み込むが失敗したかどうかを設定します。
     * 設定するとその内容に応じてViewも切り替えます
     * @param isLoadMoreFailed 更に読み込むが失敗したかどうか
     */
    public void setIsLoadMoreFailed(Boolean isLoadMoreFailed) {
        this.isLoadMoreFailed = isLoadMoreFailed;
        notifyDataSetChanged();
    }

    /**
     * クリックされた時のリスナーを設定します。
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == articleList.size()) {
            return ViewType.VIEW_TYPE_LOADING.ordinal();
        } else {
            return  ViewType.VIEW_TYPE_ARTICLE.ordinal();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType == ViewType.VIEW_TYPE_ARTICLE.ordinal()) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_item_row, viewGroup, false);
            v.setOnClickListener(this);
            return new ArticleViewHolder(v);
        } else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loading_item_row, viewGroup, false);
            v.setOnClickListener(this);
            return new LoadingViewHolder(v);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //読み込み中タイプの場合
        if (holder.getItemViewType() == ViewType.VIEW_TYPE_LOADING.ordinal()) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            if (isLoadMoreFailed) {
                loadingViewHolder.loadingLayout.setVisibility(View.GONE);
                loadingViewHolder.failedLayout.setVisibility(View.VISIBLE);
            } else {
                loadingViewHolder.loadingLayout.setVisibility(View.VISIBLE);
                loadingViewHolder.failedLayout.setVisibility(View.GONE);
            }
        }
        else
        //記事タイプの場合
        {

            //記事タイプの場合各種表示内容の設定を行う
            ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;

            ArticleEntity article = articleList.get(position);
            articleViewHolder.titleTextView.setText(article.getTitle());
            articleViewHolder.descriptionTextView.setText(article.getBlogName());

            //articleViewHolder.titleTextView.setText("テストのタイトル");
            //articleViewHolder.descriptionTextView.setText("テスト");


            if (article.getImageUrl() != null && !"".equals(article.getImageUrl())) {
                articleViewHolder.imageView.setVisibility(View.VISIBLE);
                Picasso.with(context).load(article.getImageUrl()).fit().centerCrop().into(articleViewHolder.imageView);
                //Picasso.with(context).load(article.getImageUrl()).resize(1, 1).centerCrop().into(articleViewHolder.imageView);
            } else {
                articleViewHolder.imageView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (articleList == null) {
            return 0;
        } else {
            return articleList.size() + 1;
        }
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            int position = recyclerView.getChildAdapterPosition(v);

            //最後
            if (position == this.getItemCount() - 1) {
                onItemClickListener.onLoadClick(this, v);
            } else {
                ArticleEntity item = articleList.get(position);
                onItemClickListener.onArticleClick(this, v, position, item);
            }
        }
    }

    public interface OnItemClickListener {
        void onArticleClick(ArticleAdapter adapter, View view, int position, ArticleEntity articleEntity);

        void onLoadClick(ArticleAdapter adapter, View view);
    }
}
