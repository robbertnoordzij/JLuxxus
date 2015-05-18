package nl.robbertnoordzij.luxxus;

import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.GatewayConnectedListener;
import nl.robbertnoordzij.luxxus.events.LampStateChangedListener;

public class LuxxusController implements GatewayConnectedListener, LampStateChangedListener {
	
	private LuxxusClient client = new LuxxusClient();
	
	private LuxxusLamp[] lamps;
	
	public LuxxusController () {
		client.getEventManager().addGatewayConnectedListener(this);
		client.getEventManager().addLampStateChangedListener(this);
		
		client.connect();
	}
	
	public void onLampStateChanged() {
		System.out.println("State changed...");
		lamps = client.getLamps();
	}

	public void onGatewayConnected() {
		System.out.println("Connected to gateway...");
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
