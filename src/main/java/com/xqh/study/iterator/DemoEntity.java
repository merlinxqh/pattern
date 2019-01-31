package com.xqh.study.iterator;

import java.math.BigDecimal;

/**
 * Created by leo on 2017/7/25.
 */
public class DemoEntity {

    private String name;

    private String title;

    private Integer level;

    private BigDecimal price;

    private Double doublePrice;

    private Boolean booleanFiled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getDoublePrice() {
        return doublePrice;
    }

    public void setDoublePrice(Double doublePrice) {
        this.doublePrice = doublePrice;
    }

    public Boolean getBooleanFiled() {
        return booleanFiled;
    }

    public void setBooleanFiled(Boolean booleanFiled) {
        this.booleanFiled = booleanFiled;
    }
}
