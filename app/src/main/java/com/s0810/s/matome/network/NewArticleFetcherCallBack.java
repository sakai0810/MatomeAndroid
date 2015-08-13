package com.s0810.s.matome.network;

import com.android.volley.VolleyError;
import com.s0810.s.matome.models.ArticleEntity;

import java.util.List;

public interface NewArticleFetcherCallBack {
    /**
     * 通信が成功した時のコールバックです
     * @param responseList レスポンスのList
     */
    void onNewArticleFetchSuccess(List<ArticleEntity> responseList);

    /**
     * 通信に失敗した時のコールバックです
     * @param error エラー
     */
    void onNewArticleFetchError(VolleyError error);
}
