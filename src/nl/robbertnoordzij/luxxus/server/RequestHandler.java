package nl.robbertnoordzij.luxxus.server;

public interface RequestHandler {
	public void handle(Request request, Response response);
}
