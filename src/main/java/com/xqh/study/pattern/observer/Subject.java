package com.xqh.study.pattern.observer;

 /**
 * @Description: 主题接口
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月13日 下午2:54:45
 * @version V1.0 
 *
 */
public interface Subject {
   
	/**
	 * 注册观察者
	 * @param o
	 */
	public void registerObserver(Observer o);
	
	/**
	 * 移除观察者
	 * @param o
	 */
	public void removeObserver(Observer o);
	
	/**
	 * 通知所有观察者
	 */
	public void notifyAllObserver();
}
