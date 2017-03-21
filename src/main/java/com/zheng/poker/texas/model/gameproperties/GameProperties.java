package com.zheng.poker.texas.model.gameproperties;

import com.zheng.poker.texas.model.Player;
import com.zheng.poker.texas.model.cards.Deck;
import com.zheng.poker.texas.ui.Demo;
import com.zheng.poker.texas.ui.Seat;
import com.zheng.poker.texas.utils.MySocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public  class GameProperties {
    private final int smallBlind;
    private final int bigBlind;
    private final int initialMoney;
    private final int initialJetton;
    private final int numberOfHands;
    private final int numberOfPlayer;
    private final List<Player> players = new ArrayList<Player>();
    private final Demo demo=new Demo();

    public GameProperties(){
        smallBlind=100;
        bigBlind=200;
        initialMoney=10000;
        numberOfHands=600;
        initialJetton=2000;
        numberOfPlayer=8;

        try {
            register();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendPlayersMsg();
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public int getInitialMoney() {
        return initialMoney;
    }

    public int getNumberOfHands() {
        return numberOfHands;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public int getInitialJetton() {
        return initialJetton;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    private void register() throws IOException {
        ServerSocket sk=new ServerSocket(8888);
        for(int i=0;i<getNumberOfPlayer();i++){
            Socket socket=sk.accept();
            MySocket mySocket=new MySocket(socket);
            String name=mySocket.getMsg();
            Player player=new Player(socket,getInitialMoney(),name);
            if(getNumberOfPlayer()==2){
                if(i==0)
                    player.setSeat(demo.getTable().addSeat(player,2));
                else
                    player.setSeat(demo.getTable().addSeat(player,6));
            }
            else
                player.setSeat(demo.getTable().addSeat(player,i));
            addPlayer(player);
            //mySocket.sendMsg(""+player.getId());
            System.out.println(player+" is connected!");
        }
    }

    private void sendPlayersMsg(){
        StringBuilder playerString=new StringBuilder();
        playerString.append("players/\n");
        for(Player player:players){
            playerString.append(player.getId()+" "
                    +player.getName()+" "
                    +player.getMoney()
                    +" "+player.getJetton()+"\n");
        }
        playerString.append("/players");
        for(Player player:players)
            player.sendMsg(playerString.toString());
    }

    public Demo getDemo() {
        return demo;
    }
}
