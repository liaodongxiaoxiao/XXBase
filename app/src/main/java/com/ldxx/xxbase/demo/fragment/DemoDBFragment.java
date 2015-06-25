package com.ldxx.xxbase.demo.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ldxx.xxbase.db.XXDBHelper;
import com.ldxx.xxbase.demo.R;

/**
 * SQLite data type:
 *
 *  NULL，值是NULL
 *  INTEGER，值是有符号整形，根据值的大小以1,2,3,4,6或8字节存放
 *  REAL，值是浮点型值，以8字节IEEE浮点数存放
 *  TEXT，值是文本字符串，使用数据库编码（UTF-8，UTF-16BE或者UTF-16LE）存放
 *  BLOB，只是一个数据块，完全按照输入存放（即没有准换）
 *
 * REAL或Double类型的字段
 * cursor.getString()的方式获取，取到的值是 保留6为数字 末位四舍五入
 *
 * 例如： 90.123462 getString 为 90.1235
 *
 * SQLite 不支持别名
 *
 */
public class DemoDBFragment extends Fragment {
    private Button btn_init;
    private Button btn_select;
    private TextView tv_one;
    private TextView tv_two;
    private XXDBHelper xxdbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_db, container, false);
        btn_init = (Button) view.findViewById(R.id.btn_init);
        btn_select = (Button) view.findViewById(R.id.btn_select);
        tv_one = (TextView) view.findViewById(R.id.tv_value_one);
        tv_two = (TextView) view.findViewById(R.id.tv_value_two);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] createSql = {" create table a (id varchar(36) ,name text,type text, price double,cost real)",
                "create table b (id varchar(36),type_value text,type_name text)"};
        xxdbHelper = new XXDBHelper(getActivity(), "data_test.db", 1, createSql, null);

        btn_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectValue();
            }
        });
    }

    private void selectValue() {
        SQLiteDatabase db = xxdbHelper.getReadableDatabase();
        Cursor cursor = db.query("a", new String[]{"name", "price", "cost"}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String s1 = "";
            String s2 = "";
            do {
                s1 = s1 + cursor.getString(0) + "  " + cursor.getString(1) + "   " + cursor.getString(2) + "\n";
                s2 = s2 + cursor.getString(0) + "  " + cursor.getDouble(1) + "   " + cursor.getDouble(2) + "\n";
            } while (cursor.moveToNext());

            tv_one.setText(s1);
            tv_two.setText(s2);

            cursor.close();
        }
    }


    private void initData() {
        SQLiteDatabase db = xxdbHelper.getWritableDatabase();
        db.execSQL(" insert into a values('123123','大米粉','01',100.05,90.123426)");
        db.execSQL(" insert into b values('121212','01','食品')");
        btn_init.setClickable(false);
    }
}
