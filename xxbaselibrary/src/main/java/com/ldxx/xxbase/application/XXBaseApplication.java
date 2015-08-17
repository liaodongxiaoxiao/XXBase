package com.ldxx.xxbase.application;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.ldxx.xxbase.common.BitmapCache;

/**
 * Created by WangZhuo on 2015/7/2.
 */
public class XXBaseApplication extends Application {
    private final String TAG = this.getClass().getSimpleName();

    private static XXBaseApplication application;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public static synchronized XXBaseApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        if (requestQueue == null) {
            getRequestQueue();
        }

        if (imageLoader == null) {
            imageLoader = new ImageLoader(this.requestQueue, new BitmapCache());
        }
        return imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req,String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
