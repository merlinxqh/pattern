package com.xqh.study.pattern.partterns.abstractFactory;

public interface KingdomFactory {

    Army createArmy();

    Castle createCastle();

    King createKing();
}
