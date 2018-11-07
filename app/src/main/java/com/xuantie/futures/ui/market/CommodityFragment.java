package com.xuantie.futures.ui.market;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netty.flatbuffers.FbFuturesQuotation;
import com.netty.flatbuffers.FbFuturesQuotationList;
import com.netty.flatbuffers.FbMsgGoodInfo;
import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;
import com.xuantie.futures.ui.market.detail.CommodityDetailActivity;
import com.xuantie.futures.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 商品列表
 * Created by Administrator on 2018/10/8.
 */

@SuppressLint("ValidFragment")
public class CommodityFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView mRv;
    Unbinder unbinder;
    private CommodityAdapter mAdapter;
    private List<FbMsgGoodInfo> mFbMsgGoodInfoList;
    private String marketName;
    private List<FbFuturesQuotation> mFbFuturesQuotationList = new ArrayList<>();

    @SuppressLint("ValidFragment")
    public CommodityFragment(List<FbMsgGoodInfo> beans, String name) {
        mFbMsgGoodInfoList = beans;
        marketName = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commodity, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        for(int i=0;i<mFbMsgGoodInfoList.size();i++){
            if(TextUtils.equals(marketName,mFbMsgGoodInfoList.get(i).marketName())){
                FbFuturesQuotation fbFuturesQuotation = new FbFuturesQuotation();
                fbFuturesQuotation.GoodsName = mFbMsgGoodInfoList.get(i).goodsName();
                fbFuturesQuotation.GoodsNo = mFbMsgGoodInfoList.get(i).goodsCode();
                fbFuturesQuotation.decimalPlaces = CommonUtils.getDecimalPlaces(mFbMsgGoodInfoList.get(i).minChange());
                mFbFuturesQuotationList.add(fbFuturesQuotation);
            }
        }
        mAdapter = new CommodityAdapter(R.layout.item_commodity,mFbFuturesQuotationList);
        mRv.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("goodsNo",mFbFuturesQuotationList.get(position).GoodsNo);
                intent.putExtra("goodsName",mFbFuturesQuotationList.get(position).GoodsName);
                intent.putExtra("contractNo",mFbFuturesQuotationList.get(position).ContractNo);
                intent.putExtra("LastPrice",mFbFuturesQuotationList.get(position).LastPrice);
                intent.putExtra("PreSettlementPrice",mFbFuturesQuotationList.get(position).PreSettlementPrice);
                intent.putExtra("HighestPrice",String.valueOf(mFbFuturesQuotationList.get(position).HighestPrice));
                intent.putExtra("LowestPrice",String.valueOf(mFbFuturesQuotationList.get(position).LowestPrice));
                intent.putExtra("BidPrice1",String.valueOf(mFbFuturesQuotationList.get(position).BidPrice1));
                intent.putExtra("AskPrice1",String.valueOf(mFbFuturesQuotationList.get(position).AskPrice1));
                intent.putExtra("decimalPlaces",mFbFuturesQuotationList.get(position).decimalPlaces);
                intent.setClass(mActivity,CommodityDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setData(FbFuturesQuotationList resp){
        for(int i=0;i<resp.FbFuturesQuotationListLength();i++){
           for(int j=0;j<mFbFuturesQuotationList.size();j++){
               if(TextUtils.equals(resp.FbFuturesQuotationList(i).GoodsNo(),mFbFuturesQuotationList.get(j).GoodsNo)){
                   mFbFuturesQuotationList.get(j).LastPrice = resp.FbFuturesQuotationList(i).LastPrice();
                   mFbFuturesQuotationList.get(j).ContractNo = resp.FbFuturesQuotationList(i).ContractNo();
                   mFbFuturesQuotationList.get(j).PreSettlementPrice = resp.FbFuturesQuotationList(i).PreSettlementPrice();
                   mFbFuturesQuotationList.get(j).HighestPrice = resp.FbFuturesQuotationList(i).HighestPrice();
                   mFbFuturesQuotationList.get(j).LowestPrice = resp.FbFuturesQuotationList(i).LowestPrice();
                   mFbFuturesQuotationList.get(j).BidPrice1 = resp.FbFuturesQuotationList(i).BidPrice1();
                   mFbFuturesQuotationList.get(j).AskPrice1 = resp.FbFuturesQuotationList(i).AskPrice1();
               }
           }
        }
        mAdapter.setNewData(mFbFuturesQuotationList);
    }

    public void refresh(FbFuturesQuotation resp){
        for(int i=0;i<mFbFuturesQuotationList.size();i++){
            if(TextUtils.equals(resp.GoodsNo(),mFbFuturesQuotationList.get(i).GoodsNo)){
                mFbFuturesQuotationList.get(i).LastPrice = resp.LastPrice();
                mFbFuturesQuotationList.get(i).ContractNo = resp.ContractNo();
                mFbFuturesQuotationList.get(i).PreSettlementPrice = resp.PreSettlementPrice();
                mFbFuturesQuotationList.get(i).HighestPrice = resp.HighestPrice();
                mFbFuturesQuotationList.get(i).LowestPrice = resp.LowestPrice();
                if(mFbFuturesQuotationList.get(i).LastPrice!=resp.LastPrice){
                    mAdapter.notifyItemChanged(i);
                }
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
