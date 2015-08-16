package nl.robbertnoordzij.luxxus;

import java.time.format.DateTimeFormatter;

import nl.robbertnoordzij.luxxus.scheduler.Scheduler;
import nl.robbertnoordzij.luxxus.scheduler.Task;
import nl.robbertnoordzij.luxxus.server.Request;
import nl.robbertnoordzij.luxxus.server.Response;
import nl.robbertnoordzij.luxxus.server.Server;
import nl.robbertnoordzij.luxxus.store.LampStore;
import nl.robbertnoordzij.luxxus.store.RuleStore;

public class Main {
	
	public static void main(String[] args) {
		LampStore lampStore = new LampStore();
		RuleStore ruleStore = new RuleStore();
		
		Scheduler scheduler = new Scheduler();
		Server server = new Server();
		
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
		
		Task lightsOn = () -> {
			Collection lamps = controller.getLamps();
			for (Lamp lamp : lamps) {
				lamp.setRGBI(255, 220, 210, 255);
			}
			controller.updateLamps(lamps);
		};
		Task lightsOff = () -> {
			Collection lamps = controller.getLamps();
			for (Lamp lamp : lamps) {
				lamp.setIntensity(0);
			}
			controller.updateLamps(lamps);
		};
		
		scheduler.addTask(ruleStore.get("before_sunset"), lightsOn);
		
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
		
		scheduler.addTask(ruleStore.get("at_night"), lightsOff);
		
		/**
		 * TODO Refactor...
		 */
		server.requestHandler((Request request, Response response) -> {
			if (! request.isGet()) {
				response.setStatus("405 Method not allowed");
				response.setContent("{\"state\": \"ERROR\", \"Error\":\"405 Method not allowed\"}");
				return;
			}
			
			if (! controller.isConnected()) {
				response.setContent("{\"State\":\"NOT_CONNECTED\"}");
				return;
			}
			
			if (request.getPath().equals("/status.json")) {
				String allOff = controller.areAllLightsOff() ? "true" : "false";
				String lastCommunication = controller.getLastCommunication().format(DateTimeFormatter.ISO_LOCAL_TIME);
				response.setContent("{\"state\":\"CONNECTED\", \"allOff\": " + allOff + ", \"lastCommunication\": \"" + lastCommunication + "\"}");
				return;
			}

			if (request.getPath().equals("/on.json")) {
				new Thread (() -> {
					lightsOn.execute();
				}).start();
				response.setContent("{\"state\":\"DONE\"}");
				return;
			}

			if (request.getPath().equals("/off.json")) {
				new Thread (() -> {
					lightsOff.execute();
				}).start();
				response.setContent("{\"state\":\"DONE\"}");
				return;
			}

			response.setStatus("404 Not found");
			response.setContent("{\"state\": \"ERROR\", \"Error\":\"404 Not found\"}");
		});
	}

}
