package com.xuantie.futures.ui.mine.money.golden;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/10.
 * 入金
 */

public class GoldenFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView mRv;
    Unbinder unbinder;
    private GoldenAdapter mAdapter;
    private ArrayList<String> list = new ArrayList<>();

    public static GoldenFragment newInstance() {
        GoldenFragment fragment = new GoldenFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_golden, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        for(int i=0;i<3;i++){
            list.add(String.valueOf(i));
        }
        mAdapter = new GoldenAdapter(R.layout.item_golden,list);
        mRv.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
