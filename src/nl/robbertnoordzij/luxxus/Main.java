package nl.robbertnoordzij.luxxus;

import nl.robbertnoordzij.luxxus.scheduler.Scheduler;
import nl.robbertnoordzij.luxxus.scheduler.SimpleRule;

public class Main {

	public static void main(String[] args) {
		LuxxusController controller = new LuxxusController();
		Scheduler scheduler = new Scheduler();
		
		// Switch lights on at 21:00
		scheduler.addRule(new SimpleRule(21, 00).setScene(lamps -> {
			for (LuxxusLamp lamp : lamps) {
				lamp.setRGB(220, 180, 180);
				lamp.setIntensity(255);
			}
			
			controller.updateLamps(lamps);
		}));
		
		// Switch lights off at 22:00
		scheduler.addRule(new SimpleRule(22, 00).setScene(lamps -> {
			for (LuxxusLamp lamp : lamps) {
				lamp.setIntensity(0);
			}
			
			controller.updateLamps(lamps);
		}));
		
		controller.getEventManager().addExceptionListener((event) -> {
			System.out.println("Print exception");
			event.getException().printStackTrace();
		});
		
		scheduler.getEventManager().addScheduledTaskListener((event) -> {
			event.getRule().execute(controller.getLamps());
		});
		
		controller.getEventManager().addGatewayConnectedListener((event) -> {
			scheduler.start();
		});
	}

}
