package nl.robbertnoordzij.luxxus;

import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.events.GatewayConnectedEvent;
import nl.robbertnoordzij.luxxus.events.listeners.GatewayConnectedListener;

public class Controller implements GatewayConnectedListener {
	
	private Client client = new Client();
	
	private Lamp[] lamps;
	
	public Controller () {
		client.getEventManager().addGatewayConnectedListener(this);
		
		client.connect();
	}

	public void onGatewayConnected(GatewayConnectedEvent event) {
		lamps = client.getLamps();
	}
	
	public EventManager getEventManager() {
		return client.getEventManager();
	}
	
	public Lamp[] getLamps() {
		return lamps;
	}
	
	public void updateLamps(Lamp[] lamps) {
		client.updateLamps(lamps);
	}
}
