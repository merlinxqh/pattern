package com.xqh.study.pattern.observer;

import java.util.ArrayList;

/**
 * 气象数据
 * @Description: TODO
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月13日 下午3:19:14
 * @version V1.0 
 *
 */
public class WeatherData implements Subject {
	
	/**
	 * 记录观察者
	 */
	private ArrayList<Observer> observers;
	
	private float temperature;
	
	private float humidity;
	
	private float pressure;
	
	public WeatherData(){
		observers=new ArrayList<Observer>();
	}
	
    /**
     * 注册观察者
     */
	public void registerObserver(Observer o) {
		observers.add(o);
	}
    
	/**
	 * 移除观察者
	 */
	public void removeObserver(Observer o) {
		int i=observers.indexOf(o);
		if(i>=0){
			observers.remove(i);
		}

	}
    
	/**
	 * 通知所有观察者
	 */
	public void notifyAllObserver() {
		for(Observer ob:observers){
			ob.update(temperature, humidity, pressure);
		}

	}
	
	/**
	 * 当气象站得到更新观测值时,我们通知观察者
	 */
	public void measurementsChanged(){
		notifyAllObserver();
	}
	
	public void setMeasurements(float temperature, float humidity, float pressure){
		this.temperature=temperature;
		this.humidity=humidity;
		this.pressure=pressure;
		measurementsChanged();
	}

}
