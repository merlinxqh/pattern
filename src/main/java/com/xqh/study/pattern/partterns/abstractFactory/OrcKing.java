package com.xqh.study.pattern.partterns.abstractFactory;

public class OrcKing implements King{

    static final String DESCRIPTION = "this is orc king ...";
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
