package com.zheng.poker.texas.model.cards;

public enum CardSuit {
    SPADE("\u2660"),
    HEART("\u2665"),
    CLUB("\u2663"),
    DIAMOND("\u2666");

    private final String symbol;

    private CardSuit(String symbol) {
        this.symbol = symbol;
    }

    public static CardSuit fromString(String symbol){
        if(symbol.equals("♠")) return SPADE;
        else if(symbol.equals("♥")) return HEART;
        else if(symbol.equals("♣")) return CLUB;
        else return DIAMOND;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
