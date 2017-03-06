package com.xqh.pattern.decorate;

 /**
 * @Description: 调料soy
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月20日 下午3:15:56
 * @version V1.0 
 *
 */
public class Soy extends CondimentDecorator{
	
	Beverage beverage;
	
	public Soy(Beverage beverage){
		this.beverage=beverage;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription()+",Soy.";
	}

	@Override
	public double cost() {
		return 0.5+beverage.cost();
	}

}
