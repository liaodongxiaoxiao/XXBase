package com.ldxx.xxbase.demo.service;

import android.app.IntentService;
import android.content.Intent;

import com.ldxx.utils.DateUtils;
import com.ldxx.xxbase.demo.app.XXDemoApplication;
import com.ldxx.xxbase.demo.bean.NewsInfo;
import com.ldxx.xxbase.demo.fragment.HomeFragment;
import com.ldxx.xxbase.utils.CommonUtils;
import com.ldxx.xxbase.utils.XXLog;
import com.lidroid.xutils.DbUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class LoadDataIntentService extends IntentService {

    private String TAG = this.getClass().getSimpleName();

    public LoadDataIntentService() {
        super("LoadDataIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        XXLog.e(TAG, "Service .....");
        int i=0;
        if (intent != null) {
            final String action = intent.getAction();
            DbUtils db = DbUtils.create(getApplicationContext(), XXDemoApplication.DB_NAME);

            try {
                Document doc = Jsoup.connect("http://sports.sina.com.cn/g/premierleague/").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").timeout(30000).get();
                Elements es = doc.select("#J_Focus_Player_Wrap").select(".banner_img");

                Iterator<Element> iterator = es.iterator();
                Element e;
                Element aE;
                NewsInfo news = new NewsInfo();
                while (iterator.hasNext()) {
                    e = iterator.next();
                    aE = e.select("a").first();
                    news.setPid(CommonUtils.getUUID());
                    news.setImage_src(e.select("img").first().attr("src"));
                    news.setTitle(aE.attr("title"));
                    news.setUrl(aE.attr("href"));
                    news.setCreate_time(DateUtils.getCurrentTimeStr());
                    try{
                        db.save(news);
                    }catch (Exception eS){
                        i++;
                    }

                }
            } catch (Exception e) {
                XXLog.e(TAG, e.getMessage(), e);
            } finally {
                XXLog.e(TAG,"新插入"+(5-i)+"条数据");
                if(i<5){
                    Intent intentH = new Intent();
                    intentH.setAction(HomeFragment.LOAD_DATA_ACTION);
                    sendBroadcast(intentH);
                }
            }
        }
    }


}
