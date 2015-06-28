package com.ldxx.xxbase.demo.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ldxx.xxbase.demo.R;
import com.ldxx.xxbase.activity.BaseActivity;
import com.ldxx.xxbase.view.DropDownMenuData;
import com.ldxx.xxbase.view.DropDownMenuView;
import com.ldxx.xxbase.view.adapter.DropDownMenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class DemoCustomViewActivity extends BaseActivity {
    private DropDownMenuView menu1;
    private DropDownMenuView menu2;
    private Spinner spinner1;
    private Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_custom_view);

        menu1 = (DropDownMenuView) findViewById(R.id.menu1);
        menu2 = (DropDownMenuView) findViewById(R.id.menu2);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        // 建立数据源
        String[] mItems1 = {"城市", "沈阳", "大连", "鞍山"};
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems1);
        spinner1.setAdapter(adapter1);

        String[] mItems2 = {"城市", "沈阳", "大连", "鞍山"};
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems2);
        spinner2.setAdapter(adapter2);
        List<DropDownMenuData> kind = new ArrayList<>();
        kind.add(new DropDownMenuData("全部分类", "01"));
        kind.add(new DropDownMenuData("中餐", "02"));
        kind.add(new DropDownMenuData("西餐", "03"));
        menu1.setAdapter(new DropDownMenuAdapter(this, kind, R.layout.dropdownmenu_select_item));
        menu1.setMenuSelectedListener(new DropDownMenuView.MenuSelectedListener() {
            @Override
            public void onMenuSelected(DropDownMenuData menuData) {
                Toast.makeText(DemoCustomViewActivity.this, "选中：" + menuData.getKey() + " 值是：" + menuData.getValue() , Toast.LENGTH_SHORT).show();
            }
        });

        List<DropDownMenuData> shop = new ArrayList<>();
        shop.add(new DropDownMenuData("全部商家", "01"));
        shop.add(new DropDownMenuData("四季面条", "02"));
        shop.add(new DropDownMenuData("KFC", "03"));
        shop.add(new DropDownMenuData("农家小炒", "04"));
        shop.add(new DropDownMenuData("老四烧烤", "05"));
        menu2.setAdapter(new DropDownMenuAdapter(this, shop, R.layout.dropdownmenu_select_item));

        initActionBar();
    }

    private void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.common_actionbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("自定义控件");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo_custom_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.home) {
            finish();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
