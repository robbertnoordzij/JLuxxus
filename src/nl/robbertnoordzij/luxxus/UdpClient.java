package nl.robbertnoordzij.luxxus;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.events.ExceptionEvent;
import nl.robbertnoordzij.luxxus.events.events.UdpPacketReceivedEvent;

public class UdpClient {
	
	private EventManager eventManager = EventManager.getInstance();
	
	private int portIn;
	
	private DatagramSocket udpSocket;
	
	private ListenThread listenThread = new ListenThread();

	public UdpClient(int portIn) {
		this.portIn = portIn;
	}
	
	public EventManager getEventManager() {
		return eventManager;
	}
	
	public void connect() {
		try {
			udpSocket = new DatagramSocket(portIn);
			udpSocket.setBroadcast(true);
			
			listenThread.start();
		} catch (SocketException e) {
			eventManager.trigger(new ExceptionEvent(e));
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		listenThread.stopListening();
		udpSocket.disconnect();
	}

	private void processUdpPacket(DatagramPacket packet) {
		eventManager.trigger(new UdpPacketReceivedEvent(packet));
	}
	
	private class ListenThread extends Thread {
		
		private volatile boolean listen = true;
		
		@Override
		public void run() {
			byte[] buffer = new byte[2048];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			while (listen) {
				try {
					udpSocket.receive(packet);
					processUdpPacket(packet);
				} catch (IOException e) {
					eventManager.trigger(new ExceptionEvent(e));
				}
			}
		}
		
		public void stopListening() {
			listen = false;
		}
		
	}
}
