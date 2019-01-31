package com.xqh.study.pattern.observer.jdk;

public class WeatherStation {
   public static void main(String[] args) {
	 WeatherData data=new WeatherData();
	 CurrentConditionDisplay play=new CurrentConditionDisplay(data);
	 ForecastDisplay fd=new ForecastDisplay(data);
	 data.setMeasurements(1, 2, 3);
}
}
