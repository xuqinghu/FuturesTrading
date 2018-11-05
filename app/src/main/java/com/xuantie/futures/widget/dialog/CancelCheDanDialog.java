package com.xuantie.futures.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.xuantie.futures.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/23.
 */

public class CancelCheDanDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.v_line)
    View mVLine;
    @BindView(R.id.bt_cancel)
    Button mBtCancel;
    @BindView(R.id.bt_confirm)
    Button mBtConfirm;
    private Context mContext;
    private CancelCheDanDialogListener listener;

    public interface CancelCheDanDialogListener {
        void onClickView(View view);
    }

    public CancelCheDanDialog(Context context, int theme, CancelCheDanDialogListener listener) {
        super(context, theme);
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chedan);
        ButterKnife.bind(this);
        mBtCancel.setOnClickListener(this);
        mBtConfirm.setOnClickListener(this);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.x = 0; // 新位置X坐标
        lp.y = -100; // 新位置Y坐标
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        listener.onClickView(v);
    }
}
