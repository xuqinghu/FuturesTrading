package com.xuantie.futures;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netty.client.NewNettyClient;
import com.netty.flatbuffers.FbBizMsg;
import com.netty.flatbuffers.FbMsgCaptchaResp;
import com.netty.flatbuffers.FbMsgLoginReq;
import com.netty.flatbuffers.FbMsgLoginRsp;
import com.netty.flatbuffers.FlatBufferBuilder;
import com.xuantie.futures.base.BaseActivity;
import com.xuantie.futures.utils.Base64Util;
import com.xuantie.futures.utils.CommonUtils;

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
    @BindView(R.id.iv_auth_code)
    ImageView mIvAuthCode;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    private Unbinder mUnbinder;
    private String verifyCode;
    private String verifyKey;
    private final int FAIL_MSG = 1;
    private String failMsg;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Glide.with(LoginActivity.this).load(Base64Util.base64ToByte(verifyCode)).into(mIvAuthCode);
                    break;
                case FAIL_MSG:
                    CommonUtils.showTextToast(LoginActivity.this,failMsg);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        sendYzmMsg();
        NewNettyClient.getInstance().getYzm(new NewNettyClient.YzmListen() {
            @Override
            public void success(FbMsgCaptchaResp resp) {
                verifyCode = resp.verifyCode();
                verifyKey = resp.verifyKey();
                handler.sendEmptyMessage(0);
            }

            @Override
            public void fail(String msg) {
                failMsg = msg;
            }
        });
        NewNettyClient.getInstance().login(new NewNettyClient.LoginListen() {
            @Override
            public void success(FbMsgLoginRsp resp) {
                Log.d("FbMsgLoginRsp",""+resp);
            }

            @Override
            public void fail(String msg) {
                failMsg = msg;
                handler.sendEmptyMessage(FAIL_MSG);
            }
        });
    }

    //发送验证码的msg
    private void sendYzmMsg() {
        FlatBufferBuilder headbuilder = new FlatBufferBuilder();
        int head_yzm = FbBizMsg.createFbBizMsg(headbuilder, (byte) '3', headbuilder.createString("test123456"),
                headbuilder.createString("imtoken"),
                headbuilder.createString(""),
                headbuilder.createString("T1000"),
                0, 0, headbuilder.createString("20181026113000"), 0);
        headbuilder.finish(head_yzm);
        NewNettyClient.getInstance().sendQ(headbuilder.sizedByteArray());
    }

    //发送登录的msg
    private void sendLoginMsg() {
        FlatBufferBuilder fb = new FlatBufferBuilder();
        int key = fb.createString(verifyKey);
        int code = fb.createString(mEtYzm.getText().toString());
        int accountNo = fb.createString(mEtPhone.getText().toString());
        int pwd = fb.createString(mEtPwd.getText().toString());
        int loginReq = FbMsgLoginReq.createFbMsgLoginReq(fb, key,code,accountNo,pwd);
        fb.finish(loginReq);
        FlatBufferBuilder  headbuilder= new FlatBufferBuilder();
        int head_login = FbBizMsg.createFbBizMsg(headbuilder, (byte) '3', headbuilder.createString("test123456"),
                headbuilder.createString("imtoken"),
                headbuilder.createString(""),
                headbuilder.createString("T1001"),
                0, 0, headbuilder.createString("20181026113000"),
                headbuilder.createByteVector(fb.sizedByteArray()));
        headbuilder.finish(head_login);
        NewNettyClient.getInstance().sendQ(headbuilder.sizedByteArray());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick({R.id.ll_back, R.id.tv_go_fast_login, R.id.rl_get_yzm, R.id.iv_auth_code,
            R.id.tv_forget_pwd, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                setResult(FastLoginActivity.RESULT_CODE);
                finish();
                break;
            case R.id.tv_go_fast_login:
                finishNoAnim();
                break;
            case R.id.rl_get_yzm:
                break;
            case R.id.tv_forget_pwd:
                break;
            case R.id.tv_login:
                sendLoginMsg();
                break;
            case R.id.iv_auth_code:
                sendYzmMsg();
                break;
        }
    }
}
