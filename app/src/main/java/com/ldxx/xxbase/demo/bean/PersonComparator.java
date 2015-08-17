package com.ldxx.xxbase.demo.bean;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * 按照Person的username的拼音首字母排序
 *
 * Created by wangzhuo on 2015/7/15.
 */
public class PersonComparator implements Comparator<Person> {
    private Collator collator = Collator.getInstance(Locale.CHINA);

    @Override
    public int compare(Person lhs, Person rhs) {
        //把字符串转换为一系列比特，它们可以以比特形式与 CollationKeys 相比较
        CollationKey key1 = collator.getCollationKey(lhs.getUserName());//要想不区分大小写进行比较用o1.toString().toLowerCase()
        CollationKey key2 = collator.getCollationKey(rhs.getUserName());
        //返回的分别为1,0,-1 分别代表大于，等于，小于。要想按照字母降序排序的话 加个“-”号
        return key1.compareTo(key2);
    }

    @Override
    public boolean equals(Object object) {
        return false;
    }
}
