package com.xuantie.futures;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.netty.client.KlineNettyClient;
import com.netty.client.NettyClient;
import com.netty.client.NewNettyClient;
import com.netty.flatbuffers.FbBizMsg;
import com.netty.flatbuffers.FbFuturesQuotationList;
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
import com.xuantie.futures.utils.permission.IPermissionListener;
import com.xuantie.futures.utils.permission.PermissionHelper;

import java.util.ArrayList;
import java.util.List;

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
        PermissionHelper.getInstance().request(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new IPermissionListener() {
            @Override
            public void onAllowed() {

            }

            @Override
            public void onRefused(List<String> permissions) {

            }
        });
        NewNettyClient.getInstance().connect();
        NettyClient.getInstance().connect();
        KlineNettyClient.getInstance().connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
