package nl.robbertnoordzij.luxxus.server;

import java.io.IOException;
import java.io.OutputStream;

public class Response {

	private String status = "200 Ok";
	
	private String content = "Ok";
	
	public void writeTo(OutputStream output) throws IOException {
		 output.write(("HTTP/1.1 " + status + "\r\nConnection: close\r\nContent-type: application/json\r\n\r\n" + content).getBytes());
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
