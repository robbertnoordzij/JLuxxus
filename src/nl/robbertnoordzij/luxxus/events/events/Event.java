package nl.robbertnoordzij.luxxus.events.events;

public abstract class Event {
	private boolean stopped = false;
	
	public void stopPropagation() {
		stopped = true;
	}
	
	public boolean isStopped() {
		return stopped;
	}
}
