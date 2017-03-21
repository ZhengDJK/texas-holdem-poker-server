package com.zheng.poker.texas.simulate;

import com.zheng.poker.texas.model.cards.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zheng on 2016/12/14.
 */
public class HoldCards {
    private Card card1;
    private Card card2;

    public  HoldCards(List<Card> holdCards){
        card1=holdCards.get(0);
        card2=holdCards.get(1);
    }

    public HoldCards(Card card1,Card card2){
        this.card1=card1;
        this.card2=card2;
    }


    public List<Card> cardList(){
        return new ArrayList<Card>(Arrays.asList(card1,card2));
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        if(card1.hashCode()<card2.hashCode()) {
            result = prime * result + ((card1 == null) ? 0 : card1.hashCode());
            result = prime * result + ((card2 == null) ? 0 : card2.hashCode());
        }else{
            result = prime * result + ((card2 == null) ? 0 : card2.hashCode());
            result = prime * result + ((card1 == null) ? 0 : card1.hashCode());
        }
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
        HoldCards other = (HoldCards) obj;
        return (card1.equals(other.card1) && card2.equals(other.card2))
                || (card1.equals(other.card2) && card2.equals(other.card1));
    }
    
    @Override
    public String toString(){
        return card1.toString()+" " +card2.toString();
    }
}
