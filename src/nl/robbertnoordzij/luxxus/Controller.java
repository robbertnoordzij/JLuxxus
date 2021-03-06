package nl.robbertnoordzij.luxxus;

import java.time.LocalTime;

import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.events.GatewayConnectedEvent;
import nl.robbertnoordzij.luxxus.events.events.LampStateChangedEvent;
import nl.robbertnoordzij.luxxus.events.listeners.GatewayConnectedListener;
import nl.robbertnoordzij.luxxus.events.listeners.LampStateChangedListener;

public class Controller implements GatewayConnectedListener, LampStateChangedListener {
	
	private Client client = new Client();
	
	private boolean connected = false;
	
	public Controller () {
		client.getEventManager().addGatewayConnectedListener(this);
		client.getEventManager().addLampStateChangedListener(this);
		
		client.connect();
	}
	
	public void onLampStateChanged(LampStateChangedEvent event) {
		
	}

	public void onGatewayConnected(GatewayConnectedEvent event) {
		connected = true;
	}
	
	public EventManager getEventManager() {
		return client.getEventManager();
	}
	
	public Collection getLamps() {
		return new Collection(client.getLamps());
	}
	
	public void updateLamps(Collection lamps) {
		updateLamps(lamps, true);
	}
	
	public void updateLamps(Collection lamps, boolean confirm) {
		client.updateLamps(lamps.getRaw());
		
		if (confirm) {
			Utility.sleep(5000);
			
			// Retry
			if  (! getLamps().equals(lamps)) {
				System.out.println(LocalTime.now() + " Retry...");
				client.updateLamps(lamps.getRaw());
			}
		}
	}
	
	public boolean areAllLightsOff() {
		Collection lamps = getLamps();
		
		for (Lamp lamp : lamps) {
			if (lamp.getIntensity() != 0) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public LocalTime getLastCommunication() {
		return client.getLastCommunication();
	}
}
