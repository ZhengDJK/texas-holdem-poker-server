package com.zheng.poker.texas.model;

import java.util.*;

/**
 * Created by zheng on 2016/12/6.
 */
public class Pot {
    private List<Player> players=new ArrayList<Player>();
    private int money=0;
    private int pre;

    public Pot(int pre){
        this.pre=pre;
    }

    public String toString(){
        return players.toString()+" : "+money;
    }

    public int getMoney() {
        return money;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public static List<Pot> getAllPot(Map<Player,Integer> playerBets){
        List<Pot> pots=new ArrayList<Pot>();
        playerBets=sortMapByValue(playerBets);
        int count=0;
        for(Map.Entry<Player,Integer> entry:playerBets.entrySet()){
            if(entry.getValue()-count>0){
                Pot pot=new Pot(entry.getValue()-count);
                pots.add(pot);
                count=entry.getValue();
            }
            for(Pot pot:pots){
                pot.addPlayer(entry.getKey());
                pot.money+=pot.pre;
            }
        }
        return pots;
    }

    private static Map<Player, Integer> sortMapByValue(Map<Player, Integer> oriMap) {
        Map<Player, Integer> sortedMap = new LinkedHashMap<Player, Integer>();
        if (oriMap != null && !oriMap.isEmpty()) {
            List<Map.Entry<Player, Integer>> entryList = new ArrayList<Map.Entry<Player, Integer>>(oriMap.entrySet());
            Collections.sort(entryList,
                    new Comparator<Map.Entry<Player, Integer>>() {
                        public int compare(Map.Entry<Player, Integer> entry1,
                                           Map.Entry<Player, Integer> entry2) {
                            return entry1.getValue() - entry2.getValue();
                        }
                    });
            Iterator<Map.Entry<Player, Integer>> iter = entryList.iterator();
            Map.Entry<Player, Integer> tmpEntry = null;
            while (iter.hasNext()) {
                tmpEntry = iter.next();
                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
            }
        }
        return sortedMap;
    }
}
