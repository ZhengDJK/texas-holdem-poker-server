package com.zheng.poker.texas.ui;

import com.zheng.poker.texas.model.cards.Card;

import javax.swing.*;
import java.awt.*;

/**
 * Created by zheng on 2017/1/2.
 */
public class CardImage extends JPanel {
    private final String filePath="C:\\Users\\zheng\\Desktop\\扑克牌\\";
    private Card card;
   /* public static Image getImage(String cardName){
        return new ImageIcon(filePath+cardName).getImage();
    }*/

    public CardImage(Card card){
        this.card=card;
        this.setSize(100,150);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon icon;
        if(card==null)
            icon=new ImageIcon(filePath+"back.jpg");
        else
            icon=new ImageIcon(filePath+card.toString()+".jpg");
        //System.out.println(icon.getIconWidth());
        g.drawImage(icon.getImage(),0,0,null);

    }

    public void setCardNull(){
        card=null;
    }

}
