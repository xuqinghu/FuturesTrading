package com.xuantie.futures;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xuantie.futures.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/10.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_yzm)
    EditText mEtYzm;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick({R.id.ll_back, R.id.tv_go_fast_login, R.id.rl_get_yzm, R.id.tv_forget_pwd, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                break;
            case R.id.tv_go_fast_login:
                finish();
                break;
            case R.id.rl_get_yzm:
                break;
            case R.id.tv_forget_pwd:
                break;
            case R.id.tv_login:
                break;
        }
    }
}
