package com.xuantie.futures.ui.market.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.netty.client.NettyClient;
import com.netty.flatbuffers.FbFuturesQuotation;
import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseActivity;
import com.xuantie.futures.bean.TabEntity;
import com.xuantie.futures.ui.market.OptionalActivity;
import com.xuantie.futures.utils.CommonUtils;

import java.math.BigDecimal;
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
    @BindView(R.id.tv_last_price)
    TextView mTvLastPrice;
    @BindView(R.id.tv_bidPrice)
    TextView mTvBidPrice;
    @BindView(R.id.tv_askPrice)
    TextView mTvAskPrice;
    @BindView(R.id.tv_highestPrice)
    TextView mTvHighestPrice;
    @BindView(R.id.tv_lowestPrice)
    TextView mTvLowestPrice;
    @BindView(R.id.tv_priceLimit)
    TextView mTvPriceLimit;
    @BindView(R.id.tv_upsDowns)
    TextView mTvUpsDowns;
    private Unbinder mUnbinder;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] commodity = {"分时", "1分", "5分", "15分", "30分", "1时", "日"};
    private String goodsName;
    private String contractNo;
    private String goodsNo;
    //昨结
    private float preSettlementPrice;
    private int decimalPlaces;
    private double minChange;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private KlineFragment mKlineFragment;
    //推送过来的数据
    private FbFuturesQuotation mFbFuturesQuotation;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (mTvLastPrice != null)
                        mTvLastPrice.setText(String.valueOf(mFbFuturesQuotation.LastPrice()));
                    mTvBidPrice.setText(String.valueOf(mFbFuturesQuotation.BidPrice1()));
                    mTvAskPrice.setText(String.valueOf(mFbFuturesQuotation.AskPrice1()));
                    mTvHighestPrice.setText(String.valueOf(mFbFuturesQuotation.HighestPrice()));
                    mTvLowestPrice.setText(String.valueOf(mFbFuturesQuotation.LowestPrice()));
                    updateColor(mFbFuturesQuotation.LastPrice());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_detail);
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    private void updateColor(float lastPrice) {
        //涨跌额＝最新价－昨日结算价
        BigDecimal bigDecimalPrice = new BigDecimal(lastPrice);
        BigDecimal bigDecimalSettle = new BigDecimal(preSettlementPrice);
        float upsDowns = bigDecimalPrice.subtract(bigDecimalSettle).floatValue();
        //涨跌幅＝（最新价－昨日结算价）÷昨日结算价×100%
        float priceLimit = upsDowns / preSettlementPrice * 100;
        mTvUpsDowns.setText(CommonUtils.decimalplace(String.valueOf(upsDowns),decimalPlaces));
        if(lastPrice>preSettlementPrice){
            mTvPriceLimit.setText("+"+ CommonUtils.formatAmt(priceLimit)+"%");
            mTvPriceLimit.setTextColor(getResources().getColor(R.color.red));
            mTvLastPrice.setTextColor(getResources().getColor(R.color.red));
            mTvUpsDowns.setTextColor(getResources().getColor(R.color.red));
        }else {
            mTvPriceLimit.setText(CommonUtils.formatAmt(priceLimit)+"%");
            mTvPriceLimit.setTextColor(getResources().getColor(R.color.green));
            mTvLastPrice.setTextColor(getResources().getColor(R.color.green));
            mTvUpsDowns.setTextColor(getResources().getColor(R.color.green));
        }

    }

    private void initView() {
        goodsName = getIntent().getStringExtra("goodsName");
        contractNo = getIntent().getStringExtra("contractNo");
        goodsNo = getIntent().getStringExtra("goodsNo");
        preSettlementPrice = getIntent().getFloatExtra("PreSettlementPrice", 0);
        float lastPrice = getIntent().getFloatExtra("LastPrice", 0);
        mTvCommodityName.setText(goodsName);
        mTvCommodityContract.setText(contractNo);
        mTvLastPrice.setText(String.valueOf(lastPrice));
        mTvBidPrice.setText(getIntent().getStringExtra("BidPrice1"));
        mTvAskPrice.setText(getIntent().getStringExtra("AskPrice1"));
        mTvHighestPrice.setText(getIntent().getStringExtra("HighestPrice"));
        mTvLowestPrice.setText(getIntent().getStringExtra("LowestPrice"));
        decimalPlaces = getIntent().getIntExtra("decimalPlaces",0);
        minChange = getIntent().getDoubleExtra("minChange",0);
        updateColor(lastPrice);
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
        mCl.setTabData(mTabEntities, this, R.id.fl, mFragments);
        mCl.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position > 0) {
                    mKlineFragment = (KlineFragment) mFragments.get(position);
                    if (position == 1) {
                        mKlineFragment.getKlineData("1_1");
                    } else if (position == 2) {
                        mKlineFragment.getKlineData("1_5");
                    } else if (position == 3) {
                        mKlineFragment.getKlineData("1_15");
                    } else if (position == 4) {
                        mKlineFragment.getKlineData("1_30");
                    } else if (position == 5) {
                        mKlineFragment.getKlineData("1_60");
                    } else {
                        mKlineFragment.getKlineData("3_1");
                    }
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void initData() {
        NettyClient.getInstance().getMarketData(new NettyClient.MarketListen() {
            @Override
            public void success(FbFuturesQuotation resp) {
                Log.d("FbFuturesQuotation", resp.OpenPrice() + "");
                if (TextUtils.equals(goodsNo, resp.GoodsNo())) {
                    mFbFuturesQuotation = resp;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void fail(String msg) {

            }
        });
    }

    public String getcontractNo() {
        return contractNo;
    }

    public int getDecimalPlaces() {return decimalPlaces;}

    public double getMinChange() {return minChange;}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        handler.removeCallbacksAndMessages(null);
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
