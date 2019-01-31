package com.xqh.study.pattern.strategy.impl;

import com.xqh.study.pattern.strategy.QuackBehavior;

public class GuaGuaQuack implements QuackBehavior {

	public void quack() {
		System.out.println("呱呱叫...");
	}

}
