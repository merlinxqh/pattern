package com.xqh.study.pattern.observer.jdk;

import java.util.Observable;
import java.util.Observer;

import com.xqh.study.pattern.observer.DisplayElement;

public class ForecastDisplay implements DisplayElement, Observer {
	
	Observable observable;
	private float temperature;
	private float humidity;
	private float pressure;
    
	
	public ForecastDisplay(Observable observerable){
		this.observable=observerable;
		observerable.addObserver(this);
	}
	
	public void update(Observable o, Object arg) {
		if(o instanceof WeatherData){
			WeatherData data=(WeatherData)o;
			this.temperature=data.getTemperature();
			this.humidity=data.getHumidity();
			this.pressure=data.getPressure();
			display();
		}
	}

	public void display() {
		System.out.println("forecast display:"+temperature+" F degress and "+humidity+"% humidity "+pressure);
	}

}
