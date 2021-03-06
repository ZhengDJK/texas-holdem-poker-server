package com.zheng.poker.texas.model;

import com.zheng.poker.texas.model.cards.CardNumber;

import java.util.List;

public class HandPower implements Comparable<HandPower> {
    private final HandPowerType handPowerType;
    private final List<CardNumber> tieBreakingInformation;

    public HandPower(final HandPowerType handPowerType,
            final List<CardNumber> tieBreakingInformation) {
        this.handPowerType = handPowerType;
        this.tieBreakingInformation = tieBreakingInformation;
    }

    public boolean equals(Object other){
        return this.compareTo((HandPower) other)==0;
    }

    public int hashCode(){
        int result=1;
        result=31*result+handPowerType.getPower();
        result=31*result+tieBreakingInformation.hashCode();
        return result;
    }

    public int compareTo(HandPower other) {
        int typeDifference = handPowerType.getPower()
                - other.handPowerType.getPower();
        if (typeDifference == 0) {
            for (int i = 0; i < tieBreakingInformation.size(); i++) {
                int tieDifference = tieBreakingInformation.get(i).getPower()
                        - other.tieBreakingInformation.get(i).getPower();
                if (tieDifference != 0) {
                    return tieDifference;
                }
            }
            return 0;
        }

        return typeDifference;
    }

    @Override
    public String toString() {
        return handPowerType.toString() + " "
                + tieBreakingInformation.toString();
    }

    public HandPowerType getHandPowerType() {
        return handPowerType;
    }

    public List<CardNumber> getTieBreakingInformation() {
        return tieBreakingInformation;
    }
}
