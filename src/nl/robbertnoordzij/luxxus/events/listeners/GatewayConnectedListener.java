package nl.robbertnoordzij.luxxus.events.listeners;

import nl.robbertnoordzij.luxxus.events.events.GatewayConnectedEvent;

public interface GatewayConnectedListener extends EventListener {
	public void onGatewayConnected(GatewayConnectedEvent event);
}
