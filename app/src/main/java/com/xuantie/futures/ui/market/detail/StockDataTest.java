package com.xuantie.futures.ui.market.detail;

import com.netty.flatbuffers.FbKLineData;
import com.netty.flatbuffers.FbKLineDataList;
import com.wordplat.ikvstockchart.entry.Entry;
import com.wordplat.ikvstockchart.entry.EntrySet;
import com.xuantie.futures.network.bean.resp.KLineResp;

import java.util.List;

/**
 * Created by Administrator on 2018/8/20.
 */

public class StockDataTest {

    public static EntrySet parseKLineData(List<KLineResp> datas, String timeType) {
        final EntrySet entrySet = new EntrySet();
        for (KLineResp kLineResp : datas) {
            float open = Float.parseFloat(kLineResp.getOpen());
            float high = Float.parseFloat(kLineResp.getHigh());
            float low = Float.parseFloat(kLineResp.getLow());
            float close = Float.parseFloat(kLineResp.getClose());
            int volume = kLineResp.getVolume();
            String time = String.valueOf(kLineResp.getDataTime());
            entrySet.setTimeType(timeType);
            entrySet.addEntry(new Entry(open, high, low, close, volume, time));
        }
        return entrySet;
    }

    public static EntrySet parseLightningData(List<String> prices) {
        EntrySet entrySet = new EntrySet();
        for (String price : prices) {
            entrySet.addEntry(new Entry(Float.parseFloat(price)));
        }
        return entrySet;
    }

    public static EntrySet parseKLineData(FbKLineDataList fbKLineDataList){
        final EntrySet entrySet = new EntrySet();
        FbKLineData fbKLineData = null;
        for(int i=0;i<fbKLineDataList.DataListLength();i++){
            fbKLineData = fbKLineDataList.DataList(i);
            float open = fbKLineData.openPrice();
            float high = fbKLineData.highPrice();
            float low =  fbKLineData.lowerPrice();
            float close = fbKLineData.closePrice();
            int volume = (int) fbKLineData.vol();
            String time = String.valueOf(fbKLineData.Time());
            entrySet.addEntry(new Entry(open, high, low, close, volume, time));
        }
        return entrySet;
    }
}
