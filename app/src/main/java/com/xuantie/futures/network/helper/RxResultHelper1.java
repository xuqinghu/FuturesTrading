package com.xuantie.futures.network.helper;

import com.xuantie.futures.network.bean.ResultModel1;
import com.xuantie.futures.network.exception.ServerException;
import com.xuantie.futures.network.exception.TokenException1;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2018/10/10.
 */

public class RxResultHelper1 {
    public static <T> ObservableTransformer<ResultModel1<T>, T> handleResult() {
        return new ObservableTransformer<ResultModel1<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResultModel1<T>> upstream) {
                return upstream.flatMap(new Function<ResultModel1<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull ResultModel1<T> tResultModel) throws Exception {
                        if (tResultModel == null) {
                            return Observable.error(new ServerException("服务器返回错误", -1));
                        }
                        if (tResultModel.getStatus() == ResultModel1.REQ_SUCCESS) {
                            //网络请求成功
                            if (tResultModel.getData() != null) {
                                return createData(tResultModel.getData());
                            } else {
                                List list = new ArrayList();
                                return (ObservableSource<T>) createData(list);
                            }
                        } else if (ResultModel1.REQ_TOKEN_ERROR.contains(tResultModel.getStatus())) {
                            //登录状态失效
                            return Observable.error(new TokenException1(tResultModel.getMessage(), tResultModel.getStatus()));
                        } else {
                            return Observable.error(new ServerException(tResultModel.getMessage(), tResultModel.getStatus()));
                        }
                    }
                });
            }
        };
    }

    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                e.onNext(data);
                e.onComplete();
            }
        });
    }
}
