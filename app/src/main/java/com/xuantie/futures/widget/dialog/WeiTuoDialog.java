package com.xuantie.futures.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.xuantie.futures.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/22.
 */

public class WeiTuoDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.tv_chedan)
    TextView mTvChedan;
    @BindView(R.id.tv_kline)
    TextView mTvKline;
    @BindView(R.id.tv_chedan_all)
    TextView mTvChedanAll;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    private WeiTuoListener listener;

    public interface WeiTuoListener {
        void onClick(View view);
    }

    public WeiTuoDialog(Context context, int theme, WeiTuoListener listener) {
        super(context, theme);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_weituo);
        ButterKnife.bind(this);
        mTvChedan.setOnClickListener(this);
        mTvKline.setOnClickListener(this);
        mTvChedanAll.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }
}
