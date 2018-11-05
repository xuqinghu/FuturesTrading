package com.xuantie.futures.ui.market.detail;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuantie.futures.R;

import java.util.List;

/**
 * Created by Administrator on 2018/10/15.
 */

public class TimeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int mPosition = -1;

    public TimeAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item);
        if (helper.getAdapterPosition() == mPosition) {
            helper.setVisible(R.id.tv_line, true);
        } else {
            helper.setVisible(R.id.tv_line, false);
        }

    }

    public void selectPositon(int position) {
        this.mPosition = position;
        notifyDataSetChanged();
    }
}
