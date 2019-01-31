package com.xqh.study.pattern.command;

public class GarageDoorCommand implements Command{
	
	GarageDoor garage;
	
	public GarageDoorCommand(GarageDoor garage){
		this.garage=garage;
	}

	public void excute() {
		garage.up();
	}

	public void undo() {
	   garage.close();
	}

}
