package com.ldxx.xxbase.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.ldxx.xxbase.R;

public class BaseActivity extends ActionBarActivity {
    public static final String TAG = BaseActivity.class.getSimpleName();
    public static String PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PACKAGE_NAME = this.getPackageName();
    }

    public ActionBar initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.common_actionbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        return actionBar;
    }
}
