package com.xqh.study.pattern.strategy;

public abstract class Duck {
  
	 FlyBehavior flyBehavior;
	
	 QuackBehavior quackBehavior;
	
	public void swim(){
		System.out.println("鸭子在游泳...");
	}
	
	abstract void display();
	
	public void performFly(){
		this.flyBehavior.fly();//委托给行为类
	}
	
	public void performQuack(){
		this.quackBehavior.quack();//委托给行为类
	}

	public void setFlyBehavior(FlyBehavior flyBehavior) {
		this.flyBehavior = flyBehavior;
	}

	public void setQuackBehavior(QuackBehavior quackBehavior) {
		this.quackBehavior = quackBehavior;
	}
	
}
