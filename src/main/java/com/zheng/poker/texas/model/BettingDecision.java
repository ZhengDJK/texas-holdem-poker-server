package com.zheng.poker.texas.model;

/**
 * Created by zheng on 2016/11/24.
 */
public enum BettingDecision {
    CALL, FOLD, RAISE,ALL_IN;
    public static BettingDecision getDecision(String msg){
        if(msg.equalsIgnoreCase("call")) return CALL;
        else if(msg.equalsIgnoreCase("raise")) return RAISE;
        else  if(msg.equalsIgnoreCase("all_in")) return ALL_IN;
        else return FOLD;
    }
}
