package com.xuantie.futures.ui.market.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.wordplat.ikvstockchart.InteractiveKLineLayout1;
import com.wordplat.ikvstockchart.KLineHandler;
import com.wordplat.ikvstockchart.compat.ViewUtils;
import com.wordplat.ikvstockchart.entry.Entry;
import com.wordplat.ikvstockchart.entry.EntrySet;
import com.wordplat.ikvstockchart.entry.SizeColor;
import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseActivity;
import com.xuantie.futures.network.ResultSubject;
import com.xuantie.futures.network.RetrofitClient;
import com.xuantie.futures.network.bean.ResultModel1;
import com.xuantie.futures.network.bean.resp.KLineResp;
import com.xuantie.futures.network.helper.RxResultHelper1;
import com.xuantie.futures.network.helper.RxSchedulersHelper;
import com.xuantie.futures.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/15.
 */

public class DealActivity extends BaseActivity {
    @BindView(R.id.ll_back)
    LinearLayout mLlBack;
    @BindView(R.id.tv_commodity_name)
    TextView mTvCommodityName;
    @BindView(R.id.tv_commodity_contract)
    TextView mTvCommodityContract;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.sl_deal)
    SlidingTabLayout mSlDeal;
    @BindView(R.id.vp_deal)
    ViewPager mVpDeal;
    @BindView(R.id.kl)
    InteractiveKLineLayout1 mKl;
    @BindView(R.id.ll_pop)
    LinearLayout mLlPop;
    private Unbinder mUnbinder;
    private String[] time = {"1分", "5分", "15分", "30分", "1时", "4时", "日", "周", "月"};
    private ArrayList<String> list = new ArrayList<>();
    private TimeAdapter mAdapter;
    private String[] title = {"市价交易", "限价挂单"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mMyPagerAdapter;
    private EntrySet mEntrySet;
    private List<KLineResp> list1 = new ArrayList<>();
    //日线、1分钟线、5分钟线等等
    private String mKlineType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);
        mUnbinder = ButterKnife.bind(this);
        initSize();
        initView();
        initKline();
    }

    private void initSize() {
        SizeColor.xLabelSize = ViewUtils.dpTopx(this, 8);
        SizeColor.yLabelSize = ViewUtils.dpTopx(this, 7);
        SizeColor.markerTextSize = ViewUtils.dpTopx(this, 8);
        SizeColor.candleExtremumLabelSize = ViewUtils.dpTopx(this, 7);
    }

    private void initView() {
        for (int i = 0; i < time.length; i++) {
            list.add(time[i]);
        }
        mAdapter = new TimeAdapter(R.layout.item_time, list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRv.setLayoutManager(manager);
        mRv.setAdapter(mAdapter);
        mAdapter.selectPositon(0);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.selectPositon(position);
            }
        });
        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mVpDeal.setAdapter(mMyPagerAdapter);
        mFragments.add(MarketPriceFragment.newInstance());
        mFragments.add(LimitPriceFragment.newInstance());
        if (mFragments.size() > 0 && mFragments != null) {
            mSlDeal.setViewPager(mVpDeal, title, this, mFragments);
        }
    }

    private void initKline() {
        mKlineType = "m1";
        loadKLineData(false, "", 0);
        mKl.setKLineHandler(new KLineHandler() {
            @Override
            public void onLeftRefresh() {
                loadKLineData(true, "m1", list1.get(0).getDataTime());
            }

            @Override
            public void onRightRefresh() {

            }

            @Override
            public void onSingleTap(MotionEvent e, float x, float y) {

            }

            @Override
            public void onDoubleTap(MotionEvent e, float x, float y) {

            }

            @Override
            public void onHighlight(Entry entry, int entryIndex, float x, float y) {
                int width = mKl.getMeasuredWidth();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mLlPop.getLayoutParams();
                params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                if(x<width/2){
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                }else {
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                }
                mLlPop.setLayoutParams(params); //使layout更新
                mLlPop.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelHighlight() {
                mLlPop.setVisibility(View.GONE);
            }
        });
    }

    private void loadKLineData(final boolean isLoadMore, String klineType, long time) {
        RetrofitClient.getApi().getKline("NYMEX",
                "CL", "m1", Utils.nowMillisecondTimestamp()
                , 150)
                .compose(RxSchedulersHelper.<ResultModel1<List<KLineResp>>>ioMain())
                .compose(RxResultHelper1.<List<KLineResp>>handleResult())
                .compose(this.<List<KLineResp>>bindToLifecycle())
                .subscribe(new ResultSubject<List<KLineResp>>(this, false) {
                    @Override
                    public void onNext(List<KLineResp> kLineResps) {
                        if (!isLoadMore) {
                            if (kLineResps.size() < 150) {
                                mKl.getKLineView().setEnableLeftRefresh(false);
                            } else {
                                mKl.getKLineView().setEnableLeftRefresh(true);
                            }
                            Collections.reverse(kLineResps); // 倒序排列
                            list1.addAll(kLineResps);
                            mEntrySet = StockDataTest.parseKLineData(kLineResps, getTimeType());
                            mEntrySet.setSmallestFluctuation("0.01");
                            mEntrySet.computeStockIndex();
                            mKl.getKLineView().setEntrySet(mEntrySet);
                            mKl.getKLineView().notifyDataSetChanged();
                        } else {
                            for (int i = 0; i < kLineResps.size(); i++) {
                                list1.add(0, kLineResps.get(i));
                            }
                            mEntrySet = StockDataTest.parseKLineData(list1, getTimeType());
                            mEntrySet.computeStockIndex();
                            List<Entry> entries = insertEntries(kLineResps.size(), mEntrySet);
                            mKl.getKLineView().getRender().getEntrySet().insertFirst(entries);
                            mKl.getKLineView().notifyDataSetChanged();
                            mKl.getKLineView().refreshComplete(entries.size() > 0);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    private String getTimeType() {
        if (TextUtils.equals(mKlineType, "day") || TextUtils.equals(mKlineType, "week"))
            return "MM-dd";
        if (TextUtils.equals(mKlineType, "month")) return "MM";
        if (TextUtils.equals(mKlineType, "year")) return "yyyy";
        if (TextUtils.equals(mKlineType, "m240")) return "MM-dd HH:mm";
        return "HH:mm";
    }

    private List<Entry> insertEntries(int size, EntrySet entrySet) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            entries.add(entrySet.getEntryList().get(i));
        }
        return entries;
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

    @OnClick({R.id.ll_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
