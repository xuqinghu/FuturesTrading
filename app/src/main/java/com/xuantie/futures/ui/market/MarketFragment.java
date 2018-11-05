package com.xuantie.futures.ui.market;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.netty.client.NewNettyClient;
import com.netty.flatbuffers.FbBizMsg;
import com.netty.flatbuffers.FbMsgGoodInfo;
import com.netty.flatbuffers.FbMsgGoodsInfoList;
import com.netty.flatbuffers.FlatBufferBuilder;
import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;
import com.xuantie.futures.ui.market.add.AddOptionalActivity;
import com.xuantie.futures.ui.market.edit.EditOptionalActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String [] marketName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        mAdapter = new MyPagerAdapter(getFragmentManager());
        mVpMarket.setAdapter(mAdapter);
    }

    private void initData(){
        sendGoodMsg();
        NewNettyClient.getInstance().getGoods(new NewNettyClient.GoodsListen() {
            @Override
            public void success(FbMsgGoodsInfoList resp) {
                List<FbMsgGoodInfo> list = new ArrayList<>();
                for(int i=0;i<resp.goodsInfoListLength();i++){
                    list.add(resp.goodsInfoList(i));
                }
                getMarketName(list);
            }

            @Override
            public void fail() {

            }
        });
    }

    //获取交易所名称
    private void getMarketName(List<FbMsgGoodInfo> beans){
        Set<String> set = new HashSet();
        for (int i = 0; i < beans.size(); i++) {
            set.add(beans.get(i).marketName());
        }
        marketName = new String[set.size()];
        int j = 0;
        for(String name:set){
            marketName [j] = name;
            j++;
        }
        for (int i = 0; i < marketName.length; i++) {
            CommodityFragment fragment = new CommodityFragment(beans,marketName[i]);
            mFragments.add(fragment);
        }
        if (mFragments.size() > 0 && mFragments != null) {
            mSlMarket.setViewPager(mVpMarket, marketName, getActivity(), mFragments);
        }
    }

    private void sendGoodMsg(){
        FlatBufferBuilder headbuilder = new FlatBufferBuilder();
        int head_good = FbBizMsg.createFbBizMsg(headbuilder, (byte) '3', headbuilder.createString("test123456"),
                headbuilder.createString("imtoken"),
                headbuilder.createString(""),
                headbuilder.createString("C1001"),
                0, 0, headbuilder.createString("20181026113000"), 0);
        headbuilder.finish(head_good);
        NewNettyClient.getInstance().sendQ(headbuilder.sizedByteArray());
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
            return marketName[position];
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
