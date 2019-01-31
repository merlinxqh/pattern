package com.xqh.study.pattern;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	List<Integer> list=Stream.iterate(100, item -> item-1).limit(100).collect(Collectors.toList());
        list.forEach(x->{
//        	int a=x;
//        	System.out.println(a);
        });
        
		System.out.println(
				Runtime.getRuntime().maxMemory() / 1024 / 1024 + "    " + Runtime.getRuntime().totalMemory() / 1024 / 1024
						+ "    " + Runtime.getRuntime().freeMemory() / 1024 / 1024+"    "
						+(Runtime.getRuntime().maxMemory()
							- Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
		//			logger.info("最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
//					Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024,
//					Runtime.getRuntime().freeMemory() / 1024 / 1024, (Runtime.getRuntime().maxMemory()
//							- Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
    }
}
