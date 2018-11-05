package com.xuantie.futures;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.xuantie.futures.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/10.
 * 手机快捷登录
 */

public class FastLoginActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_yzm)
    EditText mEtYzm;
    private Unbinder mUnbinder;
    private final int REQ_CODE = 101;
    public static final int RESULT_CODE = 102;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_login);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick({R.id.ll_back, R.id.tv_go_login, R.id.tv_get_yzm, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finishLogin();
                break;
            case R.id.tv_go_login:
                startActivityForResultNoAnim(new Intent(this,LoginActivity.class),REQ_CODE);
                break;
            case R.id.tv_get_yzm:
                break;
            case R.id.btn_login:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE&&resultCode==RESULT_CODE){
            finish();
        }
    }
}
