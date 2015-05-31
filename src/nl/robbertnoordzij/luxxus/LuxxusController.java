package nl.robbertnoordzij.luxxus;

import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.events.GatewayConnectedEvent;
import nl.robbertnoordzij.luxxus.events.listeners.GatewayConnectedListener;

public class LuxxusController implements GatewayConnectedListener {
	
	private LuxxusClient client = new LuxxusClient();
	
	private LuxxusLamp[] lamps;
	
	public LuxxusController () {
		client.getEventManager().addGatewayConnectedListener(this);
		
		client.connect();
	}

	public void onGatewayConnected(GatewayConnectedEvent event) {
		lamps = client.getLamps();
	}
	
	public EventManager getEventManager() {
		return client.getEventManager();
	}
	
	public LuxxusLamp[] getLamps() {
		return lamps;
	}
	
	public void updateLamps(LuxxusLamp[] lamps) {
		client.updateLamps(lamps);
	}
}
