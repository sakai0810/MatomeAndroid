package com.s0810.s.matome.network;

import com.android.volley.VolleyError;
import com.s0810.s.matome.models.ArticleEntity;

import java.util.List;

public interface NewArticleFetcherCallBack {
    /**
     * 通信が成功した時のコールバックです
     * @param responseList レスポンスのList
     */
    public void onNewArticleFetchSuccess(List<ArticleEntity> responseList);

    /**
     * 通信に失敗した時のコールバックです
     * @param error エラー内容
     */
    public void onNewArticleFetchError(VolleyError error);
}
