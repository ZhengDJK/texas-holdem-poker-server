package com.zheng.poker.texas.model;

import com.zheng.poker.texas.model.cards.Card;
import com.zheng.poker.texas.ui.Seat;
import com.zheng.poker.texas.utils.MySocket;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zheng on 2016/11/24.
 */
public class Player {
    private int sleepTime=1000;
    private final int id;
    private final String name;
    private final MySocket socket;
    private int money;
    private int jetton=0;
    private List<Card> holeCards;
    private Seat seat;

    public Player(Socket socket,int initialMoney,String name) throws IOException {
        this.name=name;
        this.socket=new MySocket(socket);
        this.id=socket.getPort();
        this.money=initialMoney;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player)) {
            return false;
        }

        Player otherPlayer = (Player) o;

        return id == otherPlayer.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Player #");
        stringBuilder.append(getId());
        stringBuilder.append(" ");
        stringBuilder.append(name);

        if (holeCards != null) {
            stringBuilder.append(holeCards.toString());
        }

        return stringBuilder.toString();
    }

    public int getMoney() {
        return money;
    }

    public void removeMoney(int amount) {
        if(amount>money){
            money=0;
        }else {
            money -= amount;
        }
        seat.updateLabel();
    }

    public void removeJetton(int amount){
        if(amount>jetton)
            jetton=0;
        else
            jetton-=amount;
        seat.updateLabel();
    }

    public void addMoney(int amount) {
        money += amount;
        seat.updateLabel();
    }
    public void addJetton(int amount){
        jetton+=amount;
        seat.updateLabel();
    }

    public void setHoleCards(Card hole1, Card hole2) {
        seat.hideCards();
        holeCards = Arrays.asList(hole1, hole2);
        String msg="hold/\n"+hole1+"\n"+hole2+"\n/hold\n";
        seat.showCards();
        sendMsg(msg);
    }

    public int getJetton() {
        return jetton;
    }

    public void setJetton(int jetton) {
        this.jetton = jetton;
    }

    public List<Card> getHoleCards() {
        return holeCards;
    }

    public void sendMsg(String msg) {
        this.socket.sendMsg(msg);
    }

    public String getMsg() {
        return this.socket.getMsg();
    }

    public BettingMoney getBetting() throws IOException {
        String[] msg=getMsg().split(" ");
        if(msg.length==2){
            int raiseMoney=Integer.getInteger(msg[1]);
            return new BettingMoney(BettingDecision.getDecision(msg[0]),raiseMoney);
        }
        return new BettingMoney(BettingDecision.getDecision(msg[0]),0);
    }

    public void putJetton(int jetton){
        this.jetton+=jetton;
        removeMoney(jetton);
    }

    public BettingMoney decide(GameHand gameHand) {
        String msg="call";
        if(jetton>0) {
            StringBuilder inquire=new StringBuilder();
            inquire.append("inquire/\n");
            inquire.append(gameHand.getCurrentBettingRound().getInquire().toString());
            inquire.append("total pot: "+gameHand.getTotalBets()+"\n");
            inquire.append("/inquire\n");
            sendMsg(inquire.toString());
            msg = getMsg();
        }
        return bettingMoneyFromString(msg);
    }

    private BettingMoney bettingMoneyFromString(String msg){
        String[] betting=msg.split(" ");
        if(betting.length==2){
            return new BettingMoney(BettingDecision.RAISE,Integer.parseInt(betting[1]));
        }
        return new BettingMoney(BettingDecision.getDecision(msg),0);
    }

    public String getName() {
        return name;
    }


    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String display(){
        return "#"+name;
    }
}
