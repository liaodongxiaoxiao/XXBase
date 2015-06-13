package com.ldxx.xxbase.demo.adapter;

import android.content.Context;

import com.ldxx.xxbase.demo.R;
import com.ldxx.xxbase.adapter.XXBaseAdapter;
import com.ldxx.xxbase.bean.XXViewHolder;
import com.ldxx.xxbase.demo.bean.MenuInfo;

import java.util.List;

/**
 * Created by wangzhuo-neu on 2014/12/23.
 */
public class NavigationMenuAdapter extends XXBaseAdapter<MenuInfo> {

    public NavigationMenuAdapter(Context context, List<MenuInfo> data, int itemLayoutId) {
        super(context, data, itemLayoutId);
    }

    @Override
    public void convert(XXViewHolder helper, MenuInfo item) {
        helper.setImageResource(R.id.menu_icon, item.getImg_src()).setText(R.id.menu_name, item.getMenu_name());
    }
}
