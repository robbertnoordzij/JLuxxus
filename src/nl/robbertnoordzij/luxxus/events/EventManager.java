package nl.robbertnoordzij.luxxus.events;

import java.util.ArrayList;

public class EventManager {
	private static EventManager instance;
	
	private ArrayList<GatewayConnectedListener> gatewayConnectedListeners = new ArrayList<GatewayConnectedListener>();
	private ArrayList<LampStateChangeListener> lampStateChangeListeners = new ArrayList<LampStateChangeListener>();
	
	private EventManager () {
		
	}
	
	public void addLampStateChangeListener(LampStateChangeListener listener) {
		if (!lampStateChangeListeners.contains(listener)) {
			lampStateChangeListeners.add(listener);
		}
	}

	public void addGatewayConnectedListener(GatewayConnectedListener listener) {
		if (!gatewayConnectedListeners.contains(listener)) {
			gatewayConnectedListeners.add(listener);
		}
	}
	
	public void triggerLampStateChange() {
		for (LampStateChangeListener listener : lampStateChangeListeners) {
			listener.onLampStateChange();
		}
	}
	
	public void triggerGatewayConnected() {
		for (GatewayConnectedListener listener : gatewayConnectedListeners) {
			listener.onGatewayConnected();
		}
	}
	
	public static EventManager getInstance() {
		if (instance == null) {
			instance = new EventManager();
		}
		
		return instance;
	}
}
