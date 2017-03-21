package com.zheng.poker.texas.utils;

/**
 * Created by zheng on 2017/1/4.
 */
public class Sleep {
    private static int divisor=1;
    public static void sleep(long millis){
        if(millis>1000)
            millis=1000;
        try {
            Thread.sleep(millis/divisor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void sleep(){
        try {
            Thread.sleep(1000/divisor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void speedUp(){
        if(divisor<=500)
            divisor*=2;
    }

    public static void speedDown(){
        if (divisor>1)
            divisor=divisor/2;
    }

    public static int getDivisor(){
        return divisor;
    }

}
