package com.xuantie.futures.ui.market;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netty.flatbuffers.FbFuturesQuotation;
import com.netty.flatbuffers.FbMsgGoodInfo;
import com.xuantie.futures.R;
import com.xuantie.futures.utils.CommonUtils;

import java.math.BigDecimal;
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
        //涨跌额＝最新价－昨日结算价
        BigDecimal bigDecimalPrice = new BigDecimal(item.LastPrice);
        BigDecimal bigDecimalSettle = new BigDecimal(item.PreSettlementPrice);
        float upsDowns = bigDecimalPrice.subtract(bigDecimalSettle).floatValue();
        //涨跌幅＝（最新价－昨日结算价）÷昨日结算价×100%
        float priceLimit = upsDowns /item.PreSettlementPrice  * 100;
        helper.setText(R.id.tv_good_name,item.GoodsName);
        helper.setText(R.id.tv_last_price,String.valueOf(item.LastPrice));
        helper.setText(R.id.tv_contractNo,item.ContractNo);
        helper.setText(R.id.tv_upsDowns,CommonUtils.decimalplace(String.valueOf(upsDowns),item.decimalPlaces));
        if(item.LastPrice>item.PreSettlementPrice){
            helper.setText(R.id.tv_priceLimit, "+"+CommonUtils.formatAmt(priceLimit)+"%");
            helper.setTextColor(R.id.tv_priceLimit,mContext.getResources().getColor(R.color.red));
            helper.setTextColor(R.id.tv_last_price,mContext.getResources().getColor(R.color.red));
            helper.setTextColor(R.id.tv_upsDowns,mContext.getResources().getColor(R.color.red));
        }else {
            helper.setText(R.id.tv_priceLimit, CommonUtils.formatAmt(priceLimit)+"%");
            helper.setTextColor(R.id.tv_priceLimit,mContext.getResources().getColor(R.color.green));
            helper.setTextColor(R.id.tv_last_price,mContext.getResources().getColor(R.color.green));
            helper.setTextColor(R.id.tv_upsDowns,mContext.getResources().getColor(R.color.green));
        }
    }
}
