package com.xqh.pattern.decorate;



 /**
 * @Description: 调料抽象类
 *    首先,必须让Condiment Decorate能够取代Beverage,所以Condiment Decorate拓展自Beverage.
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月20日 下午2:36:55
 * @version V1.0 
 *
 */
public abstract class CondimentDecorator extends Beverage {
    
	/**
	 * 所有调料装饰者必须重新实现getDescription()方法....
	 * TODO 稍后解释..
	 */
	public abstract String getDescription();
}
