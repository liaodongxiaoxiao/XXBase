package com.ldxx.xxbase.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldxx.xxbase.demo.R;
import com.ldxx.xxbase.demo.bean.NewsInfo;

import java.util.List;

/**
 * Created by WangZhuo on 2015/7/2.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    //private Context context;
    private List<NewsInfo> data;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public RecyclerViewAdapter(Context context, List<NewsInfo> data) {
        //this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewHolder holder = new ViewHolder(inflater.inflate(R.layout.recycler_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        NewsInfo ni = data.get(i);
        viewHolder.title.setText(ni.getTitle());
        viewHolder.content.setText(ni.getUrl());
        viewHolder.date.setText(ni.getCreate_time());
        //设置事件监听
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, i);
                }
            });

        }
        if (onItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.onItemLongClick(v, i);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addDatas(List<NewsInfo> newsInfos) {
        data.addAll(newsInfos);
        for (NewsInfo nw:newsInfos){
            data.add(data.size()-1,nw);
            notifyItemInserted(data.size()-1);
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView content;
        private TextView date;
        private TextView read;
        private TextView love;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.r_img);
            title = (TextView) itemView.findViewById(R.id.r_title);
            content = (TextView) itemView.findViewById(R.id.r_content);
            date = (TextView) itemView.findViewById(R.id.r_date);
            read = (TextView) itemView.findViewById(R.id.r_read);
            love = (TextView) itemView.findViewById(R.id.r_love);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
