package nl.robbertnoordzij.luxxus;

import java.time.LocalTime;

import nl.robbertnoordzij.luxxus.scheduler.Scheduler;
import nl.robbertnoordzij.luxxus.scheduler.SimpleRule;
import nl.robbertnoordzij.luxxus.scheduler.SunTime;
import nl.robbertnoordzij.luxxus.scheduler.SunTimeRule;
import nl.robbertnoordzij.luxxus.sun.Location;

public class Main {

	public static void main(String[] args) {
		LuxxusController controller = new LuxxusController();
		Scheduler scheduler = new Scheduler(controller);

		controller.getEventManager().addExceptionListener((event) -> {
			event.getException().printStackTrace();
		});
		
		controller.getEventManager().addGatewayConnectedListener((event) -> {
			System.out.println("Connected to Luxxus bridge...");
			scheduler.start();
		});
		
		// Central Station Rotterdam
		Location location = new Location(-4.469444, 51.925);
		
		// Switch lights on 45 minutes before sunset, but no later than 21:59
		scheduler.addRule(new SunTimeRule(location, SunTime.SUNSET)
			.offset(-45)
			.notAfter(LocalTime.of(21, 59))
			.setScene(lamps -> {
				System.out.println("Switching lights on");
				for (LuxxusLamp lamp : lamps) {
					lamp.setRGB(255, 220, 210);
					lamp.setIntensity(255);
				}
			}));
		
		// Dim lights at 22:00
		scheduler.addRule(new SimpleRule().at(LocalTime.of(22, 00)).setScene(lamps -> {
			System.out.println("Dim lights");
			
			for (int i = 0; i < lamps.length; i++) {
				LuxxusLamp lamp = lamps[i];
				
				if (i < 2) {
					lamp.setRGB(255, 220, 210);
					lamp.setIntensity(50);
				} else {
					lamp.setRGB(160, 180, 255);
					lamp.setIntensity(255);
				}
			}
		}));
		
		// Switch lights off at 23:00
		scheduler.addRule(new SimpleRule().at(LocalTime.of(23, 00)).setScene(lamps -> {
			System.out.println("Switching lights off");
			
			for (LuxxusLamp lamp : lamps) {
				lamp.setIntensity(0);
			}
		}));
	}

}
