package nl.robbertnoordzij.luxxus;

import java.util.List;

public class Utility {
	public static int int32FromBytes(byte[] bytes, int offset) {
		int result = intFromByte(bytes[offset]);
		result |= intFromByte(bytes[offset + 1]) << 8;
		result |= intFromByte(bytes[offset + 2]) << 16;
		result |= intFromByte(bytes[offset + 3]) << 24;
		
		return result;
	}
	
	public static int intFromByte(byte b) {
		return (int) (b & 0xff);
	}
	
	public static void sleep(long sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
	}
	
	public static String debugByte(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		
	    for (byte b : bytes) {
	        sb.append(String.format("%02X ", b));
	    }
	    
	    return sb.toString();
	}
	
	public static String implode(List<String> list, String seperator) {
		StringBuilder sb = new StringBuilder();
		
		for (String string : list) {
			if ((sb.length() != 0)) {
				sb.append(seperator);
			}
			sb.append(string);
		}
		
		return sb.toString();
	}
}
