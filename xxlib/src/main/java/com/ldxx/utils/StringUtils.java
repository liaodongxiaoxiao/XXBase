package com.ldxx.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /**
     * <p>Discription:判断字符串是否为空</p>
     * @param str
     * @return
     * @author 辽东小小 2013-11-10
     * @update [修改人] [修改时间] [变更描述]
     */
    public static boolean isEmpty(String str) {
        if (null == str) {
            return true;
        }
        str = str.trim();
        if (str.length() > 0 && !"null".equals(str)) {
            return false;
        }
        return true;
    }

    /**
     * <p>Discription:获取完整的球号，若小于10，不足两位在左边补0</p>
     * @param num
     * @return
     * @author 辽东小小 2013-11-10
     * @update [修改人] [修改时间] [变更描述]
     */
    public static String getFullBallNum(String num) {
        if (isEmpty(num)) {
            return "";
        }
        else if (num.length() > 1) {
            return num;
        }
        else {

            int i = Integer.parseInt(num);
            if (i < 10) {
                return "0" + i;
            }
            else {
                return num;
            }
        }
    }

    /**
     * <p>Discription:判读一个数是否是正整数</p>
     * @param num
     * @return
     * @author  ldxx    2014年5月28日
     * @update [修改人] [修改时间] [变更描述]
     */
    public static boolean isZNumber(String num) {
        if (num == null) {
            return false;
        }
        Pattern p = Pattern.compile("^\\d*[1-9]\\d*$");
        Matcher m = p.matcher(num);
        return m.matches();
    }

    /**
     * <p>Discription:根据属性名，获取get/set方法名</p>
     * @param setOrGet
     * @param attr
     * @return
     * @author 王卓 2014-8-13
     * @update [修改人] [修改时间] [变更描述]
     */
    public static String getMethodNameByAttr(String setOrGet, String attr) {
        attr = attr.toLowerCase();
        StringBuffer sb = new StringBuffer(setOrGet);
        sb.append(attr.substring(0, 1).toUpperCase()).append(attr.substring(1));
        return sb.toString();
    }

}
