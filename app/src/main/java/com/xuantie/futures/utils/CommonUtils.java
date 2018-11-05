package com.xuantie.futures.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/9/17.
 */

public class CommonUtils {
    private static Toast toast = null;

    public static void showTextToast(Context context, int msgRes) {
        if (toast == null) {
            // 设置土司显示在屏幕的位置
            toast = Toast.makeText(context, msgRes, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msgRes);
        }
        toast.show();
    }

    public static void showTextToast(Context context, String msgRes) {
        if (toast == null) {
            // 设置土司显示在屏幕的位置
            toast = Toast.makeText(context, msgRes, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msgRes);
        }
        toast.show();

    }
}
