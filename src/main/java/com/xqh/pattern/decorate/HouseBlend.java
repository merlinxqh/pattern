package com.xqh.pattern.decorate;

 /**
 * @Description: TODO
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月20日 下午2:45:23
 * @version V1.0 
 *
 */
public class HouseBlend extends Beverage {
	
	public HouseBlend(){
		description="House Blend Coffee";
	}

	@Override
	public double cost() {
		return 0.89;
	}

}
