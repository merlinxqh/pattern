package com.xqh.study.gc;

public class Singleton {
   
	private static Singleton singleton=new Singleton();
	
	public static int count1;
	
	public static int count2=2;
	
	//先给静态变量赋值 然后 执行静态代码块 
	static{
		count2++;
		System.out.println("执行静态代码块..."+count2);
	}
	
	{
		count2++;
		System.out.println("执行非静态代码块... "+count2);
	}
	
	public static Singleton getInstance(){
		return singleton;
	}
	
	private Singleton(){
		count1++;
		count2++;
		System.out.println("执行构造函数..."+count2);
	}
}
