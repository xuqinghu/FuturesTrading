package com.xuantie.futures.network.bean.resp;

/**
 * Created by loro on 2017/2/8.
 */
public class KLineResp {
    //最高
    public String high;
    //最低
    public String low;
    //开盘
    public String open;
    //收盘
    public String close;
    //成交量
    public int volume;
    //时间
    public long dataTime;


    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public long getDataTime() {
        return dataTime;
    }

    public void setDataTime(long dataTime) {
        this.dataTime = dataTime;
    }
}
