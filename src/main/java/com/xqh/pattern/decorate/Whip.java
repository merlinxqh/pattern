package com.xqh.pattern.decorate;

 /**
 * @Description: 调料 whip
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月20日 下午3:17:09
 * @version V1.0 
 *
 */
public class Whip extends CondimentDecorator {
	
	Beverage beverage;
	
	public Whip(Beverage beverage){
		this.beverage=beverage;
	}

	@Override
	public double cost() {
		return 0.6+beverage.cost();
	}

	@Override
	public String getDescription() {
		return beverage.getDescription()+",Whip.";
	}

}
