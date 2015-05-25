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
		
		// Switch lights on 30 minutes before sunset, but no later than 22:30
		scheduler.addRule(new SunTimeRule(location, SunTime.SUNSET)
			.offset(-30)
			.notAfter(LocalTime.of(22, 30))
			.setScene(lamps -> {
				for (LuxxusLamp lamp : lamps) {
					lamp.setRGB(255, 200, 200);
					lamp.setIntensity(255);
				}
			}));
		
		// Dim lights at 22:00
		scheduler.addRule(new SimpleRule().at(LocalTime.of(22, 00)).setScene(lamps -> {
			for (LuxxusLamp lamp : lamps) {
				lamp.setRGB(125, 125, 255);
				lamp.setIntensity(180);
			}
		}));
		
		// Switch lights off at 23:00
		scheduler.addRule(new SimpleRule().at(LocalTime.of(23, 00)).setScene(lamps -> {
			for (LuxxusLamp lamp : lamps) {
				lamp.setIntensity(0);
			}
		}));
	}

}
