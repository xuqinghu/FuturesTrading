package com.xuantie.futures.ui.deal.weituo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuantie.futures.R;
import com.xuantie.futures.ui.deal.chicang.ChiCangAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/10/12.
 */

public class WeiTuoAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    public String[] groupString = {"1", "2", "3", "4"};
    public String[][] childString = {{"1"}, {"4"}, {"5"}, {"7"}};

    public WeiTuoAdapter(Context context) {
        this.mContext = context;

    }

    @Override
    public int getGroupCount() {
        return groupString.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childString[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupString[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childString[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        WeiTuoAdapter.GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weituo_partent, parent, false);
            groupViewHolder = new WeiTuoAdapter.GroupViewHolder();
            groupViewHolder.name = convertView.findViewById(R.id.tv_commodity_name);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (WeiTuoAdapter.GroupViewHolder) convertView.getTag();
        }
//        groupViewHolder.name.setText(groupString[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        WeiTuoAdapter.ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weituo_child, parent, false);
            childViewHolder = new WeiTuoAdapter.ChildViewHolder();
            childViewHolder.time = (TextView) convertView.findViewById(R.id.tv_weituo_time);
            convertView.setTag(childViewHolder);

        } else {
            childViewHolder = (WeiTuoAdapter.ChildViewHolder) convertView.getTag();
        }
//        childViewHolder.time.setText(childString[groupPosition][childPosition]);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        TextView name;
    }

    static class ChildViewHolder {
        TextView time;

    }
}
