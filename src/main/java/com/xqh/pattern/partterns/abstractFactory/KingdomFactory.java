package com.xqh.pattern.partterns.abstractFactory;

public interface KingdomFactory {

    Army createArmy();

    Castle createCastle();

    King createKing();
}
