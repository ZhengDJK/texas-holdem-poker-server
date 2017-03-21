package com.zheng.poker.texas;

import com.zheng.poker.texas.controller.GameHandController;
import com.zheng.poker.texas.controller.HandPowerRanker;
import com.zheng.poker.texas.controller.PokerController;
import com.zheng.poker.texas.model.gameproperties.GameProperties;
import com.zheng.poker.texas.ui.Demo;

/**
 * Created by zheng on 2017/1/4.
 */
public class Play {
    public static void main(String[] args){
        GameProperties gameProperties=new GameProperties();
        HandPowerRanker handPowerRanker=new HandPowerRanker();
        GameHandController gameHandController=new GameHandController(handPowerRanker,gameProperties);
        PokerController pokerController=new PokerController(gameHandController,gameProperties);
        pokerController.play();
        try{
            Thread.sleep(1000);
        }catch(Exception e) {
            System.exit(0);//退出程序
        }
    }
}
