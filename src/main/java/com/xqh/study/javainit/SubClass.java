package com.xqh.study.javainit;

public class SubClass extends SuperClass{
	
	public final static String CONSTANT_VARIABLE="constant variable...";
	
	public static String STATIC_VARIABLE="static variable...";
	
  static {
	  System.out.println("sub class init...");
  }
}
