package com.xqh.pattern.decorate;

public class StarbuzzCoffee {
   
	public static void main(String[] args) {
		
		Beverage b1=new Espresso("1");//点一份浓缩咖啡
		
		b1=new Mocha(b1);//加一份摩卡调料
		b1=new Mocha(b1);//再加一份摩卡调料
		b1=new Soy(b1);//加一份豆浆调料
		System.out.println(b1.getDescription()+"  费用:"+b1.cost());
	}
}
