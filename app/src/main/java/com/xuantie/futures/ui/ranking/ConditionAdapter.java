package com.xuantie.futures.ui.ranking;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuantie.futures.R;

import java.util.List;

/**
 * Created by Administrator on 2018/10/24.
 */

public class ConditionAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public ConditionAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_condition_name,item);
    }
}
