package com.xqh.study.pattern.partterns.adapter;

public class App {

    public static void main(String[] args) {
        Captain captain = new Captain(new FishingBoatAdapter(new FishingBoat()));
        captain.row();
    }
}
