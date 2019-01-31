package com.xqh.study.pattern.strategy.impl;

import com.xqh.study.pattern.strategy.FlyBehavior;

public class FlyNoWay implements FlyBehavior {

	public void fly() {
		System.out.println("不会飞的...");
	}

}
