package com.ldxx.xxbase.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldxx.xxbase.R;
import com.ldxx.xxbase.view.DropDownMenuData;
import com.ldxx.xxbase.view.DropDownMenuView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhuo on 2015/5/7.
 */
public class DetailsFragment extends Fragment {


    private DropDownMenuView kindMenu;
    private DropDownMenuView shopMenu;
    private DropDownMenuView orderMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);


        return view;
    }


}
