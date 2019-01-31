package com.xqh.study.pattern.strategy;

import com.xqh.study.pattern.strategy.impl.FlyNoWay;

public class Test {
   public static void main(String[] args) {
	
	   Duck duck=new MallardDuck();
	   
	   duck.display();
	   duck.performFly();
	   duck.performQuack();
	   duck.setFlyBehavior(new FlyNoWay());
	   duck.performFly();
}
}
