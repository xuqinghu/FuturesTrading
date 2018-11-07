package com.xuantie.futures.ui.market.detail;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netty.client.KlineNettyClient;
import com.netty.flatbuffers.FbBizMsg;
import com.netty.flatbuffers.FbKLineDataList;
import com.netty.flatbuffers.FbKLineReq;
import com.netty.flatbuffers.FlatBufferBuilder;
import com.wordplat.ikvstockchart.InteractiveKLineLayout;
import com.wordplat.ikvstockchart.KLineHandler;
import com.wordplat.ikvstockchart.KLineMoveHandler;
import com.wordplat.ikvstockchart.compat.ViewUtils;
import com.wordplat.ikvstockchart.entry.Entry;
import com.wordplat.ikvstockchart.entry.EntrySet;
import com.wordplat.ikvstockchart.entry.SizeColor;
import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/9.
 */

public class KlineFragment extends BaseFragment {

    @BindView(R.id.MA_Text)
    TextView mMAText;
    @BindView(R.id.StockIndex_Text)
    TextView mStockIndexText;
    @BindView(R.id.Left_Loading_Image)
    ImageView mLeftLoadingImage;
    @BindView(R.id.kLineLayout)
    InteractiveKLineLayout mKLineLayout;
    Unbinder unbinder;
    @BindView(R.id.ll)
    LinearLayout mLl;
    @BindView(R.id.tv_high)
    TextView mTvHigh;
    @BindView(R.id.tv_low)
    TextView mTvLow;
    @BindView(R.id.tv_open)
    TextView mTvOpen;
    @BindView(R.id.tv_close)
    TextView mTvClose;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    private EntrySet mEntrySet;
    //日线、1分钟线、5分钟线等等
    private String mKlineType;
    private String contractNo;
    private int decimalPlaces;
    private double minChange;

