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
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private TextView newsTitle;
    private LinearLayout group;

    //将图片装载到数组中
    private ImageView[] imgS = new ImageView[5];
    private ImageView[] backImages = new ImageView[5];


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
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


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
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
        group = (LinearLayout) v.findViewById(R.id.viewGroup);
        ImageView b;
        for (int i=0;i<data.size();i++){
            b = new ImageView(getActivity());
            b.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
            b.setPadding(5, 5, 5, 5);
            backImages[i] = b;
            if (i == 0) {
                backImages[i]
                        .setBackgroundResource(R.mipmap.banner_dian_focus);
            } else {
                backImages[i]
                        .setBackgroundResource(R.mipmap.banner_dian_blur);
            }
            group.addView(backImages[i]);
        }
        newsTitle = (TextView) v.findViewById(R.id.news_title);
        viewPager.setCurrentItem(0);
        newsTitle.setText(data.get(0).getTitle());
        return v;
    }

    /**
     * 数据加载成功接收器
     */
    public class DataLoadedReceiver extends BroadcastReceiver {
        public DataLoadedReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            XXToast.showShort(getActivity(), R.string.load_data_succes);
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
            newsTitle.setText(news.getTitle());
            return imgS[position];
        }

    }

    private final class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            //what.getAndSet(arg0);
            for (int i = 0; i < backImages.length; i++) {

                if (arg0 == i) {
                    backImages[i]
                            .setBackgroundResource(R.mipmap.banner_dian_focus);
                    newsTitle.setText(data.get(arg0).getTitle());
                }else {
                    backImages[arg0]
                            .setBackgroundResource(R.mipmap.banner_dian_blur);
                }
            }

        }

    }
}
