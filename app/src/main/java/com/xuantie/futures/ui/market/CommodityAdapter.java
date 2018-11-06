package com.xuantie.futures.ui.market;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netty.flatbuffers.FbFuturesQuotation;
import com.netty.flatbuffers.FbMsgGoodInfo;
import com.xuantie.futures.R;

import java.util.List;

/**
 * Created by Administrator on 2018/10/8.
 */

public class CommodityAdapter extends BaseQuickAdapter<FbFuturesQuotation, BaseViewHolder> {
    public CommodityAdapter(int layoutResId, @Nullable List<FbFuturesQuotation> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FbFuturesQuotation item) {
        helper.setText(R.id.tv_good_name,item.GoodsName);
        helper.setText(R.id.tv_last_price,item.LastPrice+"");
        helper.setText(R.id.tv_contractNo,item.ContractNo);
    }
}
