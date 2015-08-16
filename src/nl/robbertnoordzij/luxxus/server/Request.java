package nl.robbertnoordzij.luxxus.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Request {
	
	private String request;
	
	private String method = "GET";
	
	private String path = "/";

	public Request (InputStream input) throws IOException {
		StringBuilder builder = new StringBuilder();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		
		String line = reader.readLine();
		
		while (line != null && !line.equals("")) {
			builder.append(line).append("\n");
			line = reader.readLine();
		}
		
		request = builder.toString();
		
		parseRequest();
	}
	
	private void parseRequest() {
		String line = request.split("\\n")[0];
		
		String[] requestParts = line.split("\\s");
		
		if (requestParts.length != 3) {
			return;
		}
		
		method = requestParts[0];
		path = requestParts[1];
	}
	
	public String getPath() {
		return path;
	}
	
	public boolean isGet() {
		return method.equals("GET");
	}
	
	public boolean isPost() {
		return method.equals("POST");
	}
}
