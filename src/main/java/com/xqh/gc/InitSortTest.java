package com.xqh.gc;

public class InitSortTest {
	public static void main(String[] args) {
		Son son=new Son();
		System.out.println(son);
	}

}
class Parent{
	{
		System.out.println("执行 parent 初始化块...");
	}
	
	static {
		System.out.println("执行 parent static初始化块...");
		
	}
	
	public Parent(){
		System.out.println("执行 parent 构造函数...");
	}
}

class Son extends Parent{
	{
		System.out.println("执行 Son 初始化块...");
	}
	
	static {
		System.out.println("执行 Son static初始化块...");
		
	}
	
	public Son(){
		System.out.println("执行 Son 构造函数...");
	}
}