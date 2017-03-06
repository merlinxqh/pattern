package com.xqh.pattern.observer.jdk;

import java.util.Observable;

/**
 * 
 * @Description: jdk自带观察者模式
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月13日 下午5:24:24
 * @version V1.0 
 *
 */
public class WeatherData extends Observable {
   private float temperature;
   
   private float humidity;
   
   private float pressure;
   
   public void measurementChanged(){
	   setChanged();//调用setChanged()来指示 状态已经改变
	   notifyObservers();//**注意 没有调用notifyObservers(args);带参数的方法,表明 我们采用的做法是 "拉" PULL
   }
   
   public void setMeasurements(float temperature,float humidity,float pressure){
	   this.temperature=temperature;
	   this.humidity=humidity;
	   this.pressure=pressure;
	   
	   measurementChanged();
   }
    
    /**
     * 我们要使用 "拉"  的方式,观察者 可以用这些getter方法 获取weatherData对象的状态
     * @return
     */
	public float getTemperature() {
		return temperature;
	}
	
	public float getHumidity() {
		return humidity;
	}
	
	public float getPressure() {
		return pressure;
	}
   
   
}
