package com.zheng.poker.texas.simulate;

import com.zheng.poker.texas.controller.HandPowerRanker;
import com.zheng.poker.texas.model.HandPower;
import com.zheng.poker.texas.model.cards.Card;
import com.zheng.poker.texas.model.cards.Deck;
import com.zheng.poker.texas.utils.Logger;

import java.io.*;
import java.util.*;

/**
 * Created by zheng on 2016/12/14.
 */
public class SimulateHoldCards {
    private final int numberOfPlayers;
    private final int numberOfGames;
    private final Map<HoldCards,Integer> winMap=new HashMap<HoldCards, Integer>();
    private final Map<HoldCards,Integer> playMap=new HashMap<HoldCards, Integer>();
    private Deck deck;
    private HandPowerRanker handPowerRanker=new HandPowerRanker();


    public SimulateHoldCards(int numberOfGames,int numberOfPlayers){
        this.numberOfGames=numberOfGames;
        this.numberOfPlayers=numberOfPlayers;
    }

    public int getMapValue(Map<HoldCards,Integer> map,HoldCards holdCards){
        Integer value=map.get(holdCards);
        return value==null?0:value;
    }

    public void addMapValue(Map<HoldCards,Integer> map,HoldCards holdCards){
        map.put(holdCards,getMapValue(map,holdCards)+1);
    }

    private List<HoldCards> getWinners(List<Card> sharedCards ,List<HoldCards> holdCards) {
        HandPower bestHandPower = null;
        List<HoldCards> winners = new ArrayList<HoldCards>();
        for (HoldCards player : holdCards) {
            List<Card> mergeCards = player.cardList();
            mergeCards.addAll(sharedCards);
            HandPower handPower = handPowerRanker.rank(mergeCards);

            //logger.log(player + ": " + handPower);

            if (bestHandPower == null || handPower.compareTo(bestHandPower) > 0) {
                winners.clear();
                winners.add(player);
                bestHandPower = handPower;
            } else if (handPower.equals(bestHandPower)) {
                winners.add(player);
            }
        }
        // statisticsController.storeWinners(winners);
        return winners;
    }

    public void play(){
        for(int i=0;i<numberOfGames;i++){
            deck=new Deck();
            List<Card> shareCards=new ArrayList<Card>();
            List<HoldCards> players=new ArrayList<HoldCards>();
            for(int j=0;j<5;j++)
                shareCards.add(deck.removeTopCard());
            for(int j=0;j<numberOfPlayers;j++){
                players.add(new HoldCards(deck.removeTopCard(),deck.removeTopCard()));
            }
            List<HoldCards> winners=getWinners(shareCards,players);
            for(HoldCards holdCards:winners){
                addMapValue(winMap,holdCards);
            }
            for(HoldCards holdCards:players){
                addMapValue(playMap,holdCards);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FileWriter out=new FileWriter("holdCardStrength.txt");
        for(int i=2;i<=8;i++) {
            out.write(i+"\n");
            System.out.println(i);
            SimulateHoldCards simulateHoldCards = new SimulateHoldCards(100000000,i);
            simulateHoldCards.play();
            for (Map.Entry<HoldCards, Integer> entry : simulateHoldCards.winMap.entrySet()) {
                out.write(entry.getKey() + " " + entry.getValue() + " " + simulateHoldCards.playMap.get(entry.getKey())+"\n");
            }
        }
        out.close();
    }
}
