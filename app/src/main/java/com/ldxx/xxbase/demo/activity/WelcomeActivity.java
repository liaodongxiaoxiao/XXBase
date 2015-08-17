package com.ldxx.xxbase.demo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ldxx.xxbase.demo.R;
import com.ldxx.xxbase.demo.service.LoadDataIntentService;
import com.ldxx.xxbase.utils.XXAppUtils;
import com.ldxx.xxbase.utils.XXDBUtils;
import com.ldxx.xxbase.utils.XXLog;


public class WelcomeActivity extends ActionBarActivity {
    private String TAG =this.getClass().getSimpleName();
    private JumpHandler handler = new JumpHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //启动加载数据的service
        startService(new Intent(WelcomeActivity.this, LoadDataIntentService.class));
        Log.e(TAG, "app name:" + XXAppUtils.getAppName(this));
        Log.e(TAG, "version:" + XXAppUtils.getVersionName(this));
        Log.e(TAG,"package name:"+XXAppUtils.getPackageName(this));
        XXDBUtils.copyDBtoSDcard(this);
        handler.sendEmptyMessageDelayed(1, 3000);

    }

    class JumpHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(WelcomeActivity.this, DemoMainActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcom, menu);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
