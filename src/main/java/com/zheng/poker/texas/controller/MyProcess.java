package com.zheng.poker.texas.controller;

import com.zheng.poker.texas.model.Game;
import com.zheng.poker.texas.model.Player;
import com.zheng.poker.texas.model.gameproperties.GameProperties;
import com.zheng.poker.texas.utils.MySocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zheng on 2016/11/25.
 */
public class MyProcess {
    private final GameProperties gameProperties=new GameProperties();
    ServerSocket sk;

    public MyProcess() throws IOException {
        sk = new ServerSocket(4001);
        System.out.println("Wait for connect......");
        start();

        Game game=new Game(gameProperties.getPlayers());
        sk.close();
    }

    public void start() throws IOException {
        getPlayer();
        System.out.println("All players are ready! Go......");
        sendMsgToAll("All players are ready! Go......");
    }

    private void getPlayer() throws IOException {
        for(int i=0;i<gameProperties.getNumberOfPlayer();i++){
            Socket socket=sk.accept();
            MySocket mySocket=new MySocket(socket);
            String name=mySocket.getMsg();
            Player player=new Player(socket,gameProperties.getInitialMoney(),name);
            gameProperties.addPlayer(player);
            System.out.println(player+" is connected!");
        }
    }

    private void sendMsgToAll(String msg) throws IOException {
        for (Player player:gameProperties.getPlayers())
            player.sendMsg(msg);
    }
}
