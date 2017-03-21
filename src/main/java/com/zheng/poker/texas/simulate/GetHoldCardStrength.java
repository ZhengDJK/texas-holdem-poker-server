package com.zheng.poker.texas.simulate;

import com.zheng.poker.texas.model.cards.Card;
import com.zheng.poker.texas.model.cards.Deck;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zheng on 2016/12/15.
 */
public class GetHoldCardStrength {
    public static List<Map<HoldCards,Double>> getHoldCardStrength() {
        List<Map<HoldCards, Double>> holdCardStrength = new ArrayList<Map<HoldCards, Double>>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("holdCardStrength.txt")));
            String data = null;
            Map<HoldCards, Double> hold = null;
            while ((data = br.readLine()) != null) {
                String[] datas = data.split(" ");
                if (datas.length == 1) {
                    hold = new HashMap<HoldCards, Double>();
                    holdCardStrength.add(hold);
                } else if (datas.length == 4) {
                    Card card1 = Card.fromString(datas[0]);
                    Card card2 = Card.fromString(datas[1]);
                    double strength = Double.parseDouble(datas[2]) / Double.parseDouble(datas[3]);
                    hold.put(new HoldCards(card1, card2), strength);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        normalization(holdCardStrength);
        return holdCardStrength;
    }

    private static void normalization(List<Map<HoldCards,Double>> mapList){
        for(Map<HoldCards,Double> map:mapList){
            double max=0,min=1;
            for(Map.Entry<HoldCards,Double> entry:map.entrySet()){
                if(max<entry.getValue())
                    max=entry.getValue();
                if(min>entry.getValue())
                    min=entry.getValue();
            }
            double weight=max-min;
            for(Map.Entry<HoldCards,Double> entry:map.entrySet()){
                map.put(entry.getKey(),(entry.getValue()-min)/weight);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<Map<HoldCards,Double>> mapList=getHoldCardStrength();
        //normalization(mapList);
        int count=2;
        for(Map<HoldCards,Double> map:mapList){
            System.out.println(count);
            for(Map.Entry<HoldCards,Double> entry:map.entrySet()){
                System.out.println(entry.getKey()+" : "+entry.getValue());
            }
            count++;
        }
    }
}
