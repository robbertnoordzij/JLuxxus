package nl.robbertnoordzij.luxxus;

import nl.robbertnoordzij.luxxus.scheduler.Scheduler;
import nl.robbertnoordzij.luxxus.store.LampStore;
import nl.robbertnoordzij.luxxus.store.RuleStore;

public class Main {
	
	public static void main(String[] args) {
		LampStore lampStore = new LampStore();
		RuleStore ruleStore = new RuleStore();
		
		Scheduler scheduler = new Scheduler();
	
		Controller controller = new Controller();
		controller.getEventManager().addExceptionListener((event) -> {
			event.getException().printStackTrace();
		});
		
		controller.getEventManager().addGatewayConnectedListener((event) -> {
			System.out.println("Connected to Luxxus bridge...");

			// Flash lights to indicate that it is connected
			Collection lamps = controller.getLamps();
			Collection original = lamps.copy();
			
			for (Lamp lamp : lamps) {
				lamp.setRGBI(255, 255, 255, 255);
			}
			
			controller.updateLamps(lamps);
			controller.updateLamps(original);

			scheduler.start();
		});
		
		scheduler.addTask(ruleStore.get("before_sunset"), () -> {
			Collection lamps = controller.getLamps();
			for (Lamp lamp : lamps) {
				lamp.setRGBI(255, 220, 210, 255);
			}
			controller.updateLamps(lamps);
		});
		
		scheduler.addTask(ruleStore.get("after_sunset"), () -> {
			Collection lamps = controller.getLamps();
			for (Lamp lamp : lamps) {
				if (lamp.getAlias() == lampStore.get("front_left") || lamp.getAlias() == lampStore.get("front_right")) {
					lamp.setRGBI(255, 220, 210, 50);
				} else {
					lamp.setRGBI(160, 180, 255, 255);
				}
			}
			controller.updateLamps(lamps);
		});
		
		scheduler.addTask(ruleStore.get("at_night"), () -> {
			Collection lamps = controller.getLamps();
			for (Lamp lamp : lamps) {
				lamp.setIntensity(0);
			}
			controller.updateLamps(lamps);
		});
	}

}
