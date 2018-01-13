package com.xqh.pattern.partterns.abstractDocument.domain;

import com.xqh.pattern.partterns.abstractDocument.AbstractDocument;

import java.util.Map;

public class Parts extends AbstractDocument implements HasModel,HasPrice,HasType {

    public Parts(Map<String, Object> properties) {
        super(properties);
    }
}
