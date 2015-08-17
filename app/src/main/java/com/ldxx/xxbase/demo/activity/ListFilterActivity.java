package com.ldxx.xxbase.demo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ldxx.xxbase.demo.R;
import com.ldxx.xxbase.demo.bean.Person;
import com.ldxx.xxbase.demo.fragment.ArrayFragment;
import com.ldxx.xxbase.demo.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ListFilterActivity extends AppCompatActivity {


    private Toolbar toolbar;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_filter);
        initActionBar();
        initView();
    }

    private void initActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
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

    public static List<Person> getPersons() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("王力宏", "1338221232"));
        persons.add(new Person("王强", "1338221232"));
        persons.add(new Person("王宏", "1338221232"));
        persons.add(new Person("李刚", "1338221232"));
        persons.add(new Person("孙茜", "1338221232"));
        persons.add(new Person("丁丁", "1338221232"));
        persons.add(new Person("阿杜", "1338221232"));
        persons.add(new Person("王芷", "1338221232"));
        persons.add(new Person("刘华", "1338221232"));


        return persons;
    }

    class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ArrayFragment();
            } else {
                return new BaseFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Tab 1";
            } else {
                return "Tab 2";
            }
        }
    }
}
