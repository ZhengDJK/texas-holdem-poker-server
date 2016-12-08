package com.zheng.poker.texas;

import java.util.*;

/**
 * Created by zheng on 2016/12/6.
 */
public class TestMap {
    public static void main(String[] args){
        Map<String,Integer> names=new HashMap<String, Integer>();
        names.put("dj",20);
        names.put("zheng",2);
        names.put("jd",12);
        names.put("dj",10);
        //names=sortMapByValue(names);
        for(Map.Entry<String,Integer> entry:names.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        List<String> l1=new ArrayList<String>();
        List<String> l2=new ArrayList<String>();
        l1.add("1");
        l1.add("2");
        l2.add("1");
        l2.add("3");
        l1.retainAll(l2);
        System.out.println(l1);
    }
    public static Map<String, Integer> sortMapByValue(Map<String, Integer> oriMap) {
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        if (oriMap != null && !oriMap.isEmpty()) {
            List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(oriMap.entrySet());
            Collections.sort(entryList,
                    new Comparator<Map.Entry<String, Integer>>() {
                        public int compare(Map.Entry<String, Integer> entry1,
                                           Map.Entry<String, Integer> entry2) {
                            return entry1.getValue()-entry2.getValue();
                        }
                    });
            Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
            Map.Entry<String, Integer> tmpEntry = null;
            while (iter.hasNext()) {
                tmpEntry = iter.next();
                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
            }
        }
        return sortedMap;
    }
}
