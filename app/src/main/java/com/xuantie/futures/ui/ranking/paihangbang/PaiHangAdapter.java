package com.xuantie.futures.ui.ranking.paihangbang;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/10/23.
 */

public class PaiHangAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PaiHangAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
