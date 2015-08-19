package com.ldxx.xxbase.demo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ldxx.xxbase.activity.BaseActivity;
import com.ldxx.xxbase.demo.R;
import com.ldxx.xxbase.demo.app.XXDemoApplication;
import com.ldxx.xxbase.demo.bean.Weather;
import com.ldxx.xxbase.net.JsonObjectRequest;
import com.ldxx.xxbase.view.JsonTextView;

import java.util.HashMap;
import java.util.Map;

public class VolleyActivity extends BaseActivity {

    private JsonTextView dataJsonTv;
    private TextView dataTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        initActionBar();
        initView();
    }

    private void initView() {
        dataJsonTv = (JsonTextView) findViewById(R.id.data_json);
        dataTv = (TextView) findViewById(R.id.data);
    }

    public void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.common_actionbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        toolbar.setTitle("Volley");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_volley, menu);
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

    public void getPMValue(View view) throws JSONException {
        String url = "http://apis.baidu.com/apistore/weatherservice/weather?citypinyin=shenyang";
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        getJSONObject(pDialog,url);

    }


    private void getJSONObject(final ProgressDialog pDialog,final String url){
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, response.toString());
                        dataJsonTv.setText(response.toString());
                        if(response.containsKey("retData")){

                            Weather weather = JSON.parseObject(response.getString("retData"),Weather.class);
                            dataTv.setText(weather.toString());
                        }

                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apiKey", "d6e91c2b841ef37858964106aa83749c");
                return headers;
            }


        };

        // Adding request to request queue
        XXDemoApplication.getApplication().addToRequestQueue(jsonObjReq, "pm2.5");
    }
}
