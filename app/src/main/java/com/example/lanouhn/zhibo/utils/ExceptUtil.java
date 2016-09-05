package com.example.lanouhn.zhibo.utils;

import java.math.BigDecimal;

/**
 * 以万为单位，保留一位小数
 * Created by lanouhn on 2016/8/17.
 */
public class ExceptUtil {

    public static String getDate(long value) {
        //小数处理

        if (value < 10000) {
            return String.valueOf(value);    //数字小于1万
        } else {    //数字大于1万
            double except = (double) value / 10000;
            BigDecimal big = new BigDecimal(except);
            double result = big.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();    //保留一位小数
            String str = result + "万";
            return str;
        }
    }
}
