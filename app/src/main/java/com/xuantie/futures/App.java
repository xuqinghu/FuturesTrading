package com.xuantie.futures;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.xuantie.futures.utils.CrashHandler;

/**
 * Created by Administrator on 2018/9/17.
 */

public class App extends MultiDexApplication {
    public static Context sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        initCrashHandler();
    }

    private void initCrashHandler(){
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(sContext);
    }
}
