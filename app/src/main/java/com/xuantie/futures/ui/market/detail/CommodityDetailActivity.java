package com.xuantie.futures.ui.market.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseActivity;
import com.xuantie.futures.bean.TabEntity;
import com.xuantie.futures.ui.market.OptionalActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/8.
 */

public class CommodityDetailActivity extends BaseActivity {
    @BindView(R.id.tv_commodity_contract)
    TextView mTvCommodityContract;
    @BindView(R.id.tv_commodity_name)
    TextView mTvCommodityName;
    @BindView(R.id.cl)
    CommonTabLayout mCl;
    private Unbinder mUnbinder;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] commodity = {"分时", "1分", "5分", "15分", "30分", "1时", "日"};
    private String goodsName;
    private String contractNo;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private KlineFragment mKlineFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_detail);
        mUnbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        goodsName = getIntent().getStringExtra("goodsName");
        contractNo = getIntent().getStringExtra("contractNo");
        mTvCommodityName.setText(goodsName);
        mTvCommodityContract.setText(contractNo);
        for (int i = 0; i < commodity.length; i++) {
            if (i == 0) {
                mFragments.add(MinuteFragment.newInstance());
            } else {
                mFragments.add(KlineFragment.newInstance());
            }

        }
        for (int i = 0; i < commodity.length; i++) {
            mTabEntities.add(new TabEntity(commodity[i]));
        }
        mCl.setTabData(mTabEntities,this,R.id.fl,mFragments);
        mCl.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(position>0){
                    mKlineFragment = (KlineFragment) mFragments.get(position);
                    if(position==1){
                        mKlineFragment.getKlineData("1_1");
                    }else if(position==2){
                        mKlineFragment.getKlineData("1_5");
                    }else if(position==3){
                        mKlineFragment.getKlineData("1_15");
                    }else if(position==4){
                        mKlineFragment.getKlineData("1_30");
                    }else if(position==5){
                        mKlineFragment.getKlineData("1_60");
                    }else {
                        mKlineFragment.getKlineData("3_1");
                    }
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    public String getcontractNo() {
        return contractNo;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick({R.id.ll_back, R.id.rl_to_optional, R.id.tv_empty, R.id.tv_more, R.id.tv_entry_orders})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_to_optional:
                startIntentLogin(new Intent(this, OptionalActivity.class));
                break;
            case R.id.tv_empty:
                startActivity(new Intent(this, DealActivity.class));
                break;
            case R.id.tv_more:
                startActivity(new Intent(this, DealActivity.class));
                break;
            case R.id.tv_entry_orders:
                startActivity(new Intent(this, DealActivity.class));
                break;
        }
    }
}
