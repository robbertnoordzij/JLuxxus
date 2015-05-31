package nl.robbertnoordzij.luxxus.requests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import nl.robbertnoordzij.luxxus.Lamp;

public class UpdateLampsRequest extends AbstractLuxxusRequest {

	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	
	public UpdateLampsRequest(Lamp[] lamps) {
		try {
			buffer.write(new byte[] {
					(byte) 0xf2,
					(byte) 0xc2, // Light control
					(byte) 0xff,
					(byte) 0xff,
					(byte) 0xff,
					(byte) 0xff,
					0x00,
					0x00,
					0x1d,
			});
			
			int length = lamps.length * 8 + 4;
	        buffer.write((byte)length); //data length
	        
	        buffer.write(new byte[] {
	        		0x00,
	        		0x00,
	        		0x00,
	        		0x43
	        });

			for (Lamp lamp : lamps) {
				buffer.write(lamp.getBytes());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] getBytes() {
		return buffer.toByteArray();
	}

}
