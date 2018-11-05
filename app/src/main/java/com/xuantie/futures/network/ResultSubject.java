package com.xuantie.futures.network;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.xuantie.futures.base.BaseActivity;
import com.xuantie.futures.base.BaseFragment;
import com.xuantie.futures.base.IBaseView;
import com.xuantie.futures.network.exception.FailException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.Subject;

/**
 * Created by Administrator on 2018/9/25.
 */

public abstract class ResultSubject<T> extends Subject<T> {
    private final IBaseView mBaseView;
    private boolean mOpenLoading = false;

    public ResultSubject(IBaseView baseView) {
        mBaseView = baseView;
        if (mBaseView != null) {
            mBaseView.openLoading();
        }
    }

    public ResultSubject(IBaseView baseView, boolean openLoading) {
        mBaseView = baseView;
        mOpenLoading = openLoading;
        if (openLoading && mBaseView != null) {
            mBaseView.openLoading();
        }
    }

    @Override
    public boolean hasObservers() {
        return false;
    }

    @Override
    public boolean hasThrowable() {
        return false;
    }

    @Override
    public boolean hasComplete() {
        return false;
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }

    @Override
    protected void subscribeActual(Observer observer) {

    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public abstract void onNext(T t);

    @Override
    public void onError(Throwable e) {
        if (mBaseView == null) return;
        if(mOpenLoading) mBaseView.closeLoading();
        if (e instanceof FailException) {
            //参数错误
            mBaseView.showError(e.getMessage());
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException || e instanceof UnknownHostException || e instanceof SSLException || e instanceof SocketException) {
            // 网络错误
            mBaseView.netError(e);
        } else {
            //其它异常
            if (mBaseView instanceof BaseFragment) {
                //登录状态失效
                if (TextUtils.equals(e.getMessage(), "HTTP 401 ")) {
                } else {
                    Toast.makeText(((BaseFragment) mBaseView).getActivity(), "未知错误：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else if (mBaseView instanceof BaseActivity) {
                if (TextUtils.equals(e.getMessage(), "HTTP 401 ")) {
                } else {
                    Toast.makeText((BaseActivity) mBaseView, "未知错误：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Log.e("TAG", "未知错误：" + e.getMessage());

            }
        }
    }

    @Override
    public void onComplete() {
        if (mBaseView != null && mOpenLoading) {
            mBaseView.closeLoading();
        }
    }
}
