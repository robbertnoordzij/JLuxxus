package nl.robbertnoordzij.luxxus;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalTime;

import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.events.ExceptionEvent;
import nl.robbertnoordzij.luxxus.events.events.GatewayConnectedEvent;
import nl.robbertnoordzij.luxxus.events.events.LampStateChangedEvent;
import nl.robbertnoordzij.luxxus.events.events.UdpPacketReceivedEvent;
import nl.robbertnoordzij.luxxus.events.listeners.UdpPackageReceivedListener;
import nl.robbertnoordzij.luxxus.requests.AbstractLuxxusRequest;
import nl.robbertnoordzij.luxxus.requests.GetLampsRequest;
import nl.robbertnoordzij.luxxus.requests.UpdateLampsRequest;

public class Client implements UdpPackageReceivedListener {

	private EventManager eventManager = EventManager.getInstance();
	
	private int portIn = 41328;
	
	private int portOut = 41330;
	
	private UdpClient udpClient;
	
	private InetAddress gateway;
	
	private int gatewayId;
	
	private boolean connected = false;
	
	private byte removedDevices = 0x00;
	
	private byte addedDevices = 0x00;
	
	private LocalTime lastCommunication = null;
	
	private long timeOut = 500;
	
	public Client() {
		
	}
	
	public EventManager getEventManager() {
		return eventManager;
	}
	
	public void connect() {
		udpClient = new UdpClient(portIn);
		udpClient.getEventManager().addUdpPacketReceivedListener(this);
		udpClient.connect();
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public void disconnect() {
		udpClient.disconnect();
		connected = false;
	}
	
	private void rateLimitRequests() {
		if (lastCommunication == null) {
			return;
		}
		
		Duration duration = Duration.between(lastCommunication, LocalTime.now());
		long sleep = timeOut - duration.toMillis();
			
		if (sleep > 0) {
			Utility.sleep(sleep);
		}
	}
	
	private synchronized void sendRequest(AbstractLuxxusRequest request) {
		rateLimitRequests();
		
		try {
			Socket tcpSocket = new Socket();
			
			tcpSocket.connect(new InetSocketAddress(gateway, portOut), 5000);
			tcpSocket.setSoTimeout(5000);
			
			tcpSocket.getOutputStream().write(request.getBytes());
			
			byte[] header = new byte[10];
			
			if (tcpSocket.getInputStream().read(header, 0, 10) == 10 && header[9] > 0) {
				byte[] bytes = new byte[header[9]];
				tcpSocket.getInputStream().read(bytes, 0, header[9]);
				
				request.setResponse(bytes);
			}
			
			lastCommunication = LocalTime.now();
			
			tcpSocket.close();
		} catch (IOException e) {
			eventManager.trigger(new ExceptionEvent(e));
			e.printStackTrace();
		}
	}
	
	public Lamp[] getLamps() {
		if (!connected) {
			return null;
		}
		
		GetLampsRequest request = new GetLampsRequest(gatewayId);
		sendRequest(request);
		byte[] data = request.getResponse();
		
		if (data == null) {
			return null;
		}
			
		Lamp[] lamps = new Lamp[data.length / 8];
		
		for (int i = 0; i < lamps.length; i++) {
			int offset = (i * 8);
			
			int deviceId = Utility.int32FromBytes(data, offset);

			int red = Utility.intFromByte(data[offset + 6]);
			int green = Utility.intFromByte(data[offset + 5]);
			int blue = Utility.intFromByte(data[offset + 4]);
			int intensity = Utility.intFromByte(data[offset + 7]);
			
			lamps[i] = new Lamp(deviceId, red, green, blue, intensity);
		}
		
		return lamps;
	}
	
	public void updateLamps(Lamp[] lamps) {
		UpdateLampsRequest request = new UpdateLampsRequest(lamps);
		sendRequest(request);
	}
	
	public void onUdpPackageReceived(UdpPacketReceivedEvent event) {
		DatagramPacket packet = event.getPacket();
		
		gateway = packet.getAddress();
		
		boolean removed = removedDevices != packet.getData()[21];
		boolean added = addedDevices != packet.getData()[22];
		
		if (connected && (removed || added)) {
			eventManager.trigger(new LampStateChangedEvent());
		}
		
		if (!connected) {
			connected = true;
			eventManager.trigger(new GatewayConnectedEvent());
		}
		
		removedDevices = packet.getData()[21];
		addedDevices = packet.getData()[22];
		
		gatewayId = Utility.int32FromBytes(packet.getData(), 2);
	}
	
}
