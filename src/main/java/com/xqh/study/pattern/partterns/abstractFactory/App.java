package com.xqh.study.pattern.partterns.abstractFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private Army army;

    private Castle castle;

    private King king;

    /**
     * create kingdom
     * @param kingdomFactory
     */
    public void createKingdom(final KingdomFactory kingdomFactory){
       setArmy(kingdomFactory.createArmy());
       setCastle(kingdomFactory.createCastle());
       setKing(kingdomFactory.createKing());
    }

    public Army getArmy() {
        return army;
    }

    public void setArmy(Army army) {
        this.army = army;
    }

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public King getKing() {
        return king;
    }

    public void setKing(King king) {
        this.king = king;
    }

    public static enum KingdomType{
        ELF,ORC
    }

    public static class KingdomMaker{
        public static KingdomFactory makeFactory(KingdomType type){
            switch (type){
                case ELF:
                    return new ElfKingdomFactory();
                case ORC:
                    return new OrcKingdomFactory();
                default:
                    throw new IllegalArgumentException("KingdomType not supported.");
            }
        }
    }

    public static void main(String[] args) {
        App app=new App();
        app.createKingdom(KingdomMaker.makeFactory(KingdomType.ELF));
        LOGGER.info("army=>"+app.getArmy().getDescription());
        LOGGER.info("castle=>"+app.getCastle().getDescription());
        LOGGER.info("army=>"+app.getKing().getDescription());

        app.createKingdom(KingdomMaker.makeFactory(KingdomType.ORC));
        LOGGER.info("army=>"+app.getArmy().getDescription());
        LOGGER.info("castle=>"+app.getCastle().getDescription());
        LOGGER.info("army=>"+app.getKing().getDescription());
    }
}
