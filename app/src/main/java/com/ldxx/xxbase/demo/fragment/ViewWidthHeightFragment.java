package com.ldxx.xxbase.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldxx.xxbase.demo.R;
import com.ldxx.xxbase.view.DropDownMenuData;
import com.ldxx.xxbase.view.DropDownMenuView;
import com.ldxx.xxbase.view.adapter.DropDownMenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhuo on 2015/5/10.
 */
public class ViewWidthHeightFragment extends Fragment {
    private String TAG = this.getClass().getSimpleName();
    private DropDownMenuView kindMenu;
    private DropDownMenuView shopMenu;
    private DropDownMenuView orderMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        kindMenu = (DropDownMenuView) view.findViewById(R.id.demo_menu_kind);
        shopMenu = (DropDownMenuView) view.findViewById(R.id.demo_menu_shop);
        orderMenu = (DropDownMenuView) view.findViewById(R.id.demo_menu_order);

        List<DropDownMenuData> kind = new ArrayList<>();
        kind.add(new DropDownMenuData("全部分类", ""));
        kind.add(new DropDownMenuData("中餐", ""));
        kind.add(new DropDownMenuData("西餐", ""));
        // menu1.setAdapter(new DropDownMenuAdapter(this,kind, R.layout.dropdownmenu_select_item));
        kindMenu.setAdapter(new DropDownMenuAdapter(getActivity(),kind, R.layout.dropdownmenu_select_item));

        List<DropDownMenuData> shop = new ArrayList<>();
        shop.add(new DropDownMenuData("全部商家", ""));
        shop.add(new DropDownMenuData("四季面条", ""));
        shop.add(new DropDownMenuData("KFC", ""));
        shop.add(new DropDownMenuData("农家小炒", ""));
        shop.add(new DropDownMenuData("老四烧烤", ""));
        //shopMenu.setData(shop);
        shopMenu.setAdapter(new DropDownMenuAdapter(getActivity(),shop,R.layout.dropdownmenu_select_item));

        List<DropDownMenuData> order = new ArrayList<>();
        order.add(new DropDownMenuData("默认排序", ""));
        order.add(new DropDownMenuData("按价格", ""));
        order.add(new DropDownMenuData("按订单量", ""));
        order.add(new DropDownMenuData("按好评", ""));
        //orderMenu.setData(order);
        orderMenu.setAdapter(new DropDownMenuAdapter(getActivity(),order,R.layout.dropdownmenu_select_item));

        getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return view;
    }
}
