package nl.robbertnoordzij.luxxus.events;

import nl.robbertnoordzij.luxxus.events.listeners.EventListener;

public interface Callable<L extends EventListener> {
	public void call(L listener);
}
