package com.xuantie.futures.ui.market.detail;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netty.client.KlineNettyClient;
import com.netty.client.NettyClient;
import com.netty.flatbuffers.FbBizMsg;
import com.netty.flatbuffers.FbKLineDataList;
import com.netty.flatbuffers.FbKLineReq;
import com.netty.flatbuffers.FbMsgLoginReq;
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
    private EntrySet mEntrySet;
    private List<KLineResp> list = new ArrayList<>();
    //日线、1分钟线、5分钟线等等
    private String mKlineType;
    private String contractNo;

    public static KlineFragment newInstance() {
        KlineFragment fragment = new KlineFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        contractNo = ((CommodityDetailActivity) activity).getcontractNo();//通过强转成宿主activity，就可以获取到传递过来的数据
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

    public void getKlineData(String klineType){
        mKlineType = klineType;
        sendKlineMsg();
        KlineNettyClient.getInstance().getKline(new KlineNettyClient.KlineListen() {
            @Override
            public void success(FbKLineDataList resp) {
                Log.d("FbKLineDataList","日期:"+resp.DataList(0).Date()+"时间:"+resp.DataList(0).Time());
            }

            @Override
            public void fail(String msg) {

            }
        });
    }

    private void sendKlineMsg(){
        FlatBufferBuilder fb = new FlatBufferBuilder();
        int contract = fb.createString(contractNo);
        int lineType = fb.createString(mKlineType);
        int rangeType = fb.createString("2");
        int klineReq = FbKLineReq.createFbKLineReq(fb, contract,lineType,rangeType);
        fb.finish(klineReq);
        FlatBufferBuilder headbuilder = new FlatBufferBuilder();
        int head_ofs = FbBizMsg.createFbBizMsg(headbuilder, (byte)'3' ,headbuilder.createString("test123456"),
                headbuilder.createString("imtoken"),
                headbuilder.createString(""),
                headbuilder.createString("Q1001"),
                0, 0, headbuilder.createString("20181026113000"),headbuilder.createByteVector(fb.sizedByteArray()));
        headbuilder.finish(head_ofs);
        KlineNettyClient.getInstance().sendQ(headbuilder.sizedByteArray());
    }

    private void initView() {
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
                mLeftLoadingImage.setVisibility(View.VISIBLE);
                ((Animatable) mLeftLoadingImage.getDrawable()).start();
                loadKLineData(true, "m1", list.get(0).getDataTime());
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
                mTvHigh.setText("高:1211.19");
                mTvLow.setText("低:1211.11");
                mTvOpen.setText("开:1211.10");
                mTvClose.setText("收:1220.78");
                setMAText(entry);
            }

            @Override
            public void onCancelHighlight() {
                mLl.setVisibility(View.GONE);
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
                                mKLineLayout.getKLineView().setEnableLeftRefresh(false);
                            } else {
                                mKLineLayout.getKLineView().setEnableLeftRefresh(true);
                            }
                            Collections.reverse(kLineResps); // 倒序排列
                            list.addAll(kLineResps);
                            mEntrySet = StockDataTest.parseKLineData(kLineResps, getTimeType());
                            mEntrySet.setSmallestFluctuation("0.01");
                            mEntrySet.computeStockIndex();
                            mKLineLayout.getKLineView().setEntrySet(mEntrySet);
                            mKLineLayout.getKLineView().notifyDataSetChanged();
                        } else {
                            for (int i = 0; i < kLineResps.size(); i++) {
                                list.add(0, kLineResps.get(i));
                            }
                            mEntrySet = StockDataTest.parseKLineData(list, getTimeType());
                            mEntrySet.computeStockIndex();
                            List<Entry> entries = insertEntries(kLineResps.size(), mEntrySet);
                            mKLineLayout.getKLineView().getRender().getEntrySet().insertFirst(entries);
                            mKLineLayout.getKLineView().notifyDataSetChanged();
                            mKLineLayout.getKLineView().refreshComplete(entries.size() > 0);
                            mLeftLoadingImage.setVisibility(View.GONE);
                            ((Animatable) mLeftLoadingImage.getDrawable()).stop();
                            if (entries.size() == 0) {
                                Toast.makeText(mActivity, "已经到达最左边了", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mLeftLoadingImage.setVisibility(View.GONE);
                        ((Animatable) mLeftLoadingImage.getDrawable()).stop();
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
        if (TextUtils.equals(mKlineType, "day") || TextUtils.equals(mKlineType, "week"))
            return "MM-dd";
        if (TextUtils.equals(mKlineType, "month")) return "MM";
        if (TextUtils.equals(mKlineType, "year")) return "yyyy";
        if (TextUtils.equals(mKlineType, "m240")) return "MM-dd HH:mm";
        return "HH:mm";
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
