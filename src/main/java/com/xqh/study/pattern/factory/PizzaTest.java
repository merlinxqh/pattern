package com.xqh.study.pattern.factory;

public class PizzaTest {
   
   public static void main(String[] args) {
	   PizzaStore p=new NYPizzaStore();
	   p.orderPizza("green");//下单
   }
}
