package com.xuantie.futures;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xuantie.futures.base.BaseActivity;
import com.xuantie.futures.constants.Url;
import com.xuantie.futures.network.ResultSubject;
import com.xuantie.futures.network.RetrofitClient;
import com.xuantie.futures.network.bean.ResultModel;
import com.xuantie.futures.network.bean.req.LoginReq;
import com.xuantie.futures.network.bean.resp.LoginResp;
import com.xuantie.futures.network.helper.RxResultHelper;
import com.xuantie.futures.network.helper.RxSchedulersHelper;
import com.xuantie.futures.utils.CommonUtils;
import com.xuantie.futures.utils.GsonUtil;
import com.xuantie.futures.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 登录
 * Created by Administrator on 2018/9/21.
 */

public class Login1Activity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.iv_pwd_state)
    ImageView mIvPwdState;
    @BindView(R.id.iv_auth_code)
    ImageView mIvAuthCode;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.rl_login)
    RelativeLayout mRlLogin;
    @BindView(R.id.et_auth_code)
    EditText mEtAuthCode;
    private Unbinder mBind;
    //是否显示密码
    private boolean showPwd = false;
    private String auth_code_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        mBind = ButterKnife.bind(this);
        initData();
        mRlLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //触摸外部，键盘消失
                Utils.closeKeyboard(Login1Activity.this);
                return false;
            }
        });
    }

    private void initData() {
        mTvTitle.setText("登录");
        //隐藏密码
        mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        auth_code_url = Url.BASE_URL + "userAccount/getauthimage";
        //获取验证码
        Glide.with(this).load(auth_code_url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(mIvAuthCode);
    }

    @OnTextChanged({R.id.et_account, R.id.et_pwd, R.id.et_auth_code})
    public void onTextChanged() {
        String account = mEtAccount.getText().toString();
        String pwd = mEtPwd.getText().toString();
        String auth_code = mEtAuthCode.getText().toString();
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(auth_code)) {
            mBtnLogin.setEnabled(true);
        } else {
            mBtnLogin.setEnabled(false);
        }
    }

    @OnClick({R.id.rl_title_back, R.id.iv_clear_account, R.id.iv_pwd_state, R.id.iv_auth_code, R.id.btn_login, R.id.tv_no_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_title_back:
                finishLogin();
                break;
            case R.id.iv_clear_account:
                mEtAccount.setText("");
                break;
            case R.id.iv_pwd_state:
                if (showPwd) {
                    //隐藏密码
                    showPwd = false;
                    mIvPwdState.setBackgroundResource(R.mipmap.hide_pwd);
                    mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    //显示密码
                    showPwd = true;
                    mIvPwdState.setBackgroundResource(R.mipmap.show_pwd);
                    mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.iv_auth_code:
                //重新获取验证码
                Glide.with(this).load(auth_code_url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(mIvAuthCode);
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_no_account:
                startActivity(new Intent(this, ApplyAccountActivity.class));
                break;
        }
    }

    //请求参数
    private String requestData() {
        String userAccountNo = mEtAccount.getText().toString();
        String userPassword = mEtPwd.getText().toString();
        String code = mEtAuthCode.getText().toString();
        LoginReq loginReq = new LoginReq();
        loginReq.setChannel("app");
        LoginReq.ContentBean contentBean = new LoginReq.ContentBean();
        contentBean.setUserAccountNo(userAccountNo);
        contentBean.setUserPassword(userPassword);
        contentBean.setCode(code);
        loginReq.setContent(contentBean);
        return GsonUtil.GsonString(loginReq);
    }

    //登录
    private void login() {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData());
        RetrofitClient.getApi().Login(requestBody)
                .compose(RxSchedulersHelper.<ResultModel<LoginResp>>ioMain())
                .compose(RxResultHelper.<LoginResp>handleResult())
                .compose(this.<LoginResp>bindToLifecycle())
                .subscribe(new ResultSubject<LoginResp>(this,true) {
                    @Override
                    public void onNext(LoginResp resp) {
                        CommonUtils.showTextToast(Login1Activity.this,"登录成功");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
