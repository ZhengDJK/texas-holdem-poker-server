package com.zheng.poker.texas.model;

import com.zheng.poker.texas.model.cards.Card;
import com.zheng.poker.texas.model.cards.Deck;
import com.zheng.poker.texas.utils.MapList;

import java.io.IOException;
import java.util.*;

public class GameHand {
    private final Deque<Player> players;
    private final Deck deck;
    private final List<Card> sharedCards = new ArrayList<Card>();
    private final List<BettingRound> bettingRounds = new ArrayList<BettingRound>();
    private Boolean hasRemoved = true;

    public GameHand(List<Player> players) {
        this.players = new LinkedList<Player>(players);
        deck = new Deck();
    }

    public void nextRound() {
        bettingRounds.add(new BettingRound());

        if (getBettingRoundName().equals(BettingRoundName.PRE_FLOP)) {
            dealHoleCards();
        } else if (getBettingRoundName().equals(BettingRoundName.POST_FLOP)) {
            dealFlopCards();
        } else {
            dealSharedCard();
        }
    }

    public Player getNextPlayer() {
        if (!hasRemoved) {
            Player player = players.removeFirst();
            players.addLast(player);
        }
        hasRemoved = false;
        return getCurrentPlayer();
    }

    public int getTotalBets() {
        int totalBets = 0;
        for (BettingRound bettingRound : bettingRounds) {
            totalBets += bettingRound.getTotalBets();
        }
        return totalBets;
    }

    public BettingRoundName getBettingRoundName() {
        return BettingRoundName.fromRoundNumber(bettingRounds.size());
    }

    public Player getCurrentPlayer() {
        return players.getFirst();
    }

    public List<Card> getSharedCards() {
        return sharedCards;
    }

    public int getPlayersCount() {
        return players.size();
    }

    public BettingRound getCurrentBettingRound() {
        return bettingRounds.get(bettingRounds.size() - 1);
    }

    public List<BettingRound> getBettingRounds() {
        return bettingRounds;
    }

    public void removeCurrentPlayer() {
        players.removeFirst();
        hasRemoved = true;
    }

    protected void dealHoleCards() {
        for (Player player : players) {
            Card hole1 = deck.removeTopCard();
            Card hole2 = deck.removeTopCard();

            player.setHoleCards(hole1, hole2);
        }
    }

    private void dealFlopCards() {
        sharedCards.add(deck.removeTopCard());
        sharedCards.add(deck.removeTopCard());
        sharedCards.add(deck.removeTopCard());
        StringBuilder msg=new StringBuilder();
        msg.append("flop/\n");
        for(Card card:sharedCards)
            msg.append(card+"\n");
        msg.append("/flop\n");
        for(Player player:players){
            player.sendMsg(msg.toString());
        }
    }

    private void dealSharedCard() {
        Card card=deck.removeTopCard();
        sharedCards.add(card);
        StringBuilder msg=new StringBuilder();
        BettingRoundName bettingRoundName=getBettingRoundName();
        msg.append(bettingRoundName+"/\n");
        msg.append(card+"\n");
        msg.append("/"+bettingRoundName+"\n");
        for(Player player:players){
           player.sendMsg(msg.toString());
        }
    }

    public Deque<Player> getPlayers() {
        return this.players;
    }


    protected Deck getDeck() {
        return deck;
    }

    public void sendSeatInfoMsg(){
        StringBuilder msg=new StringBuilder();
        msg.append("seat/\n");
        int count=0;
        for(Player player:players){
            if(count==0)
                msg.append("small blind: ");
            else if(count==1)
                msg.append("big blind: ");
            msg.append(player.getId()+" "+player.getJetton()+" "+player.getMoney()+"\n");
            count++;
        }
        msg.append("/seat\n");
        for(Player player:players){
            player.sendMsg(msg.toString());
        }
    }

    public void applyDecision(Player player, BettingMoney bettingMoney) {
        BettingRound currentBettingRound = getCurrentBettingRound();

        currentBettingRound.applyDecision(player,bettingMoney);

        if (bettingMoney.getDecision().equals(BettingDecision.FOLD)) {
            removeCurrentPlayer();
        }
    }

    public Map<Player,Integer> getAllPlayerBet(){
        Map<Player,Integer> bets=new HashMap<Player, Integer>();
        for(BettingRound round:bettingRounds){
            for(Map.Entry<Player,Integer> entry:round.getPlayerBets().entrySet()){
                if(bets.get(entry.getKey())==null)
                    bets.put(entry.getKey(),0);
                bets.put(entry.getKey(),bets.get(entry.getKey())+round.getBetForPlayer(entry.getKey()));
            }
        }
        return bets;
    }

}
