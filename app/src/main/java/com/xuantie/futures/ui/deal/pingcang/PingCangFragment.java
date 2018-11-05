package com.xuantie.futures.ui.deal.pingcang;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/9.
 * 平仓
 */

public class PingCangFragment extends BaseFragment {
    @BindView(R.id.elv)
    ExpandableListView mElv;
    Unbinder unbinder;
    private PingCangAdapter mAdapter;

    public static PingCangFragment newInstance() {
        PingCangFragment fragment = new PingCangFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pingcang, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mAdapter = new PingCangAdapter(mActivity);
        mElv.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
