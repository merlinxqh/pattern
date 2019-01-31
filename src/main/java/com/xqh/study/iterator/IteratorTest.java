package com.xqh.study.iterator;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

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
		
		
//		List<Integer> ilist=new ArrayList<>();
//		putList(ilist,100,20);
//
//		List<Integer> tmp=ilist.stream().filter(i-> i<60).collect(Collectors.toList());
//		System.out.println(tmp.size());
//
//		Stream.iterate(100, item -> item-1).limit(100).forEach(System.out::println);
        List<DemoEntity> demoList=new ArrayList<>();
        demoList.add(getDemoEntity("abc",12));
		demoList.add(getDemoEntity("qq",35));
		demoList.add(getDemoEntity("w",5));
		demoList.add(getDemoEntity("ccc",8));
		demoList.add(getDemoEntity("abc",33));
		demoList.add(getDemoEntity("ccc",69));
		demoList.add(getDemoEntity("qq",1));
        //返回一个新的List<DemoEntity>
//		demoList.stream().map(d-> {
//			DemoEntity demo=new DemoEntity();
//			demo.setLevel(d.getLevel()+1);
//			return demo;
//		}).collect(Collectors.toList()).forEach(i->{
//			System.out.println(JSON.toJSONString(i));
//		});
		//排序
//		demoList.stream().sorted((a,b)->b.getName().compareTo(a.getName())).forEach(item->{
//			System.out.println(JSON.toJSONString(item));
//		});
        //按name进行分组 返回Map<String,List<DemoEntity>>
		System.out.println(JSON.toJSONString(demoList.stream().collect(Collectors.groupingBy(DemoEntity :: getName))));
        demoList.stream().map(demo-> mapEntity(demo));


	}

	public static DemoEntity mapEntity(DemoEntity demo){
		DemoEntity result=new DemoEntity();
		result.setLevel(demo.getLevel()+1);
		result.setName(demo.getName().toUpperCase());
		return result;
	}
	public static DemoEntity getDemoEntity(String name){
		DemoEntity demo=new DemoEntity();
		demo.setName(name);
		return demo;
	}

	public static DemoEntity getDemoEntity(String name,int level){
		DemoEntity demo=new DemoEntity();
		demo.setName(name);
		demo.setLevel(level);
		return demo;
	}


	public static DemoEntity getDemoEntity(int level){
		DemoEntity demo=new DemoEntity();
		demo.setLevel(level);
		return demo;
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
