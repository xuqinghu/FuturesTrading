package com.xuantie.futures.network.helper;

import android.text.TextUtils;

import com.xuantie.futures.network.bean.ResultModel;
import com.xuantie.futures.network.exception.FailException;

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
 * Created by Administrator on 2018/9/17.
 */

public class RxResultHelper {
    public static <T> ObservableTransformer<ResultModel<T>, T> handleResult() {
        return new ObservableTransformer<ResultModel<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResultModel<T>> upstream) {
                return upstream.flatMap(new Function<ResultModel<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull ResultModel<T> tResultModel) throws Exception {
                        if (TextUtils.equals(tResultModel.getCode(),ResultModel.REQ_SUCCESS)) {
                            //网络请求成功
                            if (tResultModel.getContent() != null) {
                                return createData(tResultModel.getContent());
                            } else {
                                List list = new ArrayList();
                                return (ObservableSource<T>) createData(list);
                            }
                        } else {
                            return Observable.error(new FailException(tResultModel.getMessage(), tResultModel.getCode(),tResultModel.getStatus()));
                        }
                    }
                });
            }
        };
    }

    private static <T> Observable<T> createData(final T content) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                e.onNext(content);
                e.onComplete();
            }
        });
    }
}
