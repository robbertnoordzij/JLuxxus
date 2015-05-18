package nl.robbertnoordzij.luxxus;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class LuxxusLamp {
	private int deviceId;
	
	private int red;
	
	private int green;
	
	private int blue;
	
	private int intensity;
	
	public LuxxusLamp(int deviceId, int red, int green, int blue, int intensity) {
		this.deviceId = deviceId;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.intensity = intensity;
	}
	
	public int getIntensity() {
		return intensity;
	}
	
	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}
	
	public void setRGB(int red, int green, int blue, int intensity) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.intensity = intensity;
	}
	
	public String toString() {
		return String.format("[%d, r=%d, g=%d, b=%d, i=%d]", deviceId, red, green, blue, intensity);
	}
	
	public byte[] getBytes() {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		
		byte[] id = ByteBuffer.allocate(4).putInt(deviceId).array();
		
		for (int i = 0; i < id.length; i++) {
			buffer.write(id[id.length - i - 1]);
		}

		buffer.write((byte) intensity);
		buffer.write((byte) red);
		buffer.write((byte) green);
		buffer.write((byte) blue);
		
		return buffer.toByteArray();
	}
}
