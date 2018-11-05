package com.xuantie.futures.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuantie.futures.MainActivity;
import com.xuantie.futures.R;
import com.xuantie.futures.utils.GsonUtil;
import com.xuantie.futures.utils.Utils;
import com.xuantie.futures.widget.picker.BankBean;
import com.xuantie.futures.widget.picker.BanksWheelAdapter;
import com.xuantie.futures.widget.picker.JsonBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择开户银行
 * Created by Administrator on 2018/9/25.
 */

public class SelectBanksDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.wheel)
    WheelView mWheel;
    private SelectBanksDialogListener listener;
    private Context mContext;

    @Override
    public void onClick(View v) {
        listener.onClickView(v);
    }

    public interface SelectBanksDialogListener {
        void onClickView(View view);
    }

    public SelectBanksDialog(Context context, int theme, SelectBanksDialogListener listener) {
        super(context, theme);
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_banks);
        ButterKnife.bind(this);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.x = 0; // 新位置X坐标
        lp.y = -100; // 新位置Y坐标
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        String banks = Utils.getJson(mContext, "banks.json");
        List<BankBean> bankList = GsonUtil.GsonToList(banks,BankBean.class);
        mWheel.setCyclic(false);
        mWheel.setTextSize(18);
        mWheel.setDividerColor(R.color.grey_C0C0C0);
        mWheel.setBackgroundResource(R.color.grey_DCDCDC);
        mWheel.setAdapter(new BanksWheelAdapter(bankList));
        mWheel.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {

            }
        });
    }
}
