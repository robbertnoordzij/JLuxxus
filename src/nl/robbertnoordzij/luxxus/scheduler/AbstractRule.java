package nl.robbertnoordzij.luxxus.scheduler;

import nl.robbertnoordzij.luxxus.LuxxusLamp;

public abstract class AbstractRule implements Rule {
	
	private Scene scene;
	
	public Rule setScene(Scene scene) {
		this.scene = scene;
		
		return this;
	}

	public void execute(LuxxusLamp[] lamps) {
		scene.processScene(lamps);
	}
}
