package com.zheng.poker.texas.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by zheng on 2017/1/2.
 */
public class ImagePanel extends JPanel {
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon icon=new ImageIcon("C:\\Users\\zheng\\Desktop\\扑克牌\\poker_table.jpg");
        g.drawImage(icon.getImage(),0,0,null);

    }
}
