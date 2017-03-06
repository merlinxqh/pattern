package com.xqh.pattern.decorate;



 /**
 * @Description: 饮料实现  DarkRoast
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月20日 下午2:48:13
 * @version V1.0 
 *
 */
public class DarkRoast extends Beverage {
	
	public DarkRoast(String size){
		description="Dark roast";
		this.size=size;
	}
    
	@Override
	public double cost() {
		return 2.36;
	}

}
