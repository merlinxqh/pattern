package com.xqh.study.pattern.partterns.abstractDocument.domain;

import com.xqh.study.pattern.partterns.abstractDocument.AbstractDocument;

import java.util.Map;

public class Cars extends AbstractDocument implements HasModel,HasPrice,HasParts {
    public Cars(Map<String, Object> properties) {
        super(properties);
    }
}
