package com.zheng.poker.texas;

import com.zheng.poker.texas.controller.*;
import com.zheng.poker.texas.model.gameproperties.GameProperties;

import java.io.IOException;

/**
 * Created by zheng on 2016/11/25.
 */
public class ProcessTest {
    public static void main(String[] args) throws IOException {
        //MyProcess process=new MyProcess();
        GameProperties gameProperties=new GameProperties();
        PlayerController playerController=new PlayerController();
        playerController.register(gameProperties);
        GameHandController gameHandController=new GameHandController(new HandPowerRanker(),gameProperties);
        PokerController pokerController=new PokerController(gameHandController,gameProperties);
    }
}
