package com.xqh.pattern.observer;

 /**
 * @Description: 目前状况 布告板
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月13日 下午3:41:54
 * @version V1.0 
 *
 */
public class CurrentConditionDisplay implements Observer, DisplayElement {
    private float temperature;
    
    private float humidity;
    
    private float pressure;
    
    private Subject weatherData;
    
    /**
     * 构造器
     * @param weatherData
     */
    public CurrentConditionDisplay(Subject weatherData){
    	this.weatherData=weatherData;
    	weatherData.registerObserver(this);//注册当前 布告板
    }
	
	public void display() {
		System.out.println("current conditions :"+temperature+ " F degress and "+humidity+" % humidity"+pressure);
	}

	public void update(float temp, float humidity, float pressure) {
        //当update()被调用时,我们把温度和湿度保存起来,然后调用display();		
		this.temperature=temp;
		this.humidity=humidity;
		this.pressure=pressure;
		
		display();
	}

}
