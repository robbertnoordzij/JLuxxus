package nl.robbertnoordzij.luxxus;

public class Utility {
	public static int int32FromBytes(byte[] bytes, int offset) {
		int result = intFromByte(bytes[offset]);
		result |= intFromByte(bytes[offset + 1]) << 8;
		result |= intFromByte(bytes[offset + 2]) << 16;
		result |= intFromByte(bytes[offset + 3]) << 24;
		
		return result;
	}
	
	public static int intFromByte(byte b) {
		return (int) b & 0xff;
	}
	
	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) { }
	}
}
