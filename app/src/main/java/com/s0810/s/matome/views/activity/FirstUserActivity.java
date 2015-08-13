package com.s0810.s.matome.views.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.s0810.s.matome.R;
import com.s0810.s.matome.models.ArticleEntity;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList createDummyArticleForBigImage() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            ArticleEntity entity = new ArticleEntity();
            entity.setBlogName(getString(R.string.dummy_blog_name));
            entity.setTitle(getString(R.string.dummy_title_big_image));
            entity.setImageUrl("http://www.lirent.net/wp-content/uploads/2014/10/Android-Lollipop-wallpapers-p-800x500.png");
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
            entity.setImageUrl("http://www.lirent.net/wp-content/uploads/2014/10/Android-Lollipop-wallpapers-p-800x500.png");
            list.add(entity);

        }
        return list;
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    /**
     * タブを切り替えるFratmentPagerAdapterです
     */
    private class PagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(final int position) {
            FirstUserArticleTypeFragment bigImageArticleListFragment;
            switch (position) {
                case 0:
                    bigImageArticleListFragment = new FirstUserArticleTypeFragment();
                    bigImageArticleListFragment.setArticleList(createDummyArticleForBigImage());
                    bigImageArticleListFragment.setArticleItemType(ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_BIG_IMAGE);
                    return bigImageArticleListFragment;
                case 1:
                    bigImageArticleListFragment = new FirstUserArticleTypeFragment();
                    bigImageArticleListFragment.setArticleList(createDummyArticleForSmallImage());
                    bigImageArticleListFragment.setArticleItemType(ArticleAdapter.ArticleItemType.ARTICLE_ITEM_TYPE_SMALL_IMAGE);
                    return bigImageArticleListFragment;
                default:
                    return null;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return getString(R.string.tab_caption_new);
            } else if (position == 1) {
                return getString(R.string.tab_caption_popular);
            } else {
                return "";
            }

        }
    }

}
