package nl.robbertnoordzij.luxxus.events;

public interface UdpPackageReceivedListener extends EventListener {
	public void onUdpPackageReceived(UdpPacketReceivedEvent event);
}
