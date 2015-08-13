package com.s0810.s.matome.models;

/**
 * Created by owner on 2015/08/04.
 */
public class ArticleEntity {

    /**
     * 記事URL
     */
    private String articleUrl;

    /**
     *ブログ名
     */
    private String blogName;

    /**
     * 画像URL
     */
    private String imageUrl;

    /**
     * タイトル
     */
    private String title;

    /**
     * リソースID（imageUrlが設定されている場合はimageUrlが優先されます）
     */
    private int imageResId;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }


}
