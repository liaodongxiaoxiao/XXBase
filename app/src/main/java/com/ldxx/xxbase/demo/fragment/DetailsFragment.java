package com.ldxx.xxbase.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

import com.ldxx.xxbase.R;
import com.ldxx.xxbase.utils.XXLog;

/**
 * Created by WangZhuo on 2015/5/7.
 */
public class DetailsFragment extends AbstractDemoFragment {

    private ImageView imageView;
    private int w;
    private int h;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo ,container,false);
        imageView  = (ImageView) view.findViewById(R.id.demo_img);
        ViewTreeObserver vto2 = imageView.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                XXLog.e(TAG, "h:" + imageView.getHeight() + ",w:" + imageView.getWidth());
                w =imageView.getWidth();
                h = imageView.getHeight();
            }
        });
        return view;
    }

    @Override
    public int getImageViewWidth() {
        return w;
    }

    @Override
    public int getImageViewHeight() {
        return h;
    }
}
