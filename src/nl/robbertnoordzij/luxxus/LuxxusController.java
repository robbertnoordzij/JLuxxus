package nl.robbertnoordzij.luxxus;

import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.events.GatewayConnectedEvent;
import nl.robbertnoordzij.luxxus.events.events.LampStateChangedEvent;
import nl.robbertnoordzij.luxxus.events.listeners.GatewayConnectedListener;
import nl.robbertnoordzij.luxxus.events.listeners.LampStateChangedListener;

public class LuxxusController implements GatewayConnectedListener, LampStateChangedListener {
	
	private LuxxusClient client = new LuxxusClient();
	
	private LuxxusLamp[] lamps;
	
	public LuxxusController () {
		client.getEventManager().addGatewayConnectedListener(this);
		client.getEventManager().addLampStateChangedListener(this);
		
		client.connect();
	}
	
	public void onLampStateChanged(LampStateChangedEvent event) {
		
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
