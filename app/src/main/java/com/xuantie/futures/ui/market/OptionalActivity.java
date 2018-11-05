package com.xuantie.futures.ui.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/15.
 * 自选
 */

public class OptionalActivity extends BaseActivity {

    @BindView(R.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_optional)
    RecyclerView mRvOptional;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optional);
        mUnbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mIvTitleLeft.setBackgroundResource(R.mipmap.icon_back);
        mTvTitle.setText("自选");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick(R.id.rl_title_back)
    public void onViewClicked() {
        finishLogin();
    }
}
