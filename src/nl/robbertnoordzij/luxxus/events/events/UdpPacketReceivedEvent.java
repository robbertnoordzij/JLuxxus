package nl.robbertnoordzij.luxxus.events.events;

import java.net.DatagramPacket;

public class UdpPacketReceivedEvent extends AbstractEvent {
	private DatagramPacket packet;
	
	public UdpPacketReceivedEvent(DatagramPacket packet) {
		this.packet = packet;
	}
	
	public DatagramPacket getPacket() {
		return packet;
	}
}
