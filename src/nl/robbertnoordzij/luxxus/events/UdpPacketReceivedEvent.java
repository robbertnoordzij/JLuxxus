package nl.robbertnoordzij.luxxus.events;

import java.net.DatagramPacket;

public class UdpPacketReceivedEvent {
	private DatagramPacket packet;
	
	public UdpPacketReceivedEvent(DatagramPacket packet) {
		this.packet = packet;
	}
	
	public DatagramPacket getPacket() {
		return packet;
	}
}
