package com.xuantie.futures.ui.mine.money.withdraw;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;

/**
 * Created by Administrator on 2018/10/10.
 * 出金
 */

public class WithdrawFragment extends BaseFragment {
    public static WithdrawFragment newInstance(){
        WithdrawFragment fragment =new WithdrawFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_withdraw, container, false);
        return view;
    }
}
