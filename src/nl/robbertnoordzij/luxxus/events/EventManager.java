package nl.robbertnoordzij.luxxus.events;

import java.util.ArrayList;

import nl.robbertnoordzij.luxxus.events.events.Event;
import nl.robbertnoordzij.luxxus.events.events.GatewayConnectedEvent;
import nl.robbertnoordzij.luxxus.events.events.LampStateChangedEvent;
import nl.robbertnoordzij.luxxus.events.events.UdpPacketReceivedEvent;
import nl.robbertnoordzij.luxxus.events.listeners.EventListener;
import nl.robbertnoordzij.luxxus.events.listeners.GatewayConnectedListener;
import nl.robbertnoordzij.luxxus.events.listeners.LampStateChangedListener;
import nl.robbertnoordzij.luxxus.events.listeners.UdpPackageReceivedListener;

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
	
	public void triggerLampStateChanged(LampStateChangedEvent event) {
		for (LampStateChangedListener listener : lampStateChangedListeners) {
			listener.onLampStateChanged(event);
			
			if (event.isStopped()) {
				break;
			}
		}
	}
	
	public void triggerGatewayConnected(GatewayConnectedEvent event) {
		for (GatewayConnectedListener listener : gatewayConnectedListeners) {
			listener.onGatewayConnected(event);
			
			if (event.isStopped()) {
				break;
			}
		}
	}
	
	public void triggerUdpPacketReceived(UdpPacketReceivedEvent event) {
		for (UdpPackageReceivedListener listener : udpPackageReceivedListeners) {
			listener.onUdpPackageReceived(event);
			
			if (event.isStopped()) {
				break;
			}
		}
	}
	
	public static EventManager getInstance() {
		if (instance == null) {
			instance = new EventManager();
		}
		
		return instance;
	}
}
