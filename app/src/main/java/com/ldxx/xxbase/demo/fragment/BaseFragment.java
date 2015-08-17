package com.ldxx.xxbase.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.ldxx.xxbase.demo.R;
import com.ldxx.xxbase.demo.activity.ListFilterActivity;
import com.ldxx.xxbase.demo.adapter.PersonArrayAdapter;
import com.ldxx.xxbase.demo.bean.PersonComparator;


/**
 * Created by wangzhuo on 2015/7/15.
 */
public class BaseFragment extends Fragment {
    private ListView listView;
    private PersonArrayAdapter adapter;

    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_filter, container, false);
        listView = (ListView) view.findViewById(R.id.array_list);
        adapter = new PersonArrayAdapter(getActivity(), R.layout.person_item, ListFilterActivity.getPersons());
        adapter.sort(new PersonComparator());
        listView.setAdapter(adapter);
        editText = (EditText) view.findViewById(R.id.edit_query);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
}
