package com.xqh.study.pattern.decorate;


/**
 * @Description: 饮料  超类
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月20日 下午2:33:31
 * @version V1.0 
 *
 */
public abstract class Beverage {
    
	/**
	 * 饮料名称
	 */
	String description="UnKnow Beverage";
	
	/**
	 * 0小杯 1中杯 2大杯
	 */
	String size="0";
	
	public String getSize(){
		return size;
	}
	

	public String getDescription() {
		return description;
	}
	
	/**
	 * 费用
	 * @return
	 */
	public abstract double cost();
	
}
