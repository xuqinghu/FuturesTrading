package com.xuantie.futures.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import com.xuantie.futures.App;
import com.xuantie.futures.R;
import com.xuantie.futures.utils.AppManager;
import com.xuantie.futures.utils.CommonUtils;
import com.xuantie.futures.utils.NetUtil;

import java.net.ConnectException;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Administrator on 2018/9/17.
 */

public class BaseActivity extends AppCompatActivity implements LifecycleProvider<ActivityEvent>, IBaseView {
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    //网络类型
    private int netMobile;
    private NetActivityBroadcastReceiver mNetBroadcastReceiver;
    private KProgressHUD mKProgressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        initSystemFont();
        //将Activity实例添加到AppManager的堆栈
        AppManager.getAppManager().addActivity(this);
        //初始化判断网络状态
        inspectNet();
        //注册网络状态监听广播
        mNetBroadcastReceiver = new NetActivityBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mNetBroadcastReceiver, intentFilter);
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
        //将Activity实例从AppManager的堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        unregisterReceiver(mNetBroadcastReceiver);
    }

    /**
     * 自定义检查手机网络状态是否切换的广播接受器
     */
    public class NetActivityBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // 如果相等的话就说明网络状态发生了变化
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                int netWorkState = NetUtil.getNetWorkState(context);
                // 接口回调传过去状态的类型
                onNetChange(netWorkState);
            }
        }
    }

    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(this);
        return isNetConnect();
    }

    /**
     * 网络变化之后的类型
     */
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        this.netMobile = netMobile;
        isNetConnect();
    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == 1) {
            return true;
        } else if (netMobile == 0) {
            return true;
        } else if (netMobile == -1) {
            return false;

        }
        return false;
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    /**
     * 初始化系统的字体大小，不受设置里的字体大小的影响
     */
    private void initSystemFont() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    @Override
    public void openLoading() {
        if (mKProgressHUD == null) {
            mKProgressHUD = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f)
                    .setCancellable(true);
        }
        if (!mKProgressHUD.isShowing()) {
            mKProgressHUD.show();
        }
    }

    @Override
    public void closeLoading() {
        if (mKProgressHUD != null && mKProgressHUD.isShowing()) {
            mKProgressHUD.dismiss();
        }
    }


    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchFinish() {
        finish();
    }


    /**
     * 网络错误跳转网络错误页面
     *
     * @param e
     */
    @Override
    public void netError(Throwable e) {
        if (e instanceof ConnectException) {
            Toast.makeText(this, R.string.dont_have_net, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.net_not_good, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loginBack() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 常规Activity从左至右进入动画
     *
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_activity_right_in, R.anim.anim_activity_left_out);
    }

    /**
     * 常规Activity从左至右退出动画
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_activity_left_in, R.anim.anim_activity_right_out);
    }

    public void startIntentMain(Intent intent){
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,  android.R.anim.fade_out);
    }

    /**
     * Activity从底部弹出的进入动画
     */
    public void startIntentLogin(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_tab_pay_open, android.R.anim.fade_out);
    }

    /**
     * ctivity从上到下退出动画
     */
    public void finishLogin() {
        super.finish();
        overridePendingTransition(0, R.anim.activity_tab_pay_close);
    }

    public void finishSplash(){
        super.finish();
    }
}
