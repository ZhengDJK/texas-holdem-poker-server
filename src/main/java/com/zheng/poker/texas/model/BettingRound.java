package com.zheng.poker.texas.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zheng on 2016/11/24.
 */
public class BettingRound {
    private final Map<Player, Integer> playerBets = new HashMap<Player, Integer>();
    private final StringBuilder inquire=new StringBuilder();
    private int highestBet = 0;

    public Map<Player, Integer> getPlayerBets() {
        return playerBets;
    }

    public void applyDecision(Player player,BettingMoney bettingMoney) {
        if(player.getJetton()==0)
            return;
        switch (bettingMoney.getDecision()) {
            case CALL:
                placeBet(player, highestBet,"call");
                break;
            case RAISE:
                placeBet(player, highestBet + bettingMoney.getRaise(),"raise");
                break;
            case ALL_IN:
                placeBet(player,getBetForPlayer(player)+player.getJetton(),"all_in");
                break;
            case FOLD:
                insertInquire(player.getId()+" "+player.getJetton()+" "+player.getMoney()+" "+getBetForPlayer(player)+" fold\n");
        }

        // Don't save context information for pre flop
        // Hand strength is always 0 b/c there's no shared cards
    }

    public int getTotalBets() {
        int totalBets = 0;
        for (Integer bet : playerBets.values()) {
            totalBets += bet;
        }
        return totalBets;
    }

    public int getBetForPlayer(Player player) {
        Integer bet = playerBets.get(player);
        if (bet == null) {
            return 0;
        }
        return bet;
    }

    public void placeBet(Player player, int bet,String bettingDecision) {
        Integer playerBet = playerBets.get(player);
        if (playerBet == null) {
            playerBet=0;
        }
        if (bet > highestBet) {
            highestBet = bet;

        } else if (bet < highestBet && bet-playerBet>player.getJetton()) {
            throw new IllegalArgumentException(
                    "You can't bet less than the higher bet");
        }
        if(player.getJetton()<bet-playerBet) {
            playerBets.put(player, playerBet+player.getJetton());
            player.removeJetton(player.getJetton());
            insertInquire(player.getId()+" "+player.getJetton()+" "+player.getMoney()+" "+getBetForPlayer(player)+" all_in\n");
        }
        else {
            player.removeJetton(bet - playerBet);
            playerBets.put(player, bet);
            insertInquire(player.getId()+" "+player.getJetton()+" "+player.getMoney()+" "+getBetForPlayer(player)+" "+bettingDecision+" \n");
        }



    }

    public int getHighestBet() {
        return highestBet;
    }


    public void insertInquire(String msg){
        inquire.insert(0,msg);
    }

    public StringBuilder getInquire() {
        return inquire;
    }
}
