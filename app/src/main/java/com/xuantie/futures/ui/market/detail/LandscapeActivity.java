package com.xuantie.futures.ui.market.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;

import com.flyco.tablayout.SlidingTabLayout;
import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseActivity;
import com.xuantie.futures.widget.custom.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/10.
 */

public class LandscapeActivity extends BaseActivity {
    @BindView(R.id.sl)
    SlidingTabLayout mSl;
    @BindView(R.id.vp)
    NoScrollViewPager mVp;
    private Unbinder mUnbinder;
    private String[] commodity = {"分时", "1分", "5分", "15分", "30分", "1时", "4时", "日","周","月"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landscape);
        mUnbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mVp.setAdapter(mAdapter);
        for (int i = 0; i < commodity.length; i++) {
            if(i==0){
                mFragments.add(MinuteFragment.newInstance());
            }else {
                mFragments.add(KlineFragment.newInstance());
            }

        }
        if (mFragments.size() > 0 && mFragments != null) {
            mSl.setViewPager(mVp, commodity,this, mFragments);
        }
        mSl.setCurrentTab(1);
    }


    //回到竖屏
    private void goPortrait() {
        finish();
        startActivity(new Intent(this, CommodityDetailActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        goPortrait();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return commodity[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
