package com.xqh.study.pattern.partterns.abstractDocument.domain;

import com.xqh.study.pattern.partterns.abstractDocument.Document;

import java.util.Optional;

public interface HasType extends Document {

    String PROPERTY = "type";

    default Optional<String> getType(){
        return Optional.ofNullable((String) get(PROPERTY));
    }
}
