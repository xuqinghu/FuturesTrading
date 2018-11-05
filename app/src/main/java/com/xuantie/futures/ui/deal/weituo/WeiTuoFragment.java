package com.xuantie.futures.ui.deal.weituo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;
import com.xuantie.futures.ui.deal.chicang.ChiCangAdapter;
import com.xuantie.futures.widget.dialog.CancelCheDanDialog;
import com.xuantie.futures.widget.dialog.WeiTuoDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/10/9.
 * 委托
 */

public class WeiTuoFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.elv)
    ExpandableListView mElv;
    private WeiTuoAdapter mAdapter;
    private WeiTuoDialog dialog;
    private CancelCheDanDialog mCheDanDialog;
    private boolean isDialog = false;
    private int lastPosition = -1;

    public static WeiTuoFragment newInstance() {
        WeiTuoFragment fragment = new WeiTuoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weituo, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mAdapter = new WeiTuoAdapter(mActivity);
        mElv.setAdapter(mAdapter);
        mElv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (lastPosition == groupPosition && isDialog == false) {
                    showDialog();
                } else {
                    if (isDialog) {
                        showDialog();
                        isDialog = false;
                    } else {
                        isDialog = true;
                    }
                }
                lastPosition = groupPosition;
                for (int i = 0; i < 4; i++) {
                    if (groupPosition == i) {
                        mElv.expandGroup(i);
                    } else {
                        mElv.collapseGroup(i);
                    }
                }
                return true;
            }
        });
    }

    //底部dialog
    private void showDialog() {
        if (dialog == null) {
            dialog = new WeiTuoDialog(mActivity, R.style.MyDialog,
                    new WeiTuoDialog.WeiTuoListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.tv_chedan:
                                    dialog.dismiss();
                                    break;
                                case R.id.tv_kline:
                                    dialog.dismiss();
                                    break;
                                case R.id.tv_chedan_all:
                                    dialog.dismiss();
                                    showCheDanDialog();
                                    break;
                                case R.id.tv_cancel:
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    });
        }
        dialog.show();
    }

    //撤单dialog
    private void showCheDanDialog() {
        if (mCheDanDialog == null) {
            mCheDanDialog = new CancelCheDanDialog(mActivity, R.style.MyDialog, new CancelCheDanDialog.CancelCheDanDialogListener() {
                @Override
                public void onClickView(View view) {
                    switch (view.getId()) {
                        case R.id.bt_cancel:
                            mCheDanDialog.dismiss();
                            break;
                        case R.id.bt_confirm:
                            mCheDanDialog.dismiss();
                            break;
                    }
                }
            });
        }
        mCheDanDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
