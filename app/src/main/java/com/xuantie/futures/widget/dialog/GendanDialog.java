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
 * Created by Administrator on 2018/10/23.
 */

public class GendanDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.tv_gendan)
    TextView mTvGendan;
    @BindView(R.id.tv_deal_records)
    TextView mTvDealRecords;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    private GenDanListener listener;

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }

    public interface GenDanListener {
        void onClick(View view);
    }

    public GendanDialog(Context context, int theme, GenDanListener listener) {
        super(context, theme);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_gendan);
        ButterKnife.bind(this);
        mTvGendan.setOnClickListener(this);
        mTvDealRecords.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
    }
}
