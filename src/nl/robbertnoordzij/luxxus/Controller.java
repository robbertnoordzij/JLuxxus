package nl.robbertnoordzij.luxxus;

import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.events.GatewayConnectedEvent;
import nl.robbertnoordzij.luxxus.events.listeners.GatewayConnectedListener;

public class Controller implements GatewayConnectedListener {
	
	private Client client = new Client();
	
	private LampCollection lamps;
	
	public Controller () {
		client.getEventManager().addGatewayConnectedListener(this);
		
		client.connect();
	}

	public void onGatewayConnected(GatewayConnectedEvent event) {
		lamps = new LampCollection(client.getLamps());
	}
	
	public EventManager getEventManager() {
		return client.getEventManager();
	}
	
	public LampCollection getLamps() {
		return lamps;
	}
	
	public void updateLamps(LampCollection lamps) {
		client.updateLamps(lamps.getRaw());
	}
}
