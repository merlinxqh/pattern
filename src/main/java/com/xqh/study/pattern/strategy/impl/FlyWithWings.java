package com.xqh.study.pattern.strategy.impl;

import com.xqh.study.pattern.strategy.FlyBehavior;

public class FlyWithWings implements FlyBehavior{

	public void fly() {
		System.out.println("会飞的...");
	}

}
