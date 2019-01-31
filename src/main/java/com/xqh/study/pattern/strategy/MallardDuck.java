package com.xqh.study.pattern.strategy;

import com.xqh.study.pattern.strategy.impl.FlyWithWings;
import com.xqh.study.pattern.strategy.impl.JiaJiaQuack;

public class MallardDuck extends Duck {

	@Override
	void display() {
		System.out.println("这是绿头鸭...");
	}

    public MallardDuck(){
    	flyBehavior=new FlyWithWings();
    	quackBehavior=new JiaJiaQuack();
    }
	
}
