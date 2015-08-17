package com.ldxx.xxbase.demo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;


import com.ldxx.xxbase.demo.R;
import com.ldxx.xxbase.demo.bean.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by wangzhuo on 2015/7/15.
 */
public class PersonArrayAdapter extends ArrayAdapter<Person> {
    private PersonFilter filter;
    private final List<Person> data;
    private List<Person> list = new ArrayList<>();
    private int resource;
    private LayoutInflater inflater;

    public PersonArrayAdapter(Context context, int resource, List<Person> objects) {
        super(context, resource, objects);
        this.data = objects;
        if (objects != null && !objects.isEmpty()) {
            this.list.addAll(objects);
        }
        this.list = objects;
        this.resource = resource;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(resource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.person_name);
            viewHolder.tel = (TextView) convertView.findViewById(R.id.person_tel);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Person p = list.get(position);
        viewHolder.name.setText(p.getUserName());
        viewHolder.tel.setText(p.getTel());

        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Person getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new PersonFilter();
        }
        return filter;
    }

    @Override
    public void sort(Comparator<? super Person> comparator) {
        Collections.sort(list, comparator);
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView name;
        TextView tel;
    }

    class PersonFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults filterResults = new FilterResults();
            if (!TextUtils.isEmpty(constraint)) {
                final List<Person> persons = new ArrayList<>();
                for (Person p : data) {
                    if (!TextUtils.isEmpty(p.getUserName()) && p.getUserName().contains(constraint)) {
                        persons.add(p);
                    }
                }
                filterResults.count = persons.size();
                filterResults.values = persons;
            } else {
                filterResults.count = data.size();
                filterResults.values = data;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //list.clear();
            //list.addAll((Collection<? extends Person>) results.values);
            list = (List<Person>) results.values;
            notifyDataSetChanged();
        }
    }
}
