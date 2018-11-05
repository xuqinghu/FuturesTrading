package com.xuantie.futures.base;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by Administrator on 2018/9/17.
 */

public interface IBaseView {
    void openLoading();//启动网络请求进度提示

    void closeLoading();//关闭网络请求进度提示

    void showError(String errorMsg);//显示错误提示

    void launchFinish();//结束该界面

    void netError(Throwable e);//网络错误

    void loginBack();//登录之后回来

    @NonNull
    @CheckResult
    <T> LifecycleTransformer<T> bindToLifecycle();//rxjava绑定view生命周期
}
