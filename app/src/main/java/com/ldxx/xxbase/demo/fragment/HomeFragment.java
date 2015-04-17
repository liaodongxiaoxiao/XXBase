package com.ldxx.xxbase.demo.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.ldxx.xxbase.R;
import com.ldxx.xxbase.common.BitmapCache;
import com.ldxx.xxbase.demo.app.XXDemoApplication;
import com.ldxx.xxbase.demo.bean.NewsInfo;
import com.ldxx.xxbase.utils.XXToast;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class HomeFragment extends Fragment {
    public static final String LOAD_DATA_ACTION = "com.ldxx.xxbase.demo.fragment.HomeFragment.loadData";
    private DataLoadedReceiver receiver;
    private ViewPager viewPager;
    private List<NewsInfo> data = new ArrayList<>();
    private ImageLoader mImageLoader;
    private ImageLoader.ImageListener listener;

    //将图片装载到数组中
    ImageView[] imgS = new ImageView[5];


    /**
     * @return
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new DataLoadedReceiver();
        IntentFilter intentFilter = new IntentFilter(LOAD_DATA_ACTION);
        getActivity().registerReceiver(receiver, intentFilter);

        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        mImageLoader = new ImageLoader(mQueue, new BitmapCache());

        ImageView imageView;
        for (int i = 0; i < 5; i++) {
            imageView = new ImageView(getActivity());
            imgS[i] = imageView;
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        //加载本地数据
        loadData();

    }

    private void loadData() {
        DbUtils db = DbUtils.create(getActivity(), XXDemoApplication.DB_NAME);
        try {
            List<NewsInfo> news = db.findAll(Selector.from(NewsInfo.class).orderBy("create_time", true).limit(5));
            if (!news.isEmpty()) {
                data.clear();
                data.addAll(news);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.main_pager);
        viewPager.setAdapter(new NewsPageAdapter());
        return v;
    }

    public class DataLoadedReceiver extends BroadcastReceiver {
        public DataLoadedReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            XXToast.showShort(getActivity(), "数据加载成功...");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
    }


    public class NewsPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(imgS[position]);
        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
            ImageView imageView = imgS[position];
            listener = ImageLoader.getImageListener(imageView, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);
            NewsInfo news = data.get(position);
            mImageLoader.get(news.getImage_src(), listener);
            ((ViewPager) container).addView(imageView, 0);
            return imgS[position];
        }

    }
}
