package com.zheng.poker.texas;

import com.zheng.poker.texas.controller.GameHandController;
import com.zheng.poker.texas.controller.HandPowerRanker;
import com.zheng.poker.texas.controller.PokerController;
import com.zheng.poker.texas.model.gameproperties.GameProperties;

/**
 * Created by zheng on 2016/12/2.
 */
public class Server {
    public static void main(String[] args){
        GameProperties gameProperties=new GameProperties();
        HandPowerRanker handPowerRanker=new HandPowerRanker();
        GameHandController gameHandController=new GameHandController(handPowerRanker,gameProperties);
        PokerController pokerController=new PokerController(gameHandController,gameProperties);
        pokerController.play();
    }

}
