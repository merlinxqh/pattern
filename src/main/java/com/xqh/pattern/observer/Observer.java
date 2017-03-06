package com.xqh.pattern.observer;



 /**
 * @Description: 观察者接口
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月13日 下午2:51:37
 * @version V1.0 
 *
 */
public interface Observer {
    
	/**
	 * 
	 * @param temp
	 * @param humidity
	 * @param pressure
	 */
	public void update(float temp, float humidity, float pressure);
}
