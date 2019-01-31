package com.xqh.study.pattern.partterns.adapter;

public class Captain implements RowingBoat {

    private RowingBoat rowingBoat;

    public void setRowingBoat(RowingBoat rowingBoat) {
        this.rowingBoat = rowingBoat;
    }

    public Captain(){

    }

    public Captain(RowingBoat rowingBoat){
       this.rowingBoat=rowingBoat;
    }
    @Override
    public void row() {
        rowingBoat.row();
    }
}
