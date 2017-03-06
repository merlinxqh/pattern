package com.xqh.pattern.factory;

/**
 * 
 * @Description: 披萨店超类
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月26日 下午3:06:54
 * @version V1.0 
 *
 */
public abstract class PizzaStore {
  
	public Pizza orderPizza(String type){
		Pizza pizza;
		pizza=createPizza(type);
		pizza.prepaer();
		pizza.bake();
		pizza.cut();
		pizza.box();
		return pizza;
	}
	
	/**
	 * 不同pizza店 有不同制作pizza的流程
	 * @param type
	 * @return
	 */
	abstract Pizza createPizza(String type);
}
