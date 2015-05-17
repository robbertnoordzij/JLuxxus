package nl.robbertnoordzij.luxxus;

import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.GatewayConnectedListener;
import nl.robbertnoordzij.luxxus.events.LampStateChangeListener;

public class LuxxusController implements GatewayConnectedListener, LampStateChangeListener {
	
	private LuxxusClient client = new LuxxusClient();
	
	private LuxxusLamp[] lamps;
	
	public LuxxusController () {
		client.getEventManager().addGatewayConnectedListener(this);
		client.getEventManager().addLampStateChangeListener(this);
		
		client.connect();
	}
	
	public void onLampStateChange() {
		lamps = client.getLamps();
	}

	public void onGatewayConnected() {
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
