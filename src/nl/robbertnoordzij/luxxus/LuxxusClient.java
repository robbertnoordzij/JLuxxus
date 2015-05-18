package nl.robbertnoordzij.luxxus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.UdpPackageReceivedListener;
import nl.robbertnoordzij.luxxus.events.UdpPacketReceivedEvent;

public class LuxxusClient implements UdpPackageReceivedListener {

	private EventManager eventManager = EventManager.getInstance();
	
	private Socket tcpSocket;
	
	private int portIn = 41328;
	
	private int portOut = 41330;
	
	private UdpClient udpClient;
	
	private InetAddress gateway;
	
	private int gatewayId;
	
	private boolean connected = false;
	
	private byte removedDevices = 0x00;
	
	private byte addedDevices = 0x00;
	
	public LuxxusClient() {
		
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
	
	public void disconnect() throws IOException {
		udpClient.disconnect();
		tcpSocket.close();
	}
	
	public LuxxusLamp[] getLamps() {
		if (!connected) {
			return null;
		}
		
		LuxxusLamp[] lamps = null;
		
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			buffer.write(0xf3);
			buffer.write(0xd4); // Query current state
		
			byte[] gatewayIdArray = ByteBuffer.allocate(4).putInt(gatewayId).array();
			buffer.write(gatewayIdArray);
			
			buffer.write(0x00);
			buffer.write(0x00);
			buffer.write(0x1d);
			buffer.write(0x05);
			buffer.write(0x00);
			buffer.write(0x00);
			buffer.write(0x00);
			buffer.write(0x43);
			buffer.write(0x00);
			
			tcpSocket.getOutputStream().write(buffer.toByteArray());
			
			byte[] header = new byte[10];
			if (tcpSocket.getInputStream().read(header, 0, 10) == 10) {

				byte[] data = new byte[header[9]];
				
				tcpSocket.getInputStream().read(data, 0, header[9]);
				
				lamps = new LuxxusLamp[data.length / 8];
				
				for (int i = 0; i < lamps.length; i++) {
					int offset = (i * 8);
					
					int deviceId = Utility.int32FromBytes(data, offset);
					
					int red = data[offset + 6] & 0xff;
					int green = data[offset + 5] & 0xff;
					int blue = data[offset + 4] & 0xff;
					int intensity = data[offset + 7] & 0xff;
					
					LuxxusLamp lamp = new LuxxusLamp(deviceId, red, green, blue, intensity);
					lamps[i] = lamp;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lamps;
	}
	
	public void updateLamps(LuxxusLamp[] lamps) {
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			buffer.write(0xf2);
			buffer.write(0xc2); // Light control
			
			buffer.write(0xff);
			buffer.write(0xff);
			buffer.write(0xff);
			buffer.write(0xff);
	
			buffer.write(0x00);
			buffer.write(0x00);
			buffer.write(0x1d);
	
	        int length = lamps.length * 8 + 4;
	        buffer.write((byte)length); //data length
	
	        buffer.write(0x00);
	        buffer.write(0x00);
	        buffer.write(0x00);
	        buffer.write(0x43);
		
			for (LuxxusLamp lamp : lamps) {
				buffer.write(lamp.getBytes());
			}
			
			tcpSocket.getOutputStream().write(buffer.toByteArray());
			
			byte[] header = new byte[10];
			if (tcpSocket.getInputStream().read(header, 0, 10) == 10 && header[9] == 0) {
				eventManager.triggerLampStateChanged();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onUdpPackageReceived(UdpPacketReceivedEvent event) {
		System.out.println("Udp Packet received...");
		
		DatagramPacket packet = event.getPacket();
		
		gateway = packet.getAddress();
		
		boolean removed = removedDevices != packet.getData()[21];
		boolean added = addedDevices != packet.getData()[22];
		
		if (connected && (removed || added)) {
			eventManager.triggerLampStateChanged();
		}
		
		if (!connected) {
			connectTcpSocket();
		}
		
		removedDevices = packet.getData()[21];
		addedDevices = packet.getData()[22];
		
		gatewayId = Utility.int32FromBytes(packet.getData(), 2);
	}
	
	private void connectTcpSocket() {
		try {
			tcpSocket = new Socket(gateway, portOut);
			connected = true;
			
			eventManager.triggerGatewayConnected();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
