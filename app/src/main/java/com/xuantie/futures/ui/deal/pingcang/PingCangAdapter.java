package com.xuantie.futures.ui.deal.pingcang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.xuantie.futures.R;

/**
 * Created by Administrator on 2018/10/23.
 */

public class PingCangAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    public String[] groupString = {"1", "2", "3", "4"};
    public String[][] childString = {{"1"}, {"4"}, {"5"}, {"7"}};

    public PingCangAdapter(Context context) {
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
        PingCangAdapter.GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pingcang_partent, parent, false);
            groupViewHolder = new PingCangAdapter.GroupViewHolder();
            groupViewHolder.name = convertView.findViewById(R.id.tv_commodity_name);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (PingCangAdapter.GroupViewHolder) convertView.getTag();
        }
//        groupViewHolder.name.setText(groupString[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PingCangAdapter.ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pingcang_child, parent, false);
            childViewHolder = new PingCangAdapter.ChildViewHolder();
            childViewHolder.time = convertView.findViewById(R.id.tv_pingcang_time);
            convertView.setTag(childViewHolder);

        } else {
            childViewHolder = (PingCangAdapter.ChildViewHolder) convertView.getTag();
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
