package com.xqh.study.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapTest {
   public static void main(String[] args) {
	   
	   Map<String, Object> map=new HashMap<String, Object>();
//	   map.put("test", "123");
//	   System.out.println(map.putIfAbsent("test1", "1234567"));
//	   System.out.println(map.get("test1"));
       
	   List<String> list=new ArrayList<String>();
	   list.add("1");
	   list.add("2");
	   list.add("3");
	   
	   for(String str:list){
		   if(str.equals("2")){
			   list.remove(str);
		   }
	   }
	   System.out.println();
//	   list.iterator();
	   
	   Map<String, Object> table=new Hashtable<String, Object>();
	   
	   Map<String, Object> con=new ConcurrentHashMap<String, Object>();
   }
}
