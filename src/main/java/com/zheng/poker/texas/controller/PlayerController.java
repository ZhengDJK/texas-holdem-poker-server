package com.zheng.poker.texas.controller;

import com.zheng.poker.texas.model.*;
import com.zheng.poker.texas.model.gameproperties.GameProperties;
import com.zheng.poker.texas.utils.MySocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zheng on 2016/11/30.
 */
public class PlayerController {
    public BettingMoney decide(Player player, GameHand gameHand) {
        return new BettingMoney(BettingDecision.CALL,0);
    }

    protected boolean canCheck(GameHand gameHand, Player player) {
        BettingRound bettingRound = gameHand.getCurrentBettingRound();
        return bettingRound.getHighestBet() == bettingRound.getBetForPlayer(player);
    }

    public void register(GameProperties gameProperties) throws IOException {
        ServerSocket serverSocket=new ServerSocket(4001);
        for(int i=0;i<gameProperties.getNumberOfPlayer();i++){
            Socket socket=serverSocket.accept();
            MySocket mySocket=new MySocket(socket);
            String name=mySocket.getMsg();
            Player player=new Player(socket,gameProperties.getInitialMoney(),name);
            gameProperties.addPlayer(player);
            System.out.println(player+" is connected!");
        }
    }

}
