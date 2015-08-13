package com.s0810.s.matome.views.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.s0810.s.matome.R;
import com.s0810.s.matome.views.fragment.NewArticleFragment;

public class MainActivity extends AppCompatActivity {

    ///ツールバー
    private Toolbar toolbar;
    ///タブ
    private TabLayout tabLayout;
    ///ドロワー
    private DrawerLayout drawerLayout;
    ///ドロワートグル
    private ActionBarDrawerToggle mDrawerToggle;
    ///ViewPager
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* ツールバーの設定 */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setSupportActionBar(toolbar);

        //タブとドロワー初期化
        initTablayout();
        initDrawerToggle();
    }

    /**
     * タブを初期化します
     */
    private void initTablayout() {

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_caption_new));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_caption_popular));

        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(adapter);
        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * ドロワートグルを初期化します
     */
    private void initDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // アクションバー上のボタン選択時のハンドリング
        // ActionBarDrawerToggleにイベント渡す
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            NewArticleFragment newArticleFragment = new NewArticleFragment();
            return newArticleFragment;
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
                return  getString(R.string.tab_caption_new);
            } else if (position == 1) {
                return getString(R.string.tab_caption_popular);
            } else {
                return "";
            }

        }
    }
}
