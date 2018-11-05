package com.xuantie.futures.ui.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;
import com.xuantie.futures.ui.market.add.AddOptionalActivity;
import com.xuantie.futures.ui.market.edit.EditOptionalActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/9/26.
 */

public class MarketFragment extends BaseFragment {
    @BindView(R.id.sl_market)
    SlidingTabLayout mSlMarket;
    @BindView(R.id.vp_market)
    ViewPager mVpMarket;
    Unbinder unbinder;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private String[] commodity = {"自选", "NYMEX", "COMEX", "EUREX", "SGX", "HKEX", "CME", "CBOT"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mAdapter = new MyPagerAdapter(getFragmentManager());
        mVpMarket.setAdapter(mAdapter);
        for (int i = 0; i < commodity.length; i++) {
            mFragments.add(CommodityFragment.newInstance());
        }
        if (mFragments.size() > 0 && mFragments != null) {
            mSlMarket.setViewPager(mVpMarket, commodity, getActivity(), mFragments);
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
            return commodity[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_market_edit, R.id.rl_market_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_market_edit:
                startActivity(new Intent(mActivity, EditOptionalActivity.class));
                break;
            case R.id.rl_market_add:
                startActivity(new Intent(mActivity, AddOptionalActivity.class));
                break;
        }
    }
}
