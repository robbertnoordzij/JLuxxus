package nl.robbertnoordzij.luxxus.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.events.ExceptionEvent;

public class Dispatcher implements Runnable {

	private EventManager eventManager = EventManager.getInstance();
	
	private RequestHandler handler;
	
	private Socket client;
	
	public Dispatcher (RequestHandler handler, Socket client) {
		this.handler = handler;
		this.client = client;
	}
	
	public void run() {
		try {
			InputStream input  = client.getInputStream();
            OutputStream output = client.getOutputStream();
            
            Request request = new Request(input);
            Response response = new Response();
            
            if (handler != null) {
            	handler.handle(request, response);
            }
            
            response.writeTo(output);
            
            output.close();
            input.close();
            client.close();
		} catch (IOException e) {
			e.printStackTrace();
			eventManager.trigger(new ExceptionEvent(e));
		}
	}

}
