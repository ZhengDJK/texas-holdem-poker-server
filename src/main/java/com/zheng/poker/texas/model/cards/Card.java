package com.zheng.poker.texas.model.cards;


public class Card implements Comparable<Card> {
    private final CardSuit suit;
    private final CardNumber number;

    public Card(final CardSuit suit, final CardNumber number) {
        this.suit = suit;
        this.number = number;
    }

    public static Card fromString(String string){
        return new Card(CardSuit.fromString(string.substring(0,1)),
                CardNumber.fromString(string.substring(1)));
    }

    @Override
    public String toString() {
        return suit.toString() + number.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false;
        }

        Card other = (Card) obj;

        return suit.equals(other.suit) && number.equals(other.number);
    }

    @Override
    public int hashCode(){
        return number.getPower()*4+suit.ordinal();
    }

    
    public int compareTo(Card card) {
        return number.getPower() - card.number.getPower();
    }

    public CardSuit getSuit() {
        return suit;
    }

    public CardNumber getNumber() {
        return number;
    }
}
