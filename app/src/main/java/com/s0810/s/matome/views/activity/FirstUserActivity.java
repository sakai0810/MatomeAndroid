package com.s0810.s.matome.views.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.s0810.s.matome.R;
import com.s0810.s.matome.models.ArticleEntity;
import com.s0810.s.matome.models.UserSettingManager;
import com.s0810.s.matome.views.adapter.ArticleAdapter;
import com.s0810.s.matome.views.fragment.FirstUserArticleTypeFragment;
import java.util.ArrayList;

public class FirstUserActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private Button acceptDesignButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_user);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        acceptDesignButton = (Button) findViewById(R.id.button_accept_design);
        acceptDesignButton.setOnClickListener(this);

        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private ArrayList createDummyArticleForBigImage() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            ArticleEntity entity = new ArticleEntity();
            entity.setBlogName(getString(R.string.dummy_blog_name));
            entity.setTitle(getString(R.string.dummy_title_big_image));
            entity.setImageResId(R.drawable.dummy_article_image);
            list.add(entity);
        }
        return list;
    }

    private ArrayList createDummyArticleForSmallImage() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            ArticleEntity entity = new ArticleEntity();
            entity.setBlogName(getString(R.string.dummy_blog_name));
            entity.setTitle(getString(R.string.dummy_title_small_image));
            entity.setImageResId(R.drawable.dummy_article_image);
            list.add(entity);
        }
        return list;
    }

    private ArrayList createDummyArticleForTextOnly() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            ArticleEntity entity = new ArticleEntity();
            entity.setBlogName(getString(R.string.dummy_blog_name));
            entity.setTitle(getString(R.string.dummy_title_text_only));
            list.add(entity);

        }
        return list;
    }

    @Override
    public void onClick(View v) {
        UserSettingManager userSettingManager = new UserSettingManager(this.getApplicationContext());
        switch (viewPager.getCurrentItem()) {
            case 0:
                userSettingManager.setArticleListType(ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_BIG_IMAGE);
                break;
            case 1:
                userSettingManager.setArticleListType(ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_SMALL_IMAGE);
                break;
            case 2:
                userSettingManager.setArticleListType(ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_TEXT_ONLY);
            default:
                break;
        }
        finish();
    }

    /**
     * タブを切り替えるFratmentPagerAdapterです
     */
    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(final int position) {
            FirstUserArticleTypeFragment firstUserArticleTypeFragment = new FirstUserArticleTypeFragment();
            switch (position) {
                case 0:
                    firstUserArticleTypeFragment.setArticleList(createDummyArticleForBigImage());
                    firstUserArticleTypeFragment.setArticleItemType(ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_BIG_IMAGE);
                    return firstUserArticleTypeFragment;
                case 1:
                    firstUserArticleTypeFragment.setArticleList(createDummyArticleForSmallImage());
                    firstUserArticleTypeFragment.setArticleItemType(ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_SMALL_IMAGE);
                    return firstUserArticleTypeFragment;
                case 2:
                    firstUserArticleTypeFragment.setArticleList(createDummyArticleForTextOnly());
                    firstUserArticleTypeFragment.setArticleItemType(ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_TEXT_ONLY);
                    return firstUserArticleTypeFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}
