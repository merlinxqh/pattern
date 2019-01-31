package com.xqh.study.pattern.partterns.command;

public enum Visibility {

    VISIBLE("visible"),INVISIBLE("invisible");

    private String title;

    Visibility(String title){
        this.title = title;
    }


    @Override
    public String toString() {
        return title;
    }

}
