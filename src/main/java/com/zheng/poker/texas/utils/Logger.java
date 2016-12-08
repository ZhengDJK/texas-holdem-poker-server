package com.zheng.poker.texas.utils;

/**
 * Created by zheng on 2016/12/7.
 */
public class Logger {
    private static boolean flag=true;
    public static void log(String msg){
        if(flag)
            System.out.println(msg);
    }
    public static void logImportant(String msg){
        System.out.println(msg);
    }

}
