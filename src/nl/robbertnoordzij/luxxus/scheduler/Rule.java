package nl.robbertnoordzij.luxxus.scheduler;

import nl.robbertnoordzij.luxxus.LuxxusLamp;

public interface Rule {
	public boolean shouldExecute();
	
	public Rule setScene(Scene scene);
	
	public void execute(LuxxusLamp[] lamps);
}
