package com.xuantie.futures.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.text.DecimalFormat;

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

    /**
     * 格式化金额(保留小数点两位)
     *
     * @param amt
     * @return
     */
    public static String formatAmt(double amt) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(amt);
    }

    //小数点后有几位
    public static int getDecimalPlaces(double minChange){
        return (minChange+"").length()-(minChange+"").indexOf(".")-1;
    }

    /**
     * 格式化金额
     *
     * @param amt
     * @return
     */
    public static String formatAmt(String amt) {
        if (amt != null) {
            return formatAmt(Double.parseDouble(amt));
        }
        return "";
    }

    /**
     * 保存小数点后n位
     */
    public static String decimalplace(String amt, int n) {
        String decimalplace;
        if (!amt.contains(".")) {
            //字符串没有小数位
            //不满需要保留的小数自动补0
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < n; i++) {
                stringBuffer.append("0");
            }
            decimalplace = amt + "." + stringBuffer.toString();
        } else {
            //取整，截取掉小数点前的字符
            String number = amt.substring(0, amt.indexOf("."));
            //截取小数点之后的字符
            String signInfo = amt.substring(amt.indexOf(".") + 1);
            String cut_out;
            if (signInfo.length() > n) {
                //原始数据小数点后几位小于预留保存的小数点位数
                cut_out = signInfo.substring(0, n);
            } else if (signInfo.length() == n) {
                cut_out = signInfo;
            } else {
                //不满需要保留的小数自动补0
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < (n - signInfo.length()); i++) {
                    stringBuffer.append("0");
                }
                cut_out = signInfo + stringBuffer.toString();
            }
            decimalplace = number + "." + cut_out;
        }
        return decimalplace;
    }
}
