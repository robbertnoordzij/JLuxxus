package nl.robbertnoordzij.luxxus;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilityTest {

	@Test
	public void testIntFromByte() {
		assertEquals(255, Utility.intFromByte((byte) 0xFF));
		assertEquals(242, Utility.intFromByte((byte) 0xF2));
		assertEquals(15, Utility.intFromByte((byte) 0x0F));
		assertEquals(0, Utility.intFromByte((byte) 0x00));
	}
	
	@Test
	public void testInt32FromBytes() {
		byte[] bytes = new byte[] { 0x0F, 0x43, (byte) 0xF2, 0x32, 0x12, 0x00 };
		
		assertEquals(854737679, Utility.int32FromBytes(bytes, 0));
		assertEquals(305328707, Utility.int32FromBytes(bytes, 1));
		assertEquals(1192690, Utility.int32FromBytes(bytes, 2));
	}

}
