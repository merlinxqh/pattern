package com.xqh.pattern.partterns.abstractFactory;

public class ElfCastle implements Castle {
    static final String DESCRIPTION="this is elven castle...";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
