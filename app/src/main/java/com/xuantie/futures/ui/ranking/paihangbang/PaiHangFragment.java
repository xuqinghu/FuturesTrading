package com.xuantie.futures.ui.ranking.paihangbang;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;
import com.xuantie.futures.widget.dialog.GendanDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/23.
 */

public class PaiHangFragment extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView mRv;
    Unbinder unbinder;
    private List<String> list = new ArrayList<>();
    private PaiHangAdapter mAdapter;
    private GendanDialog mGendanDialog;

    public static PaiHangFragment newInstance() {
        PaiHangFragment fragment = new PaiHangFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paihang, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        mAdapter = new PaiHangAdapter(R.layout.item_paihang, list);
        mRv.setLayoutManager(new LinearLayoutManager(mActivity));
        mRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        if (mGendanDialog == null) {
            mGendanDialog = new GendanDialog(mActivity, R.style.MyDialog, new GendanDialog.GenDanListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.tv_gendan:
                            mGendanDialog.dismiss();
                            break;
                        case R.id.tv_deal_records:
                            mGendanDialog.dismiss();
                            break;
                        case R.id.tv_cancel:
                            mGendanDialog.dismiss();
                            break;
                    }
                }
            });
            mGendanDialog.show();
        } else {
            mGendanDialog.show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
