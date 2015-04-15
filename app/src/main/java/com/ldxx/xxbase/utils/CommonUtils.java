package com.ldxx.xxbase.utils;

import java.util.Random;

/**
 * Created by WANGZHUO on 2015/4/14.
 */
public class CommonUtils {


    /**
     * 获取十六进制的颜色代码.例如  "#6E36B4" , For HTML ,
     * @return String
     */
    public static String getRandColorCode(){
        String r,g,b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(150)).toUpperCase();
        g = Integer.toHexString(random.nextInt(250)).toUpperCase();
        b = Integer.toHexString(random.nextInt(50)).toUpperCase();

        r = r.length()==1 ? "0" + r : r ;
        g = g.length()==1 ? "0" + g : g ;
        b = b.length()==1 ? "0" + b : b ;

        return "#"+r+g+b;
    }
}
