package com.ldxx.xxbase.demo.bean;

/**
 * Created by wangzhuo on 2015/7/15.
 */
public class Person {
    private String userName;
    private String tel;

    public Person(String userName, String tel) {
        this.tel = tel;
        this.userName = userName;
    }

    public Person() {
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
