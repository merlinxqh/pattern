package com.xqh.study.pattern.partterns.command;

public class App {
    public static void main(String[] args) {
        Wizard wizard = new Wizard();
        Goblin goblin = new Goblin();

        goblin.printStatus();

        wizard.castSpell(new ShrinkSpell(), goblin);
        goblin.printStatus();

        wizard.castSpell(new InVisibilitySpell(), goblin);
        goblin.printStatus();

        wizard.undoLastSpell();
        goblin.printStatus();

        wizard.undoLastSpell();
        goblin.printStatus();

        wizard.redoLastSpell();
        goblin.printStatus();

        wizard.redoLastSpell();
        goblin.printStatus();
    }
}
