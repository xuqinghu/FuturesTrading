package com.xuantie.futures.ui.ranking.myRanking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;

/**
 * Created by Administrator on 2018/10/23.
 */

public class MyRankingFragment extends BaseFragment{
    public static MyRankingFragment newInstance(){
        MyRankingFragment fragment = new MyRankingFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myranking, container, false);
        return view;
    }
}
