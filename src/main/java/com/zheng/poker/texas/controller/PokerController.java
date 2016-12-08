package com.zheng.poker.texas.controller;


import com.zheng.poker.texas.model.Game;
import com.zheng.poker.texas.model.Player;
import com.zheng.poker.texas.model.gameproperties.GameProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokerController {
    private final Game game;
    private final GameProperties gameProperties;
    private final GameHandController gameHandController;

    public PokerController(final GameHandController gameHandController, final GameProperties gameProperties) {
        this.gameHandController = gameHandController;
        this.gameProperties = gameProperties;
        game = new Game(gameProperties.getPlayers());
    }

    public void play() {
        for (int i = 0; i < gameProperties.getNumberOfHands(); i++) {
            checkJetton();
            if(gameProperties.getPlayers().size()<2)
                break;
            gameHandController.play(game);
            game.setNextDealer();
        }
        for(Player player:gameProperties.getPlayers()){
            player.sendMsg("Game Over");
        }
        for(Player player:gameProperties.getPlayers())
            System.out.println(player.toString()+" "+player.getJetton()+" "+player.getMoney());
        printFinalStats();
    }

    private void checkJetton(){
        List<Player> removePlayers=new ArrayList<Player>();
        for(Player player:gameProperties.getPlayers()){
            if(player.getJetton()<gameProperties.getBigBlind()){
                if(player.getMoney()<gameProperties.getInitialJetton())
                    removePlayers.add(player);
                else
                    player.putJetton(gameProperties.getInitialJetton());
            }
        }
        for(Player player:removePlayers){
            player.sendMsg("Game Over");
            gameProperties.getPlayers().remove(player);
        }
    }

    private void printFinalStats() {
        System.out.println("-----------------------------------------");
        System.out.println("Statistics");
        System.out.println("-----------------------------------------");
        System.out.println("Number of hands played: " + game.gameHandsCount());
        for (Player player : game.getPlayers()) {
            System.out.println(player.toString() + "(" + player.toString() + ")" + ": " + player
                    .getMoney() + "$");
        }
    }
}
