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
                intent.putExtra("goodsName",mFbFuturesQuotationList.get(position).GoodsName);
                intent.putExtra("contractNo",mFbFuturesQuotationList.get(position).ContractNo);
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
                   mAdapter.notifyItemChanged(j);
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
