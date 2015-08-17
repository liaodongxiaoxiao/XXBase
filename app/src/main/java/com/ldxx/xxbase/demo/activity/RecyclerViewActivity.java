package com.ldxx.xxbase.demo.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ldxx.xxbase.activity.BaseActivity;
import com.ldxx.xxbase.demo.R;
import com.ldxx.xxbase.demo.adapter.RecyclerViewAdapter;
import com.ldxx.xxbase.demo.app.XXDemoApplication;
import com.ldxx.xxbase.demo.bean.DividerItemDecoration;
import com.ldxx.xxbase.demo.bean.NewsInfo;
import com.ldxx.xxbase.utils.XXLog;
import com.ldxx.xxbase.view.SwipeRefreshLayout;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView demo
 * 包路径：
 * android.support.v7.widget.RecyclerView
 */
public class RecyclerViewActivity extends BaseActivity {
    //private BGARefreshLayout bgaRefreshLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<NewsInfo> data = new ArrayList<>();
    private RecyclerViewAdapter adapter;

    private LinearLayoutManager linearLayoutManager;
    private boolean mLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initActionBar();
        initData();
        //initRefreshLayout();
        initView();
    }


    private void initData() {
        DbUtils db = DbUtils.create(getApplicationContext(), XXDemoApplication.DB_NAME);
        try {
            List<NewsInfo> l = db.findAll(Selector.from(NewsInfo.class).orderBy("create_time", true).limit(5));
            if (l != null && !l.isEmpty()) {
                data.addAll(l);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.common_actionbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("RecyclerView Demo");
    }

    private void initView() {

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.rl_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadDataAsyncTask().execute();
            }
        });

        swipeRefreshLayout.setColorSchemeColors(R.color.text_bg, R.color.btn_color3_selected, R.color.btn_color1, R.color.btn_color2_selected);

        //获取android.support.v7.widget.RecyclerView对象
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //设置布局管理器
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置Adapter
        adapter = new RecyclerViewAdapter(this, data);
        recyclerView.setAdapter(adapter);
        //设置移动动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        //添加和删除调用Adapter的 notifyItemInserted(position)与notifyItemRemoved(position)
        //设置item点击事件监听
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(RecyclerViewActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onItemClick :" + position);
            }
        });

       /* recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!mLoading && lastVisibleItem == totalItem - 1) {
                    new LoadMoreAsyncTask().execute();
                }
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoadDataAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                mLoading = true;
                Thread.sleep(4000l);
                mLoading = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    class LoadMoreAsyncTask extends AsyncTask<String, Void, List<NewsInfo>> {

        private ProgressDialog progressDialog;

        @Override
        protected List<NewsInfo> doInBackground(String... params) {
            mLoading=true;

            if(params.length<=0){
                return null;
            }
            List<NewsInfo> l = null;
            DbUtils db = DbUtils.create(getApplicationContext(), XXDemoApplication.DB_NAME);
            try {
                l = db.findAll(Selector.from(NewsInfo.class).where("create_time", "<=", params[0]).orderBy("create_time", true).limit(5));
                if (l != null && !l.isEmpty()) {
                    data.addAll(l);
                }
            } catch (DbException e) {
                //e.printStackTrace();
                XXLog.e(TAG, e.getMessage(), e);
            }
            return l;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RecyclerViewActivity.this);
            progressDialog.setMessage("Loading More ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(List<NewsInfo> newsInfos) {
            super.onPostExecute(newsInfos);
            mLoading=false;
            if(newsInfos!=null&&!newsInfos.isEmpty()){

                adapter.addDatas(newsInfos);
                data.addAll(newsInfos);
            }
            progressDialog.dismiss();
        }
    }
}
