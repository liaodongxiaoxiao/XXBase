package com.ldxx.xxbase.demo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ldxx.xxbase.demo.R;

import java.util.List;

/**
 * Created by WANGZHUO on 2015/4/14.
 */
public class ColorAdapter extends BaseAdapter
{
    List<String> data ;
    LayoutInflater inflater;
    public ColorAdapter(Context context,List<String> data){
        this.data = data;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh =null;
        if(convertView==null){
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.demo_color_item,parent,false);
            vh.tv = (TextView) convertView.findViewById(R.id.demo_color_item);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        String color = data.get(position);
        vh.tv.setText(color);
        vh.tv.setBackgroundColor(Color.parseColor(color));
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    class ViewHolder {
        TextView tv;
    }
}
