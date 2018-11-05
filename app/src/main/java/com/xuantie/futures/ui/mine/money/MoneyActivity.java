package com.xuantie.futures.ui.mine.money;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseActivity;
import com.xuantie.futures.ui.mine.money.golden.GoldenFragment;
import com.xuantie.futures.ui.mine.money.record.RecordFragment;
import com.xuantie.futures.ui.mine.money.withdraw.WithdrawFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/9.
 */

public class MoneyActivity extends BaseActivity {

    @BindView(R.id.sl_money)
    SlidingTabLayout mSlMoney;
    @BindView(R.id.vp_money)
    ViewPager mVpMoney;
    private Unbinder mUnbinder;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private String[] title = {"入金", "出金", "资金记录"};
    /**
     * 0--入金
     * 1--出金
     */
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        mUnbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        type = getIntent().getIntExtra("type", 0);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mVpMoney.setAdapter(mAdapter);
        mFragments.add(GoldenFragment.newInstance());
        mFragments.add(WithdrawFragment.newInstance());
        mFragments.add(RecordFragment.newInstance());
        if (mFragments.size() > 0 && mFragments != null) {
            mSlMoney.setViewPager(mVpMoney, title, this, mFragments);
        }
        if (type == 0) {
            mSlMoney.setCurrentTab(0);
        } else {
            mSlMoney.setCurrentTab(1);
        }
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
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }
}
