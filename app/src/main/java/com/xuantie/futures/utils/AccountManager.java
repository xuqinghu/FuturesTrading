package com.xuantie.futures.utils;

/**
 * SharedPreferences保存一些轻量级的数据
 * Created by Administrator on 2018/9/18.
 */

public class AccountManager {
    private static AccountManager mInstance;

    public static AccountManager getInstance() {
        if (mInstance == null) {
            mInstance = new AccountManager();
        }
        return mInstance;
    }
}
