package com.zheng.poker.texas.ui;

import com.zheng.poker.texas.model.Game;
import com.zheng.poker.texas.model.GameHand;
import com.zheng.poker.texas.model.Player;
import com.zheng.poker.texas.model.cards.Deck;

import java.util.List;

/**
 * Created by zheng on 2017/1/6.
 */
public class GameDemo {
    private final Demo demo;
    private final Game game;
    public GameDemo(Demo demo,Game game){
        this.demo=demo;
        this.game=game;
    }

    public void start(){
        List<Player> players=game.getPlayers();
        int count=0;
        for(Player player:players){
            demo.getTable().addSeat(player,count);
        }
        List<GameHand> gameHands=game.getGameHands();

    }
}
