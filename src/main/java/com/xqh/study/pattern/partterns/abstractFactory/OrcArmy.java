package com.xqh.study.pattern.partterns.abstractFactory;

public class OrcArmy implements Army {
    static final String DESCRIPTION = "this is orc army...";
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
