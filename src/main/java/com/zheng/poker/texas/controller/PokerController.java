package com.zheng.poker.texas.controller;


import com.zheng.poker.texas.model.Game;
import com.zheng.poker.texas.model.Player;
import com.zheng.poker.texas.model.gameproperties.GameProperties;
import com.zheng.poker.texas.ui.Demo;
import com.zheng.poker.texas.ui.GameDemo;
import com.zheng.poker.texas.ui.Seat;
import com.zheng.poker.texas.utils.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokerController {
    private final Game game;
    private final GameProperties gameProperties;
    private final GameHandController gameHandController;
    private final Demo demo;

    public PokerController(final GameHandController gameHandController, final GameProperties gameProperties) {
        this.gameHandController = gameHandController;
        this.gameProperties = gameProperties;
        game = new Game(gameProperties.getPlayers());
        this.demo=gameProperties.getDemo();
    }

    public void play() {
        for (int i = 0; i < gameProperties.getNumberOfHands(); i++) {
            checkJetton();
            if(game.getPlayers().size()<2)
                break;
            gameHandController.play(game);
            demo.getTable().clear();
            game.setNextDealer();
        }
        for(Player player:game.getPlayers()){
            Logger.log(player.toString()+"----------");
            player.sendMsg("Game Over");
        }
        for(Player player:gameProperties.getPlayers())
            System.out.println(player.toString()+" "+player.getJetton()+" "+player.getMoney());
        printFinalStats();
        /*Demo d=new Demo();
        GameDemo gameDemo=new GameDemo(d,game);
        gameDemo.start();*/
    }

    private void checkJetton(){
        List<Player> removePlayers=new ArrayList<Player>();
        for(Player player:game.getPlayers()){
            if(player.getJetton()<gameProperties.getBigBlind()){
                if(player.getMoney()<gameProperties.getInitialJetton())
                    removePlayers.add(player);
                else {
                    player.putJetton(gameProperties.getInitialJetton());
                    Seat seat = demo.getTable().getSeatByPlayer(player);
                    seat.updateLabel();
                }
            }
        }
        for(Player player:removePlayers){
            Logger.log(player+"-----------------");
            player.sendMsg("Game Over");
            game.getPlayers().remove(player);
            demo.getTable().removeSeat(player.getSeat());
        }
    }

    private void printFinalStats() {
        System.out.println("-----------------------------------------");
        System.out.println("Statistics");
        System.out.println("-----------------------------------------");
        System.out.println("Number of hands played: " + game.gameHandsCount());
        for (Player player : gameProperties.getPlayers()) {
            System.out.println(player.toString() + "(" + player.toString() + ")" + ": " + player
                    .getMoney() + "$");
        }
    }
}
