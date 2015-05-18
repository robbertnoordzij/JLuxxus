package nl.robbertnoordzij.luxxus.events;

import java.util.ArrayList;

public class EventManager {
	private static EventManager instance;
	
	private ArrayList<GatewayConnectedListener> gatewayConnectedListeners = new ArrayList<GatewayConnectedListener>();
	private ArrayList<LampStateChangedListener> lampStateChangedListeners = new ArrayList<LampStateChangedListener>();
	private ArrayList<UdpPackageReceivedListener> udpPackageReceivedListeners = new ArrayList<UdpPackageReceivedListener>();
	
	private EventManager () {
		
	}
	
	public void addLampStateChangedListener(LampStateChangedListener listener) {
		if (!lampStateChangedListeners.contains(listener)) {
			lampStateChangedListeners.add(listener);
		}
	}

	public void addGatewayConnectedListener(GatewayConnectedListener listener) {
		if (!gatewayConnectedListeners.contains(listener)) {
			gatewayConnectedListeners.add(listener);
		}
	}
	
	public void addUdpPacketReceivedListener(UdpPackageReceivedListener listener) {
		if (!udpPackageReceivedListeners.contains(listener)) {
			udpPackageReceivedListeners.add(listener);
		}
	}
	
	public void triggerLampStateChanged() {
		for (LampStateChangedListener listener : lampStateChangedListeners) {
			listener.onLampStateChanged();
		}
	}
	
	public void triggerGatewayConnected() {
		for (GatewayConnectedListener listener : gatewayConnectedListeners) {
			listener.onGatewayConnected();
		}
	}
	
	public void triggerUdpPacketReceived(UdpPacketReceivedEvent event) {
		for (UdpPackageReceivedListener listener : udpPackageReceivedListeners) {
			listener.onUdpPackageReceived(event);
		}
	}
	
	public static EventManager getInstance() {
		if (instance == null) {
			instance = new EventManager();
		}
		
		return instance;
	}
}
