package com.xqh.pattern.observer.jdk;

import java.util.Observable;
import java.util.Observer;

import com.xqh.pattern.observer.DisplayElement;

public class CurrentConditionDisplay implements DisplayElement, Observer {
	
	Observable observable;
	private float temperature;
	private float humidity;
	
	public CurrentConditionDisplay(Observable able){
		this.observable=able;
		able.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		if(o instanceof WeatherData){
			WeatherData data=(WeatherData)o;
			this.temperature=data.getTemperature();
			this.humidity=data.getHumidity();
			display();
		}
	}

	public void display() {
        System.out.println("Current conditionds:"+temperature+" F degress and "+humidity+"% humidity");
	}
}