    public static KlineFragment newInstance() {
        KlineFragment fragment = new KlineFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        contractNo = ((CommodityDetailActivity) activity).getcontractNo();//通过强转成宿主activity，就可以获取到传递过来的数据
        decimalPlaces = ((CommodityDetailActivity) activity).getDecimalPlaces();
        minChange = ((CommodityDetailActivity) activity).getMinChange();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kline, container, false);
        unbinder = ButterKnife.bind(this, view);
        mKLineLayout.getKLineView().setEnableRightRefresh(false);
        initSize();
        initView();
        return view;
    }

    private void initSize() {
        SizeColor.xLabelSize = ViewUtils.dpTopx(getContext(), 8);
        SizeColor.yLabelSize = ViewUtils.dpTopx(getContext(), 7);
        SizeColor.markerTextSize = ViewUtils.dpTopx(getContext(), 8);
        SizeColor.candleExtremumLabelSize = ViewUtils.dpTopx(getContext(), 7);
    }

    public void getKlineData(String klineType) {
        mKlineType = klineType;
        sendKlineMsg();
        KlineNettyClient.getInstance().getKline(new KlineNettyClient.KlineListen() {
            @Override
            public void success(FbKLineDataList resp) {
                mEntrySet = StockDataTest.parseKLineData(resp, getTimeType());
                mEntrySet.setSmallestFluctuation(String.valueOf(minChange));
                mEntrySet.computeStockIndex();
                mKLineLayout.getKLineView().setEntrySet(mEntrySet);
                mKLineLayout.getKLineView().notifyDataSetChanged();
            }

            @Override
            public void fail(String msg) {

            }
        });
    }

    private void sendKlineMsg() {
        FlatBufferBuilder fb = new FlatBufferBuilder();
        int contract = fb.createString(contractNo);
        int lineType = fb.createString(mKlineType);
        int rangeType = fb.createString("1");
        int klineReq = FbKLineReq.createFbKLineReq(fb, contract, lineType, rangeType);
        fb.finish(klineReq);
        FlatBufferBuilder headbuilder = new FlatBufferBuilder();
        int head_ofs = FbBizMsg.createFbBizMsg(headbuilder, (byte) '3', headbuilder.createString("test123456"),
                headbuilder.createString("imtoken"),
                headbuilder.createString(""),
                headbuilder.createString("Q1001"),
                0, 0, headbuilder.createString("20181026113000"), headbuilder.createByteVector(fb.sizedByteArray()));
        headbuilder.finish(head_ofs);
        KlineNettyClient.getInstance().sendQ(headbuilder.sizedByteArray());
    }

    private void initView() {
        mKLineLayout.getKLineView().getRender().setDecimalPlaces(decimalPlaces);
        mKLineLayout.getKLineView().setEnableLeftRefresh(false);
        mKLineLayout.getkLineRender().setKLineMoveHandler(new KLineMoveHandler() {
            @Override
            public void onMove(int maxVisibleIndex) {
                if (maxVisibleIndex < 0) return;
                if (mKLineLayout.getKLineView().getRender().isHighlight()) return;
                setMAText(mEntrySet.getEntryList().get(maxVisibleIndex));
            }
        });
        mKLineLayout.setKLineHandler(new KLineHandler() {
            @Override
            public void onLeftRefresh() {

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
                int width = mKLineLayout.getMeasuredWidth();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLl.getLayoutParams();
                params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                if (x < width / 2) {
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                } else {
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                }
                mLl.setLayoutParams(params); //使layout更新
                mLl.setVisibility(View.VISIBLE);
                mTvHigh.setText("高:" + entry.getHigh());
                mTvLow.setText("低:" + entry.getLow());
                mTvOpen.setText("开:" + entry.getOpen());
                mTvClose.setText("收:" + entry.getClose());
                mTvTime.setText(ViewUtils.StringStampDate(entry.getXLabel(),getTimeType()));
                setMAText(entry);
            }

            @Override
            public void onCancelHighlight() {
                mLl.setVisibility(View.GONE);
            }
        });
    }

    private List<Entry> insertEntries(int size, EntrySet entrySet) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            entries.add(entrySet.getEntryList().get(i));
        }
        return entries;
    }

    private void setMAText(Entry entry) {
        final SizeColor sizeColor = mKLineLayout.getKLineView().getRender().getSizeColor();
        String maString = String.format(getResources().getString(R.string.ma_highlight),
                entry.getMa5(),
                entry.getMa10(),
                entry.getMa20());

        mMAText.setText(getSpannableString(maString,
                sizeColor.getMa5Color(),
                sizeColor.getMa10Color(),
                sizeColor.getMa20Color()));

        SpannableString spanString = new SpannableString("");
        if (mKLineLayout.isShownVOL()) {
            String str = String.format(getResources().getString(R.string.volume_highlight),
                    entry.getVolumeMa5(),
                    entry.getVolumeMa10());
            spanString = getSpannableString(str,
                    sizeColor.getMa5Color(),
                    sizeColor.getMa10Color(),
                    0);
        } else if (mKLineLayout.isShownMACD()) {
            String str = String.format(getResources().getString(R.string.macd_highlight),
                    entry.getDiff(),
                    entry.getDea(),
                    entry.getMacd());

            spanString = getSpannableString(str,
                    sizeColor.getDiffLineColor(),
                    sizeColor.getDeaLineColor(),
                    sizeColor.getMacdHighlightTextColor());
        } else {
            String str = String.format(getResources().getString(R.string.kdj_highlight),
                    entry.getK(),
                    entry.getD(),
                    entry.getJ());

            spanString = getSpannableString(str,
                    sizeColor.getKdjKLineColor(),
                    sizeColor.getKdjDLineColor(),
                    sizeColor.getKdjJLineColor());
        }
        mStockIndexText.setText(spanString);
    }

    private SpannableString getSpannableString(String str, int partColor0, int partColor1, int partColor2) {
        String[] splitString = str.split("[●]");
        SpannableString spanString = new SpannableString(str);

        int pos0 = splitString[0].length();
        int pos1 = pos0 + splitString[1].length() + 1;
        int end = str.length();

        spanString.setSpan(new ForegroundColorSpan(partColor0),
                pos0, pos1, SpannableString.SPAN_EXCLUSIVE_INCLUSIVE);

        if (splitString.length > 2) {
            int pos2 = pos1 + splitString[2].length() + 1;

            spanString.setSpan(new ForegroundColorSpan(partColor1),
                    pos1, pos2, SpannableString.SPAN_EXCLUSIVE_INCLUSIVE);

            spanString.setSpan(new ForegroundColorSpan(partColor2),
                    pos2, end, SpannableString.SPAN_EXCLUSIVE_INCLUSIVE);
        } else {
            spanString.setSpan(new ForegroundColorSpan(partColor1),
                    pos1, end, SpannableString.SPAN_EXCLUSIVE_INCLUSIVE);
        }

        return spanString;
    }

    private String getTimeType() {
        if (TextUtils.equals(mKlineType, "3-1")) return "1";
        return "0";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_full_screen)
    public void onViewClicked() {
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            startActivity(new Intent(mActivity, CommodityDetailActivity.class));
        } else {
            startActivity(new Intent(mActivity, LandscapeActivity.class));
        }
        mActivity.finish();
    }
}
