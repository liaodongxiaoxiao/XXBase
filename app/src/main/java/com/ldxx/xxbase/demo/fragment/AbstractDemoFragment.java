package com.ldxx.xxbase.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ldxx.xxbase.utils.XXLog;

/**
 * Created by WangZhuo on 2015/5/7.
 */
public abstract class AbstractDemoFragment extends Fragment {
protected String TAG = this.getClass().getSimpleName();
    private ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        XXLog.e(TAG,"hdhdh:"+getImageViewHeight()+" "+getImageViewWidth());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public abstract int getImageViewWidth();
    public abstract int getImageViewHeight();
}
