package com.xuantie.futures.widget.picker;

import com.contrarywind.adapter.WheelAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/9/25.
 */

public class BanksWheelAdapter implements WheelAdapter {
    // items
    private List<BankBean> items;
    /**
     * Constructor
     * @param items the items
     */
    public BanksWheelAdapter(List<BankBean> items) {
        this.items = items;

    }
    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index).getName();
        }
        return "";
    }

    @Override
    public int indexOf(Object o) {
        return items.indexOf(o);
    }
}
