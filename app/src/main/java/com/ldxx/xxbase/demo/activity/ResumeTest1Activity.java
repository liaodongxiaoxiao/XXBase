package com.ldxx.xxbase.demo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ldxx.xxbase.demo.R;

public class ResumeTest1Activity extends AppCompatActivity {

    private int i = -1;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_test1);
        tv = (TextView) findViewById(R.id.tv);
        setInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resume_test1, menu);
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

    public void setValue(View view) {
        i += 1;
        setInfo();
    }

    public void next(View view) {
        startActivity(new Intent(this, ResumeTest2Activity.class));
    }

    private void setInfo(){
        tv.setText("i:" + i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setInfo();
    }
}
