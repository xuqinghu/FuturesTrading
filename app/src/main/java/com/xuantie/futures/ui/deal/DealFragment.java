package com.xuantie.futures.ui.deal;

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
import com.xuantie.futures.ui.deal.chengjiao.ChengJiaoFragment;
import com.xuantie.futures.ui.deal.chicang.ChiCangFragment;
import com.xuantie.futures.ui.deal.pingcang.PingCangFragment;
import com.xuantie.futures.ui.deal.weituo.WeiTuoFragment;
import com.xuantie.futures.ui.market.CommodityFragment;
import com.xuantie.futures.ui.market.MarketFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/9/26.
 */

public class DealFragment extends BaseFragment {
    @BindView(R.id.sl_deal)
    SlidingTabLayout mSlDeal;
    @BindView(R.id.vp_deal)
    ViewPager mVpDeal;
    Unbinder unbinder;
    private String[] commodity = {"持仓", "委托", "成交", "平仓"};
    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deal, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        mAdapter = new MyPagerAdapter(getFragmentManager());
        mVpDeal.setAdapter(mAdapter);
        mFragments.add(ChiCangFragment.newInstance());
        mFragments.add(WeiTuoFragment.newInstance());
        mFragments.add(ChengJiaoFragment.newInstance());
        mFragments.add(PingCangFragment.newInstance());
        if (mFragments.size() > 0 && mFragments != null) {
            mSlDeal.setViewPager(mVpDeal, commodity, getActivity(), mFragments);
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
}
