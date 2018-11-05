package com.xuantie.futures.ui.mine.announcement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/10.
 * 公告中心
 */

public class AnnouncementActivity extends BaseActivity {
    @BindView(R.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv)
    RecyclerView mRv;
    private Unbinder mUnbinder;
    private AnnouncementAdapter mAdapter;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        mUnbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        mIvTitleLeft.setBackgroundResource(R.mipmap.icon_back);
        mTvTitle.setText("公告中心");
        for(int i=0;i<10;i++){
            list.add(String.valueOf(i));
        }
        mAdapter = new AnnouncementAdapter(R.layout.item_announcement,list);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick(R.id.rl_title_back)
    public void onViewClicked() {
        finish();
    }
}
