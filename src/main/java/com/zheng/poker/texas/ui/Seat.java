package com.zheng.poker.texas.ui;

import com.zheng.poker.texas.model.Player;
import com.zheng.poker.texas.utils.Sleep;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zheng on 2017/1/2.
 */
public class Seat {
    private final JPanel mainPanel;
    private Player player;
    private int number;
    private final int cardWeight=100+20;
    private final int[][] cardLocation={{150,450},{450,180},{850,180},{1250,180},{1550,450},{1250,720},{850,720},{450,720}};
    private final int[][] nameLocation={{150,610},{450,130},{850,130},{1250,130},{1550,610},{1250,880},{850,880},{450,880}};
    private final int[][] actionLocation={{390,510},{500,340},{900,340},{1300,340},{1420,510},{1300,670},{900,670},{500,670}};

    private List<CardImage> cardImages;
    private JLabel nameLabel=new JLabel();
    private JLabel moneyLabel=new JLabel();
    private JLabel jettonLabel=new JLabel();
    private JLabel actionLabel=new JLabel();



    public Seat(Player player,int number,JPanel panel){
        this.mainPanel=panel;
        this.player=player;
        this.number=number;

        nameLabel.setText(player.display());
        nameLabel.setFont(new Font(null, Font.BOLD, 24));
        nameLabel.setBounds(nameLocation[number][0], nameLocation[number][1], 100, 30);

        moneyLabel.setText("" + player.getMoney());
        moneyLabel.setFont(new Font(null,Font.ITALIC,18));
        moneyLabel.setBounds(nameLocation[number][0]+120, nameLocation[number][1], 80, 30);

        jettonLabel.setText(""+player.getJetton());
        jettonLabel.setFont(new Font(null,Font.ITALIC,18));
        jettonLabel.setBounds(nameLocation[number][0]+180, nameLocation[number][1], 80, 30);

        actionLabel.setFont(new Font(null,Font.BOLD,24));
        actionLabel.setBounds(actionLocation[number][0],actionLocation[number][1],200,30);

        mainPanel.add(nameLabel);
        mainPanel.add(moneyLabel);
        mainPanel.add(jettonLabel);
        mainPanel.add(actionLabel);
    }

    public void showCards(){
        cardImages= Arrays.asList(new CardImage(player.getHoleCards().get(0)),
                new CardImage(player.getHoleCards().get(1)));
        suitSeat();
        mainPanel.repaint();
    }

    public void hideCards(){
        if(cardImages!=null && cardImages.size()==2) {
            cardImages.get(0).setVisible(false);
            cardImages.get(1).setVisible(false);
            mainPanel.remove(cardImages.get(0));
            mainPanel.remove(cardImages.get(1));
            //cardImages.clear();
            mainPanel.repaint();
        }
    }

    private void suitSeat(){
        cardImages.get(0).setLocation(cardLocation[number][0],cardLocation[number][1]);
        cardImages.get(1).setLocation(cardLocation[number][0]+cardWeight,cardLocation[number][1]);
        mainPanel.add(cardImages.get(0));
        mainPanel.add(cardImages.get(1));
        //cardImages.get(1).setLocation(400,400);
    }

    public void updateLabel(){
        moneyLabel.setText(""+player.getMoney());
        //System.out.println(player.getMoney());
        jettonLabel.setText(""+player.getJetton());
        //moneyLabel.repaint();
        //jettonLabel.repaint();
        mainPanel.repaint();
        Sleep.sleep(100);
    }

    public void showAction(String action){
        actionLabel.setText(action);
        actionLabel.setVisible(true);
        mainPanel.repaint();
    }

    public void hideAction(){
        actionLabel.setVisible(false);
        mainPanel.repaint();
    }

    public void clear(){
        mainPanel.remove(nameLabel);
        mainPanel.remove(moneyLabel);
        mainPanel.remove(jettonLabel);
        mainPanel.remove(actionLabel);
        hideCards();
        mainPanel.repaint();
    }

    public Player getPlayer() {
        return player;
    }

    public void ShowBackCard(){
        cardImages.get(0).setCardNull();
        cardImages.get(1).setCardNull();
        mainPanel.repaint();
        Sleep.sleep();
    }
}
