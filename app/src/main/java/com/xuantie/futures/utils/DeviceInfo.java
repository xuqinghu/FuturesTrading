package com.xuantie.futures.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.xuantie.futures.App;

import java.util.UUID;

/**
 * Created by Administrator on 2018/9/17.
 * 设备信息相关
 */

public class DeviceInfo {

    private static DeviceInfo sDeviceInfo;
    public final static String PLATFORM = "android";
    private String mAppVersion;
    private String mDeviceId;

    private DeviceInfo() {
    }

    public static DeviceInfo get() {
        if (sDeviceInfo == null) {
            sDeviceInfo = new DeviceInfo();
        }
        return sDeviceInfo;
    }

    public String getAppVersion() {
        if (!TextUtils.isEmpty(mAppVersion)) {
            return mAppVersion;
        }

        PackageManager packageManager = App.sContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(App.sContext.getPackageName(), 0);
            mAppVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return mAppVersion;
    }

    public void init() {
        TelephonyManager tm = (TelephonyManager) App.sContext.getSystemService(Context.TELEPHONY_SERVICE);
        mDeviceId = tm.getDeviceId();
        if (TextUtils.isEmpty(mDeviceId)) {
            mDeviceId = Utils.getIdentity();
        }
    }

    public static String getUniqueID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }

    /**
     * androidId
     *
     * @param context
     * @return
     */
    public static String androidId(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
    }


    public String getDeviceId() {
        if (TextUtils.isEmpty(mDeviceId)) {
            TelephonyManager tm = (TelephonyManager) App.sContext.getSystemService(Context.TELEPHONY_SERVICE);
            mDeviceId = tm.getDeviceId();
            if (TextUtils.isEmpty(mDeviceId)) {
                mDeviceId = Utils.getIdentity();
            }
        }
        return mDeviceId;
    }

}
