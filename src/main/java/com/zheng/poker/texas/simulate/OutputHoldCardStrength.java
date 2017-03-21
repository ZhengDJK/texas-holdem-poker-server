package com.zheng.poker.texas.simulate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zheng on 2017/1/4.
 */
public class OutputHoldCardStrength {
    public static void main(String[] args) throws IOException {
        List<Map<HoldCard,Double>> maps=GetHoldCardsStrength.getHoldCardStrength();
        int count=2;
        for(Map<HoldCard,Double> map:maps){
            FileWriter out=new FileWriter("outStrength"+count+".txt");
            for(Map.Entry<HoldCard,Double> entry:map.entrySet()){
                HoldCard holdCard= entry.getKey();
                out.write(holdCard.getCard1().getNumber().getPower()+" "+holdCard.getCard2().getNumber().getPower()+" "+entry.getValue()+"\n");
            }
            out.close();
            count++;
        }
    }
}
