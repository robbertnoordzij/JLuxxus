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
			scheduler.start();
		});
		
		// Central Station Rotterdam
		Location location = new Location(-4.469444, 51.925);
		
		// Describe rules for scheduler
		Rule atSunSet = new SunTimeRule(location, SunTime.SUNSET).offset(-45).notAfter(LocalTime.of(21, 59));
		Rule atLateEvening = new SimpleRule().at(LocalTime.of(22, 00));
		Rule atNight = new SimpleRule().at(LocalTime.of(23, 00));
		
		// Show that it is connected
		Rule atBoot = new SimpleRule().at(LocalTime.now());
		
		scheduler.addTask(atSunSet, () -> {
			LampCollection lamps = controller.getLamps();
			for (Lamp lamp : lamps) {
				lamp.setRGB(255, 220, 210);
				lamp.setIntensity(255);
			}
			controller.updateLamps(lamps);
		});
		
		scheduler.addTask(atLateEvening, () -> {
			LampCollection lamps = controller.getLamps();
			for (int i = 0; i < lamps.getLength(); i++) {
				Lamp lamp = lamps.at(i);
				
				if (i < 2) {
					lamp.setRGB(255, 220, 210);
					lamp.setIntensity(50);
				} else {
					lamp.setRGB(160, 180, 255);
					lamp.setIntensity(255);
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
		
		scheduler.addTask(atBoot, () -> {
			LampCollection lamps = controller.getLamps();
			LampCollection original = lamps.copy();
			
			for (Lamp lamp : lamps) {
				lamp.setRGB(255, 255, 255);
				lamp.setIntensity(255);
			}
			
			controller.updateLamps(lamps);
			controller.updateLamps(original);
		});
	}

}
