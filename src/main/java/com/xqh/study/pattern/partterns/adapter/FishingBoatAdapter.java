package com.xqh.study.pattern.partterns.adapter;

public class FishingBoatAdapter implements RowingBoat {

    private FishingBoat fishingBoat;

    public FishingBoatAdapter(FishingBoat fishingBoat){
        this.fishingBoat = fishingBoat;
    }

    @Override
    public void row() {
        fishingBoat.sail();
    }
}
