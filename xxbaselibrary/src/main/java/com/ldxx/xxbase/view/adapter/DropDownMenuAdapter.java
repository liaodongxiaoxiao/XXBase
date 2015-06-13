package com.ldxx.xxbase.view.adapter;

import android.content.Context;

import com.ldxx.xxbase.R;
import com.ldxx.xxbase.adapter.XXBaseAdapter;
import com.ldxx.xxbase.bean.XXViewHolder;
import com.ldxx.xxbase.view.DropDownMenuData;

import java.util.List;

/**
 * Created by WangZhuo on 2015/6/9.
 */
public class DropDownMenuAdapter extends XXBaseAdapter<DropDownMenuData> {
    private List<DropDownMenuData> data;
    private Context context;

    public DropDownMenuAdapter(Context context, List<DropDownMenuData> data, int itemLayoutId) {
        super(context, data, itemLayoutId);
        this.context = context;
        this.data = data;
    }

    @Override
    public void convert(XXViewHolder helper, DropDownMenuData item) {
        helper.setText(R.id.dropdownmenu_select_item_title, item.getKey());
    }
}
