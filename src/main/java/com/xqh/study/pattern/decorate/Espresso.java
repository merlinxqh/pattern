package com.xqh.study.pattern.decorate;



 /**
 * @Description: 饮料 实现 (浓缩咖啡...)
 *   拓展自 饮料超类
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月20日 下午2:42:06
 * @version V1.0 
 *
 */
public class Espresso extends Beverage {
	
	//设置饮料的描述
	public Espresso(String size){
		description="Espresso";
		this.size=size;
	}

	@Override
	public double cost() {
		return 1.99;
	}

}
