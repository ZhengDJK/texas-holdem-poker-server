package com.zheng.poker.texas.simulate;

import com.zheng.poker.texas.controller.HandPowerRanker;
import com.zheng.poker.texas.model.HandPower;
import com.zheng.poker.texas.model.cards.Card;
import com.zheng.poker.texas.model.cards.Deck;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zheng on 2016/12/14.
 */
public class SimulateHoldCard {
    private final int numberOfPlayers;
    private final int numberOfGames;
    private final Map<HoldCard,Integer> winMap=new HashMap<HoldCard, Integer>();
    private final Map<HoldCard,Integer> playMap=new HashMap<HoldCard, Integer>();
    private Deck deck;
    private HandPowerRanker handPowerRanker=new HandPowerRanker();


    public SimulateHoldCard(int numberOfGames, int numberOfPlayers){
        this.numberOfGames=numberOfGames;
        this.numberOfPlayers=numberOfPlayers;
    }

    public int getMapValue(Map<HoldCard,Integer> map,HoldCard holdCards){
        Integer value=map.get(holdCards);
        return value==null?0:value;
    }

    public void addMapValue(Map<HoldCard,Integer> map,HoldCard holdCards){
        map.put(holdCards,getMapValue(map,holdCards)+1);
    }

    private List<HoldCard> getWinners(List<Card> sharedCards ,List<HoldCard> holdCards) {
        HandPower bestHandPower = null;
        List<HoldCard> winners = new ArrayList<HoldCard>();
        for (HoldCard player : holdCards) {
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
            List<HoldCard> players=new ArrayList<HoldCard>();
            for(int j=0;j<5;j++)
                shareCards.add(deck.removeTopCard());
            for(int j=0;j<numberOfPlayers;j++){
                players.add(new HoldCard(deck.removeTopCard(),deck.removeTopCard()));
            }
            List<HoldCard> winners=getWinners(shareCards,players);
            for(HoldCard holdCards:winners){
                addMapValue(winMap,holdCards);
            }
            for(HoldCard holdCards:players){
                addMapValue(playMap,holdCards);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FileWriter out=new FileWriter("holdCardStrength.txt");
        for(int i=2;i<=8;i++) {
            out.write(i+"\n");
            System.out.println(i);
            SimulateHoldCard simulateHoldCards = new SimulateHoldCard(100000000,i);
            simulateHoldCards.play();
            for (Map.Entry<HoldCard, Integer> entry : simulateHoldCards.winMap.entrySet()) {
                out.write(entry.getKey() + " " + entry.getValue() + " " + simulateHoldCards.playMap.get(entry.getKey())+"\n");
            }
        }
        out.close();
    }
}
