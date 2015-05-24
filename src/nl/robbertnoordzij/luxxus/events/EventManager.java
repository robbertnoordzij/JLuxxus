package nl.robbertnoordzij.luxxus.events;

import nl.robbertnoordzij.luxxus.events.events.ExceptionEvent;
import nl.robbertnoordzij.luxxus.events.events.GatewayConnectedEvent;
import nl.robbertnoordzij.luxxus.events.events.LampStateChangedEvent;
import nl.robbertnoordzij.luxxus.events.events.ScheduledTaskEvent;
import nl.robbertnoordzij.luxxus.events.events.UdpPacketReceivedEvent;
import nl.robbertnoordzij.luxxus.events.listeners.ExceptionListener;
import nl.robbertnoordzij.luxxus.events.listeners.GatewayConnectedListener;
import nl.robbertnoordzij.luxxus.events.listeners.LampStateChangedListener;
import nl.robbertnoordzij.luxxus.events.listeners.ScheduledTaskListener;
import nl.robbertnoordzij.luxxus.events.listeners.UdpPackageReceivedListener;

public class EventManager {
	private static EventManager instance = new EventManager();

	private EventHandler<LampStateChangedListener> lampStateChanged = new EventHandler<LampStateChangedListener>();
	private EventHandler<GatewayConnectedListener> gatewayConnected = new EventHandler<GatewayConnectedListener>();
	private EventHandler<UdpPackageReceivedListener> udpPackageReceived = new EventHandler<UdpPackageReceivedListener>();
	private EventHandler<ScheduledTaskListener> scheduledTask = new EventHandler<ScheduledTaskListener>();
	private EventHandler<ExceptionListener> exception = new EventHandler<ExceptionListener>();
	
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
	
	public void addScheduledTaskListener(ScheduledTaskListener listener) {
		scheduledTask.addListener(listener);
	}

	public void addExceptionListener(ExceptionListener listener) {
		exception.addListener(listener);
	}
	
	public void trigger(LampStateChangedEvent event) {
		lampStateChanged.trigger(event, listener -> listener.onLampStateChanged(event));
	}
	
	public void trigger(GatewayConnectedEvent event) {
		gatewayConnected.trigger(event, listener -> listener.onGatewayConnected(event));
	}
	
	public void trigger(UdpPacketReceivedEvent event) {
		udpPackageReceived.trigger(event, listener -> listener.onUdpPackageReceived(event));
	}
	
	public void trigger(ScheduledTaskEvent event) {
		scheduledTask.trigger(event, listener -> listener.onScheduledTask(event));
	}
	
	public void trigger(ExceptionEvent event) {
		exception.trigger(event, listener -> listener.onException(event));
	}
	
	public static EventManager getInstance() {
		return instance;
	}
}
