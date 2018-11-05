package com.xuantie.futures;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.netty.client.NettyClient;
import com.netty.client.NewNettyClient;
import com.netty.flatbuffers.FbBizMsg;
import com.netty.flatbuffers.FbMsgCaptchaResp;
import com.netty.flatbuffers.FlatBufferBuilder;
import com.xuantie.futures.base.BaseActivity;
import com.xuantie.futures.bean.TabEntity;
import com.xuantie.futures.ui.deal.DealFragment;
import com.xuantie.futures.ui.market.MarketFragment;
import com.xuantie.futures.ui.mine.MineFragment;
import com.xuantie.futures.ui.ranking.RankingFragment;
import com.xuantie.futures.utils.Base64Util;
import com.xuantie.futures.utils.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tl)
    CommonTabLayout mTl;
    private Unbinder mUnbinder;
    private String[] mTitles = {"行情", "交易", "跟单", "我的"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int[] mIconSelectIds = {R.mipmap.market_selected, R.mipmap.deal_selected, R.mipmap.ranking_selected, R.mipmap.personage_selected};
    private int[] mIconUnselectIds = {R.mipmap.market_no_selected, R.mipmap.deal_no_selected, R.mipmap.ranking_no_selected, R.mipmap.personage_no_selected};
    private DealFragment mDealFragment = new DealFragment();
    private MarketFragment mMarketFragment = new MarketFragment();
    private RankingFragment mRankingFragment = new RankingFragment();
    private MineFragment mMineFragment = new MineFragment();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mFragments.add(mMarketFragment);
        mFragments.add(mDealFragment);
        mFragments.add(mRankingFragment);
        mFragments.add(mMineFragment);
        mTl.setTabData(mTabEntities, this, R.id.fl, mFragments);
        mTl.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(position==3){
                    StatusBarUtil.setColorNoTranslucent(MainActivity.this,getResources().getColor(R.color.red_cd1212));
                }else {
                    StatusBarUtil.setColorNoTranslucent(MainActivity.this,getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        initView();
    }

    private void initView() {
        NewNettyClient.getInstance().connect();
//        NettyClient.getInstance().connect();
        FlatBufferBuilder headbuilder = new FlatBufferBuilder();
//        int head_ofs = FbBizMsg.createFbBizMsg(headbuilder, (byte)'3' ,headbuilder.createString("test123456"),
//                headbuilder.createString("imtoken"),
//                headbuilder.createString(""),
//                headbuilder.createString("Q0001"),
//                0, 0, headbuilder.createString("20181026113000"), 0);
//        headbuilder.finish(head_ofs);
//        NettyClient.getInstance().sendQ(headbuilder.sizedByteArray());
                int head_yzm = FbBizMsg.createFbBizMsg(headbuilder, (byte)'3' ,headbuilder.createString("test123456"),
                headbuilder.createString("imtoken"),
                headbuilder.createString(""),
                headbuilder.createString("T1000"),
                0, 0, headbuilder.createString("20181026113000"), 0);
        headbuilder.finish(head_yzm);
        NewNettyClient.getInstance().sendQ(headbuilder.sizedByteArray());
        NewNettyClient.getInstance().getYzm(new NewNettyClient.YzmListen() {
            @Override
            public void getData(FbMsgCaptchaResp resp) {
                Base64Util.base64ToBitmap(resp.verifyCode());
                Log.d("YZM",resp.verifyCode()+"");
            }
        });
    }

    private void sendQ(String bts) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
