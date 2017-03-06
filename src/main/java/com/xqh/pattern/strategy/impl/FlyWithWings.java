package com.xqh.pattern.strategy.impl;

import com.xqh.pattern.strategy.FlyBehavior;

public class FlyWithWings implements FlyBehavior{

	public void fly() {
		System.out.println("会飞的...");
	}

}
