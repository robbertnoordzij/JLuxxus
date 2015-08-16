package nl.robbertnoordzij.luxxus.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.events.ExceptionEvent;


public class Server implements Runnable {

	private EventManager eventManager = EventManager.getInstance();
	
	ServerSocket server;
	
	private volatile boolean stopped = false;
	
	private volatile RequestHandler requestHandler;
	
	public Server() {
		try {
			server = new ServerSocket(8080);
			new Thread(this).start();
		} catch (IOException e) {
			eventManager.trigger(new ExceptionEvent(e));
		}
	}

	public void run() {
		while (! stopped) {
			try {
				Socket socket = server.accept();
				new Thread(new Dispatcher(this.requestHandler, socket)).start();
			} catch (IOException e) {
				eventManager.trigger(new ExceptionEvent(e));
			}
		}
	}
	
	public void stop() {
		stopped = true;
	}
	
	public EventManager getEventManager() {
		return getEventManager();
	}
	
	public void requestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}
}
