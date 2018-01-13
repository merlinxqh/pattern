package com.xqh.pattern.partterns.abstractDocument.domain;

import com.xqh.pattern.partterns.abstractDocument.Document;

import java.util.stream.Stream;

public interface HasParts extends Document {

    String PROPERTY = "parts";

    default Stream<Parts> getParts(){
        return children(PROPERTY, Parts::new);
    }
}
