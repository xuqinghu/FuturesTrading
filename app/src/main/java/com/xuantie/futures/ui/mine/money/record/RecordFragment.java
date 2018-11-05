package com.xuantie.futures.ui.mine.money.record;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/10.
 * 资金记录
 */

public class RecordFragment extends BaseFragment {
    @BindView(R.id.tv_time_start)
    TextView mTvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView mTvTimeEnd;
    @BindView(R.id.rv)
    RecyclerView mRv;
    Unbinder unbinder;
    private RecordAdapter mAdapter;
    private ArrayList<String> list = new ArrayList<>();

    public static RecordFragment newInstance() {
        RecordFragment fragment = new RecordFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        mAdapter = new RecordAdapter(R.layout.item_record, list);
        mRv.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.rl_select_time)
    public void onViewClicked() {
    }
}
