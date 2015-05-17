package nl.robbertnoordzij.luxxus;

public class Utility {
	public static int int32FromBytes(byte[] bytes, int offset) {
		int result = (int) bytes[offset] & 0xff;
		result |= ((int) bytes[offset + 1] & 0xff) << 8;
		result |= ((int) bytes[offset + 2] & 0xff) << 16;
		result |= ((int) bytes[offset + 3] & 0xff) << 24;
		
		return result;
	}
}
