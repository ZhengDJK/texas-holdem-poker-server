package com.zheng.poker.texas.simulate;

import com.zheng.poker.texas.model.cards.Card;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zheng on 2016/12/15.
 */
public class GetHoldCardsStrength {
    public static List<Map<HoldCard,Double>> getHoldCardStrength() {
        List<Map<HoldCard, Double>> holdCardStrength = new ArrayList<Map<HoldCard, Double>>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("holdCardStrength.txt")));
            String data = null;
            Map<HoldCard, Double> hold = null;
            while ((data = br.readLine()) != null) {
                String[] datas = data.split(" ");
                if (datas.length == 1) {
                    hold = new HashMap<HoldCard, Double>();
                    holdCardStrength.add(hold);
                } else if (datas.length == 5) {
                    Card card1 = Card.fromString(datas[1]);
                    Card card2 = Card.fromString(datas[2]);
                    double strength = Double.parseDouble(datas[3]) / Double.parseDouble(datas[4]);
                    hold.put(new HoldCard(card1, card2), strength);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        normalization(holdCardStrength);
        return holdCardStrength;
    }

    private static void normalization(List<Map<HoldCard,Double>> mapList){
        for(Map<HoldCard,Double> map:mapList){
            double max=0,min=1;
            for(Map.Entry<HoldCard,Double> entry:map.entrySet()){
                if(max<entry.getValue())
                    max=entry.getValue();
                if(min>entry.getValue())
                    min=entry.getValue();
            }
            double weight=max-min;
            for(Map.Entry<HoldCard,Double> entry:map.entrySet()){
                map.put(entry.getKey(),(entry.getValue()-min)/weight);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<Map<HoldCard,Double>> mapList=getHoldCardStrength();
        //normalization(mapList);
        int count=2;
        for(Map<HoldCard,Double> map:mapList){
            System.out.println(count);
            for(Map.Entry<HoldCard,Double> entry:map.entrySet()){
                System.out.println(entry.getKey()+" : "+entry.getValue());
            }
            count++;
        }
    }
}
