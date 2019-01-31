package com.xqh.study.pattern.observer;

public class WeatherStation {
   public static void main(String[] args) {
	
	   WeatherData data=new WeatherData();
	   
	   CurrentConditionDisplay display=new CurrentConditionDisplay(data);
	   CurrentConditionDisplay dis=new CurrentConditionDisplay(data);
	   data.setMeasurements(10, 12, 11);
}
}
