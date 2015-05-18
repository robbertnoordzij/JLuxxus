package nl.robbertnoordzij.luxxus.events.listeners;

import nl.robbertnoordzij.luxxus.events.events.UdpPacketReceivedEvent;

public interface UdpPackageReceivedListener extends EventListener {
	public void onUdpPackageReceived(UdpPacketReceivedEvent event);
}
