package com.xqh.iterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IteratorTest {
	public static void main(String[] args) {
//         List<Map<String,Object>> list=new ArrayList<>();
//         list.add(getMap("a",1));
//         list.add(getMap("b",2));
//         list.add(getMap("c",3));
//         list.add(getMap("d",4));
//         list.forEach(x->{
//        	 System.out.println(x);
//         });
//		System.out.println(list.stream().filter(abc -> abc.containsKey("a")).count());
//        list.stream().filter(abc -> abc.containsKey("a")).forEach(abc->{
//        	System.out.println(abc);
//        });;
//        
//		Map<String,String> map=new HashMap<>();
		
		
		List<Integer> ilist=new ArrayList<>();
		putList(ilist,100,20);
		
		List<Integer> tmp=ilist.stream().filter(i-> i<60).collect(Collectors.toList());
		System.out.println(tmp.size());
		
		Stream.iterate(100, item -> item-1).limit(100).forEach(System.out::println);
		
	}
	
	public static void putList(List<Integer> list,int max,int length){
		Random ran=new Random();
		for(int i=0;i<length;i++){
			list.add(ran.nextInt(max));
		}
	}
	
	public static Map<String,Object> getMap(String key){
		return getMap(key,null);
	}
	
	public static Map<String,Object> getMap(String key,Object value){
		Map<String,Object> map=new HashMap<>();
		if(null != value){
			map.put(key, value);
		}else{
			map.put(key, UUID.randomUUID().toString());
		}
		return map;
	}
}
