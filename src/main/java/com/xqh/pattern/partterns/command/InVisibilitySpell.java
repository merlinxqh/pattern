package com.xqh.pattern.partterns.command;

public class InVisibilitySpell extends Command{

    private Target target;


    @Override
    public void execute(Target target) {
       target.setVisibility(Visibility.INVISIBLE);
       this.target = target;
    }

    @Override
    public void undo() {
       if(null != target){
           target.setVisibility(Visibility.VISIBLE);
       }
    }

    @Override
    public void redo() {
        if(null != target){
            target.setVisibility(Visibility.INVISIBLE);
        }
    }

    @Override
    public String toString() {
        return "Invisibility spell";
    }
}
