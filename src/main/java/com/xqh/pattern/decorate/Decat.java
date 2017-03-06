package com.xqh.pattern.decorate;



 /**
 * @Description: 饮料 Decat
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月20日 下午2:49:49
 * @version V1.0 
 *
 */
public class Decat extends Beverage {
    
	public Decat(){
		description="Decat";
	}
	
	@Override
	public double cost() {
		return 3.49;
	}

}
