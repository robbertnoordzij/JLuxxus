package nl.robbertnoordzij.luxxus.events;

import java.util.ArrayList;

import nl.robbertnoordzij.luxxus.events.events.GatewayConnectedEvent;
import nl.robbertnoordzij.luxxus.events.events.LampStateChangedEvent;
import nl.robbertnoordzij.luxxus.events.events.UdpPacketReceivedEvent;
import nl.robbertnoordzij.luxxus.events.listeners.GatewayConnectedListener;
import nl.robbertnoordzij.luxxus.events.listeners.LampStateChangedListener;
import nl.robbertnoordzij.luxxus.events.listeners.UdpPackageReceivedListener;

public class EventManager {
	private static EventManager instance = new EventManager();

	private EventHandler<LampStateChangedListener> lampStateChanged = new EventHandler<LampStateChangedListener>();
	private EventHandler<GatewayConnectedListener> gatewayConnected = new EventHandler<GatewayConnectedListener>();
	private EventHandler<UdpPackageReceivedListener> udpPackageReceived = new EventHandler<UdpPackageReceivedListener>();
	
	private EventManager () {
		
	}
	
	public void addLampStateChangedListener(LampStateChangedListener listener) {
		lampStateChanged.addListener(listener);
	}

	public void addGatewayConnectedListener(GatewayConnectedListener listener) {
		gatewayConnected.addListener(listener);
	}
	
	public void addUdpPacketReceivedListener(UdpPackageReceivedListener listener) {
		udpPackageReceived.addListener(listener);
	}
	
	public void triggerLampStateChanged(LampStateChangedEvent event) {
		lampStateChanged.trigger(event, (listener) -> {
			listener.onLampStateChanged(event);
		});
	}
	
	public void triggerGatewayConnected(GatewayConnectedEvent event) {
		gatewayConnected.trigger(event, (listener) -> {
			listener.onGatewayConnected(event);
		});
	}
	
	public void triggerUdpPacketReceived(UdpPacketReceivedEvent event) {
		udpPackageReceived.trigger(event, (listener) -> {
			listener.onUdpPackageReceived(event);
		});
	}
	
	public static EventManager getInstance() {
		return instance;
	}
}
