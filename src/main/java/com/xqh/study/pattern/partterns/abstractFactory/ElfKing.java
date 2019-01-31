package com.xqh.study.pattern.partterns.abstractFactory;

public class ElfKing implements King {
    static final String DESCRIPTION = "this is elven king...";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
