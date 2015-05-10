package com.ldxx.xxbase.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ldxx.xxbase.R;
import com.ldxx.xxbase.utils.XXLog;

/**
 * Created by WangZhuo on 2015/5/10.
 */
public class ViewWidthHeightFragment extends Fragment {
    private String TAG = this.getClass().getSimpleName();
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        imageView = (ImageView) view.findViewById(R.id.demo_img);
        XXLog.e(TAG, "img width:" + imageView.getWidth() + " height:" + imageView.getHeight());
        view.post(new Runnable() {
            @Override
            public void run() {
                //view.getHeight(); //height is ready
                XXLog.e(TAG, "img width:" + imageView.getWidth() + " height:" + imageView.getHeight()+" "+imageView.getMeasuredWidth());
            }
        });
        return view;
    }
}
