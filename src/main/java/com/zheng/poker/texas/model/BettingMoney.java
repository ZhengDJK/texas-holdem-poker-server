package com.zheng.poker.texas.model;

/**
 * Created by zheng on 2016/11/24.
 */
public class BettingMoney {
    private BettingDecision decision;
    private int raise;
    public BettingMoney(BettingDecision decision,int raise){
        this.decision=decision;
        this.raise=raise;
    }

    public BettingDecision getDecision() {
        return decision;
    }

    public void setDecision(BettingDecision decision) {
        this.decision = decision;
    }

    public int getRaise() {
        return raise;
    }

    public void setRaise(int raise) {
        this.raise = raise;
    }
}
