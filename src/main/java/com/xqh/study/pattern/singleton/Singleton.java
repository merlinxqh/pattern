package com.xqh.study.pattern.singleton;

public class Singleton {
	
	
	/**
	 * volatile 关键字 可以在 多线程 并发时 让 多线程 取到变化的uniqueInstance
	 */
	private volatile static Singleton uniqueInstance;
	
	/**
	 *构造器 私有化
	 */
	private Singleton(){
		
	}
	
	public static Singleton getInstance(){
		/**
		 * 只有在 第一次 访问时 才会 
		 */
		if(uniqueInstance == null){
			synchronized (Singleton.class) {
				if(uniqueInstance == null){
					uniqueInstance=new Singleton();
				}
			}
		}
		return uniqueInstance;
	}

}
