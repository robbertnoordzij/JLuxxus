package nl.robbertnoordzij.luxxus;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LampTest {

	private Lamp lamp;
	
	@Before
	public void setUp() {
		lamp = new Lamp(123123, 125, 0, 255, 35);
	}

	@Test
	public void testConstructor() {
		assertEquals("[123123, r=125, g=0, b=255, i=35]", lamp.toString());
	}

	@Test
	public void testGetSetIntensity() {
		lamp.setIntensity(255);
		assertEquals(255, lamp.getIntensity());
	}

	@Test
	public void testGetSetRed() {
		lamp.setRed(255);
		assertEquals(255, lamp.getRed());
	}

	@Test
	public void testGetSetGreen() {
		lamp.setGreen(255);
		assertEquals(255, lamp.getGreen());
	}

	@Test
	public void testGetSetBlue() {
		lamp.setBlue(12);
		assertEquals(12, lamp.getBlue());
	}
	
	@Test
	public void testGetSetRGB() {
		lamp.setRGB(0, 23, 123);
		assertEquals("[123123, r=0, g=23, b=123, i=35]", lamp.toString());
	}
	
	@Test
	public void testGetBytes() {
		byte[] expected = new byte[] {
			(byte) -13,
			(byte) -32,
			(byte) 1,
			(byte) 0,
			(byte) 35,
			(byte) 125,
			(byte) 0,
			(byte) 255
		};
		byte[] actual = lamp.getBytes();
		
		assertEquals(expected.length, actual.length);
		
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
	}
}
