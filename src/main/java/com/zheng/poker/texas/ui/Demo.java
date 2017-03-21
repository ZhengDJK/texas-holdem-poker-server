package com.zheng.poker.texas.ui;

import javax.swing.*;

/**
 * Created by zheng on 2017/1/3.
 */
public class Demo extends JFrame {
    private final JPanel panel=new ImagePanel();
    private final PokerTable table=new PokerTable(panel);

    public Demo(){
        getContentPane().add(panel);
        setTitle("TEXAS HOLDEM");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JPanel getPanel() {
        return panel;
    }

    public PokerTable getTable() {
        return table;
    }
}
