package com.xqh.study.algorithm.generic;

public class GenericMemoryCell<AnyType> {

    private AnyType storedValue;

    public AnyType read(){
        return storedValue;
    }

    public void write(AnyType x){
        this.storedValue = x;
    }
}
