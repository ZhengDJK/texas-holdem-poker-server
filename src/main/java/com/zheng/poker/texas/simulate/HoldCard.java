package com.zheng.poker.texas.simulate;

import com.zheng.poker.texas.model.cards.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zheng on 2017/1/3.
 */
public class HoldCard {
    private Card card1,card2;
    String type;
    public HoldCard(Card card1,Card card2){
        if(card1.getSuit()==card2.getSuit()){
            type="S";
            if(card1.getNumber().getPower()>card2.getNumber().getPower()){
                this.card1=card1;
                this.card2=card2;
            }else{
                this.card2=card1;
                this.card1=card2;
            }
        }else{
            type="U";
            if(card1.getNumber().getPower()<card2.getNumber().getPower()){
                this.card1=card1;
                this.card2=card2;
            }else{
                this.card2=card1;
                this.card1=card2;
            }
        }
    }

    public List<Card> cardList(){
        return new ArrayList<Card>(Arrays.asList(card1, card2));
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((card1 == null) ? 0 : card1.getNumber().hashCode());
        result = prime * result + ((card2 == null) ? 0 : card2.getNumber().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HoldCard other = (HoldCard) obj;
        return (card1.getNumber().equals(other.card1.getNumber())
                && card2.getNumber().equals(other.card2.getNumber())
                && type.equals(other.type));
    }

    @Override
    public String toString(){
        return type+" "+card1.toString()+" " +card2.toString();
    }

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }
}
