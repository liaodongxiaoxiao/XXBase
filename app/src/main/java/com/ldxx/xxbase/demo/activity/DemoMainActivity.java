package com.ldxx.xxbase.demo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ldxx.xxbase.R;
import com.ldxx.xxbase.activity.BaseActivity;
import com.ldxx.xxbase.demo.adapter.NavigationMenuAdapter;
import com.ldxx.xxbase.demo.bean.MenuInfo;
import com.ldxx.xxbase.demo.fragment.HomeFragment;
import com.ldxx.xxbase.utils.XXToast;

import java.util.ArrayList;
import java.util.List;

public class DemoMainActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

    private ListView menuList;
    private NavigationMenuAdapter adapter;
    private List<MenuInfo> menuData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);
        menuData = getMenuData();
        mToolbar = (Toolbar) findViewById(R.id.common_actionbar);
        // toolbar.setLogo(R.drawable.ic_launcher);
        mToolbar.setTitle(R.string.title_home);// 标题的文字需在setSupportActionBar之前，不然会无效
        // toolbar.setSubtitle("副标题");
        setSupportActionBar(mToolbar);
        /* 这些通过ActionBar来设置也是一样的，注意要在setSupportActionBar(toolbar);之后，不然就报错了 */
        // getSupportActionBar().setTitle("标题");
        // getSupportActionBar().setSubtitle("副标题");
        // getSupportActionBar().setLogo(R.drawable.ic_launcher);

		/* 菜单的监听可以在toolbar里设置，也可以像ActionBar那样，通过下面的两个回调方法来处理 */
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        XXToast.showShort(DemoMainActivity.this, "action_settings");
                        break;

                    default:
                        break;
                }
                return true;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        menuList = (ListView) findViewById(R.id.menu_list);
        adapter = new NavigationMenuAdapter(this, menuData);
        menuList.setAdapter(adapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuInfo menuInfo = (MenuInfo) adapter.getItem(position);


                mToolbar.setTitle(menuInfo.getMenu_name());
                // update the main content by replacing fragments
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.demo_content_container, menuInfo.getFragment())
                        .commit();

                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        //默认选中第一页
        initDefaultFragment();

    }

    private void initDefaultFragment(){
        //默认选中第一个
        menuList.setSelection(0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.demo_content_container, menuData.get(0).getFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo_b, menu);
        return true;
    }



    private List<MenuInfo> getMenuData() {
        List<MenuInfo> list = new ArrayList<>();
        list.add(new MenuInfo(R.string.title_home, R.mipmap.menu_home, HomeFragment.newInstance()));
        return list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        return super.onOptionsItemSelected(item);

    }


}
