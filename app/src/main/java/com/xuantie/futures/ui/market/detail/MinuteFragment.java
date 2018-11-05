package com.xuantie.futures.ui.market.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;

/**
 * Created by Administrator on 2018/10/9.
 */

public class MinuteFragment extends BaseFragment {
    public static MinuteFragment newInstance() {
        MinuteFragment fragment = new MinuteFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minute, container, false);
        return view;
    }
}
