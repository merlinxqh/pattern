package com.xqh.study.pattern.partterns.abstractFactory;

public class OrcCastle implements Castle {
    static final String DESCRIPTION = "this is orc castle";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
