package com.xqh.study.pattern.partterns.abstractFactory;

/**
 * Elven  精灵
 */
public class ElfArmy implements Army {

    static final String DESCRIPTION="this is elven army...";
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
