package nl.robbertnoordzij.luxxus.requests;

public abstract class AbstractLuxxusRequest implements LuxxusRequest {
	private byte[] response;
	
	public void setResponse(byte[] bytes) {
		response = bytes;
	}
	
	public byte[] getResponse() {
		return response;
	}
}
