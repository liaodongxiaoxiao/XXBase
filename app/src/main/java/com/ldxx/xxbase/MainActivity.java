package com.ldxx.xxbase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ldxx.xxbase.activity.BaseActivity;
import com.ldxx.xxbase.demo.adapter.ColorAdapter;
import com.ldxx.xxbase.demo.adapter.activity.DemoBActivity;
import com.ldxx.xxbase.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private GridView gridView;
    private ColorAdapter adapter;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBars();

        gridView = (GridView) findViewById(R.id.demo_grid);
        data = getData();
        adapter = new ColorAdapter(this,data);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, DemoBActivity.class));
            }
        });

    }

    public ActionBar initActionBars() {
        ActionBar actionBar = getSupportActionBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.common_actionbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        return actionBar;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    public List<String> getData() {
        List<String> d = new ArrayList<>();
        for (int i=0;i<10;i++){
            d.add(CommonUtils.getRandColorCode());
        }
        return d;
    }
}
