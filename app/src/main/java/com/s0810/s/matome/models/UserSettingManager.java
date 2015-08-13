package com.s0810.s.matome.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.s0810.s.matome.views.adapter.ArticleAdapter;

/**
 * Created by sakai on 15/08/13.
 */
public class UserSettingManager {

    private final String ARTICLE_LIST_TYPE_KEY = "ARTICLE_LIST_TYPE";
    private final String DEFAULT_PREFERENCE_NAME ="DEFAULT";
    private Context context;

    public UserSettingManager(Context context) {
        this.context = context;
    }

    /**
     * ユーザの記事表示種別を設定に保存します
     * @param itemType 記事表示種別
     */
    public void setArticleListType(ArticleAdapter.ArticleItemType itemType) {
        SharedPreferences.Editor editor = defaultPreference().edit();

        switch (itemType) {
            case ARTICLE_ITEM_TYPE_BIG_IMAGE:
                editor.putInt(ARTICLE_LIST_TYPE_KEY, ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_BIG_IMAGE.ordinal());
                break;
            case ARTICLE_ITEM_TYPE_SMALL_IMAGE:
                editor.putInt(ARTICLE_LIST_TYPE_KEY, ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_SMALL_IMAGE.ordinal());
                break;
            case ARTICLE_ITEM_TYPE_TEXT_ONLY:
                editor.putInt(ARTICLE_LIST_TYPE_KEY, ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_TEXT_ONLY.ordinal());
        }
        editor.apply();
    }

    /**
     * ユーザの記事表示種別を取得します
     * @return ユーザが設定した記事表示種別。デフォルトはARTICLE_ITEM_TYPE_BIG_IMAGEです。
     */
    public ArticleAdapter.ArticleItemType getArticleItemType() {

        int type = defaultPreference().getInt(ARTICLE_LIST_TYPE_KEY, ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_BIG_IMAGE.ordinal());
        int typeBig = ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_BIG_IMAGE.ordinal();
        int typeSmall = ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_SMALL_IMAGE.ordinal();
        int typeTextOnly = ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_TEXT_ONLY.ordinal();

        if (type == typeBig) {
            return ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_BIG_IMAGE;
        } else if (type == typeSmall) {
            return ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_SMALL_IMAGE;
        } else if (type == typeTextOnly) {
            return ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_TEXT_ONLY;
        } else {
            return ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_BIG_IMAGE;
        }
    }

    /**
     * 記事表示種別の設定値が保存されているかを取得します
     * @return 設定値が保存されているかのBoolean値
     */
    public Boolean hasArticleItemTypeSetting() {
        int type = defaultPreference().getInt(ARTICLE_LIST_TYPE_KEY, 999);
        if (type == 999) {
            return false;
        } else {
            return true;
        }
    }

    private SharedPreferences defaultPreference() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
