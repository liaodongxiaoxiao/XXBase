package com.ldxx.xxbase.demo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ldxx.xxbase.demo.R;
import com.ldxx.xxbase.adapter.XXBaseAdapter;
import com.ldxx.xxbase.bean.XXViewHolder;
import com.ldxx.xxbase.demo.activity.DemoAppBarLayoutActivity;
import com.ldxx.xxbase.demo.activity.DemoCustomViewActivity;
import com.ldxx.xxbase.demo.activity.DemoTextInputLayoutActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhuo on 2015/5/30.
 */
public class FragmentDemoNewViews extends Fragment {

    private GridView gridView;
    private List<Beans> data = new ArrayList<>();
    private NewViewsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_view, container, false);
        gridView = (GridView) view.findViewById(R.id.f_n_v_grid);
        initData();
        adapter = new NewViewsAdapter(getActivity(), data, R.layout.item_demo_fragment_newviews);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Beans beans = adapter.getItem(position);
                getActivity().startActivity(new Intent(getActivity(), beans.cc));
            }
        });
        return view;
    }

    private void initData() {
        data.add(new Beans("AppBarLayout", "【新闻详情】", DemoAppBarLayoutActivity.class));
        data.add(new Beans("TextInputLayout", "【吼吼】", DemoTextInputLayoutActivity.class));
        data.add(new Beans("自定义控件", "【各种自定义控件】", DemoCustomViewActivity.class));
        data.add(new Beans("AppBarLayout", "【新闻详情】", DemoAppBarLayoutActivity.class));
        data.add(new Beans("AppBarLayout", "【新闻详情】", DemoAppBarLayoutActivity.class));
    }

    class Beans {
        private String title;
        private String info;
        private Class cc;

        public Beans(String title, String info, Class cc) {
            this.title = title;
            this.info = info;
            this.cc = cc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public Class getCc() {
            return cc;
        }

        public void setCc(Class cc) {
            this.cc = cc;
        }
    }

    class NewViewsAdapter extends XXBaseAdapter<Beans> {

        public NewViewsAdapter(Context context, List<Beans> data, int itemLayoutId) {
            super(context, data, itemLayoutId);
        }

        @Override
        public void convert(XXViewHolder helper, Beans item) {
            helper.setText(R.id.item_d_f_n_title, item.getTitle()).setText(R.id.item_d_f_n_info, item.getInfo());
        }
    }
}
