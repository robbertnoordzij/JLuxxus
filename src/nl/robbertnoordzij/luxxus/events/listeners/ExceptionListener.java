package nl.robbertnoordzij.luxxus.events.listeners;

import nl.robbertnoordzij.luxxus.events.events.ExceptionEvent;

public interface ExceptionListener extends EventListener {
	public void onException(ExceptionEvent event);
}
