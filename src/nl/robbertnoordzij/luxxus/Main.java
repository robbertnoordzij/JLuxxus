package nl.robbertnoordzij.luxxus;

import nl.robbertnoordzij.luxxus.scheduler.Scheduler;
import nl.robbertnoordzij.luxxus.scheduler.SimpleRule;

public class Main {

	public static void main(String[] args) {
		LuxxusController controller = new LuxxusController();
		Scheduler scheduler = new Scheduler(controller);

		controller.getEventManager().addExceptionListener((event) -> {
			event.getException().printStackTrace();
		});
		
		controller.getEventManager().addGatewayConnectedListener((event) -> {
			scheduler.start();
		});
		
		// Switch lights on at 21:00
		scheduler.addRule(new SimpleRule(21, 00).setScene(lamps -> {
			for (LuxxusLamp lamp : lamps) {
				lamp.setRGB(220, 190, 190);
				lamp.setIntensity(255);
			}
		}));
		
		// Switch lights off at 22:00
		scheduler.addRule(new SimpleRule(22, 00).setScene(lamps -> {
			for (LuxxusLamp lamp : lamps) {
				lamp.setIntensity(0);
			}
		}));
	}

}
