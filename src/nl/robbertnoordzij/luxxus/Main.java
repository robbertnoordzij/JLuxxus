package nl.robbertnoordzij.luxxus;

import java.time.LocalTime;

import nl.robbertnoordzij.luxxus.scheduler.Rule;
import nl.robbertnoordzij.luxxus.scheduler.Scheduler;
import nl.robbertnoordzij.luxxus.scheduler.SimpleRule;
import nl.robbertnoordzij.luxxus.scheduler.SunTime;
import nl.robbertnoordzij.luxxus.scheduler.SunTimeRule;
import nl.robbertnoordzij.luxxus.sun.Location;

public class Main {

	public static void main(String[] args) {
		Scheduler scheduler = new Scheduler();
		
		Controller controller = new Controller();
		controller.getEventManager().addExceptionListener((event) -> {
			event.getException().printStackTrace();
		});
		controller.getEventManager().addGatewayConnectedListener((event) -> {
			System.out.println("Connected to Luxxus bridge...");

			// Flash lights to indicate that it is connected
			LampCollection lamps = controller.getLamps();
			LampCollection original = lamps.copy();
			
			for (Lamp lamp : lamps) {
				lamp.setRGBI(255, 255, 255, 255);
			}
			
			controller.updateLamps(lamps);
			controller.updateLamps(original);

			scheduler.start();
		});
		
		// Central Station Rotterdam
		Location location = new Location(-4.469444, 51.925);
		
		// Describe rules for scheduler
		Rule beforeSunSet = new SunTimeRule(location, SunTime.SUNSET).offset(-45).notAfter(LocalTime.of(22, 59));
		Rule afterSunSet = new SunTimeRule(location, SunTime.SUNSET).offset(+30).notAfter(LocalTime.of(22, 59));
		Rule atNight = new SimpleRule().at(LocalTime.of(23, 00));

		scheduler.addTask(beforeSunSet, () -> {
			LampCollection lamps = controller.getLamps();
			for (Lamp lamp : lamps) {
				lamp.setRGBI(255, 220, 210, 255);
			}
			controller.updateLamps(lamps);
		});
		
		scheduler.addTask(afterSunSet, () -> {
			LampCollection lamps = controller.getLamps();
			for (int i = 0; i < lamps.getLength(); i++) {
				Lamp lamp = lamps.at(i);
				
				if (i < 2) {
					lamp.setRGBI(255, 220, 210, 50);
				} else {
					lamp.setRGBI(160, 180, 255, 255);
				}
			}
			controller.updateLamps(lamps);
		});
		
		scheduler.addTask(atNight, () -> {
			LampCollection lamps = controller.getLamps();
			for (Lamp lamp : lamps) {
				lamp.setIntensity(0);
			}
			controller.updateLamps(lamps);
		});
	}

}
