package com.xuantie.futures.network.api;

import com.xuantie.futures.network.bean.ResultModel;
import com.xuantie.futures.network.bean.ResultModel1;
import com.xuantie.futures.network.bean.resp.KLineResp;
import com.xuantie.futures.network.bean.resp.LoginResp;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/9/17.
 */

public interface Api {
    /**
     * 短信登录/密码登录---短信登录页面
     */
    @POST("userAccount/login")
    Observable<ResultModel<LoginResp>> Login(@Body RequestBody requestBody);

    /**
     * 获取K线图数据
     */
    @GET("market/kline")
    Observable<ResultModel1<List<KLineResp>>> getKline(@Query("exchange") String exchange,
                                                       @Query("symbol") String symbol,
                                                       @Query("period") String period,
                                                       @Query("begin") long begin,
                                                       @Query("count") int count);
}
