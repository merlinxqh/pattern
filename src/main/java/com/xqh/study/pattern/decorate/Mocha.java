package com.xqh.study.pattern.decorate;

 /**
 * @Description: 调料 mocha
 * 
 *  摩卡是一个装饰者 所以 拓展自CondimentDecorator
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月20日 下午2:56:59
 * @version V1.0 
 * 要让Mocha能够引用一个Beverage,做法如下:
 *    (1)用一个实例变量记录饮料,也就是被装饰者.
 *    (2)想办法让被装饰者(饮料)被记录到实例变量中.这里的做法是把饮料当做构造器参数,再由构造器将此饮料记录到实例变量中.
 *
 */
public class Mocha extends CondimentDecorator {
	
	Beverage beverage;
	
	public Mocha(Beverage beverage){
		this.beverage=beverage;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription()+",Mocha";//组合饮料名称+调料
	}

	@Override
	public double cost() {
		String _size=getSize();
		if("0".equals(_size)){
			
		}else if("1".equals(_size)){
			
		}else{
			
		}
		return 0.2+beverage.cost();//调料价+对应饮料价
	}

}
