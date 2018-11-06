package com.xuantie.futures.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.xuantie.futures.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/9/17.
 */

public class Utils {

    /**
     * 关闭键盘
     *
     * @param activity
     */
    public static void closeKeyboard(Activity activity) {
        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 判断是否有虚拟底部按钮
     *
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }

    /**
     * 获取底部虚拟按键高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    /**
     * 针对无法获取设备号的设备生成的随机标识
     *
     * @return
     */
    public static String getIdentity() {
        SpUtils.getString(App.sContext, "identity");
        String identity = SpUtils.getString(App.sContext, "identity");
        if (identity == null) {
            identity = java.util.UUID.randomUUID().toString();
            SpUtils.putString(App.sContext, "identity", identity);
        }
        return identity;
    }

    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 获取当前时间时间戳--精确到毫秒
     */
    public static long nowMillisecondTimestamp() {
        long time = System.currentTimeMillis();
        return time;
    }

    /**
     * dip 转换成px
     *
     * @param dip
     * @return
     */
    public static int dipToPx(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 获取本地SD卡路径
     *
     * @return
     */
    public static String getSDPath() {
        File sdDir = null;
        try {
            boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
            File pp1 = new File("/external_sd");// 获取跟目录
            File filesd2 = new File("/udisk");// 客户sd卡路径
            File file = new File("/mnt/internal");// 客户sd卡路径
            if (sdCardExist) {
                sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
                return sdDir.toString();
            } else if (filesd2.exists()) {
                sdDir = filesd2;
                return sdDir.toString();
            } else if (pp1.exists()) {
                sdDir = pp1;
                return sdDir.toString();
            } else if (file.exists()) {
                sdDir = file;
                return sdDir.toString();
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 创建sd卡的目录
     *
     * @param dir
     * @return
     */
    public static File creatSDDir(String dir) {
        File appSDPathFile = new File(getSDPath() + "/" + "FuturesTrading");
        if (!appSDPathFile.exists()) {
            appSDPathFile.mkdirs();
        }
        File destFileDir = new File(appSDPathFile + "/" + dir);
        if (!destFileDir.exists()) {
            destFileDir.mkdirs();
        }

        return destFileDir;
    }

    /**
     * 把string保存在文件中
     *
     * @param path
     * @param filename
     * @param content
     */
    public static void saveStringFile(String path, String filename, String content) {
        File filePath = new File(getSDPath() + "/" + "FuturesTrading" + "/" + path);
        if (!filePath.exists()) {
            creatSDDir(path);
        }
        File fileAbsolutely = new File(filePath + "/" + filename);
        if (!fileAbsolutely.exists()) {
            try {
                fileAbsolutely.createNewFile();
                FileOutputStream fos = new FileOutputStream(fileAbsolutely);
                fos.write(content.getBytes());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            fileAbsolutely.delete();
            try {
                fileAbsolutely.createNewFile();
                FileOutputStream fos = new FileOutputStream(fileAbsolutely);
                fos.write(content.getBytes());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
