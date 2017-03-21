package com.zheng.poker.texas.ui;

import com.zheng.poker.texas.model.Player;
import com.zheng.poker.texas.model.cards.Card;
import com.zheng.poker.texas.utils.Sleep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zheng on 2017/1/3.
 */
public class PokerTable {
    private final JPanel mainPanel;
    private final int[] cardLocation={670,450};
    private final int[] potLocation={900,380};
    private final int cardWeight=100+20;
    private List<JPanel> cards=new ArrayList<JPanel>();
    private List<Seat> seats=new ArrayList<Seat>();
    private JLabel potLabel=new JLabel();
    private JButton speedUp=new JButton(">>");
    private JButton speedDown=new JButton("<<");
    private JLabel speed=new JLabel("X 1");

    public PokerTable(JPanel panel){
        panel.setLayout(null);
        this.mainPanel=panel;
        potLabel.setText("Pot:0");
        potLabel.setFont(new Font(null, Font.BOLD, 38));
        potLabel.setBounds(potLocation[0], 380, 500, 50);
        speed.setFont(new Font(null, Font.ITALIC, 24));
        speed.setBounds(1080, 950, 70, 30);
        speedDown.setBounds(900,940,70,40);
        speedUp.setBounds(990,940,70,40);
        speedDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sleep.speedDown();
                speed.setText("X " + Sleep.getDivisor());
            }
        });
        speedUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sleep.speedUp();
                speed.setText("X "+Sleep.getDivisor());
            }
        });
        mainPanel.add(speed);
        mainPanel.add(speedDown);
        mainPanel.add(speedUp);
        mainPanel.add(potLabel);
    }

    public void addCard(JPanel card){
        card.setLocation(cardWeight*cards.size()+cardLocation[0],cardLocation[1]);
        mainPanel.add(card);
        cards.add(card);
        mainPanel.repaint();
        Sleep.sleep(100);
    }

    public void removeAllCards(){
        for(JPanel card:cards) {
            card.setVisible(false);
            mainPanel.remove(card);
        }
        cards.clear();
        mainPanel.repaint();
    }

    public void updatePot(int pot){
        this.potLabel.setText("Pot:"+pot);
        mainPanel.repaint();
        Sleep.sleep();
    }

    public void addSeat(Seat seat){
        seats.add(seat);
    }

    public Seat addSeat(Player player,int number){
        Seat seat=new Seat(player,number,mainPanel);
        seats.add(seat);
        return seat;
    }

    public void removeSeat(Seat seat){
        seat.clear();
        seats.remove(seat);
        Sleep.sleep(200);
    }

    public Seat getSeatByPlayer(Player player){
        Seat searchSeat=null;
        for(Seat seat:seats){
            if(seat.getPlayer()==player){
                searchSeat=seat;
                break;
            }
        }
        return searchSeat;
    }

    public void clear(){
        removeAllCards();
        potLabel.setText("Pot:0");
        for(Seat seat:seats){
            seat.hideCards();
            seat.hideAction();
        }
        mainPanel.repaint();
        Sleep.sleep();
    }

    public void showWinner(Player player,List<Card> cards,int pot){
        removeAllCards();
        cardLocation[0]-=cardWeight;
        potLabel.setText("Winner: "+player.display()+" "+pot);
        for(Card card:cards){
            addCard(new CardImage(card));
        }
        for(Card card:player.getHoleCards()){
            addCard(new CardImage(card));
        }
        cardLocation[0]+=cardWeight;
        Sleep.sleep();
        Sleep.sleep();
        Sleep.sleep();
    }
}
