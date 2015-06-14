package nl.robbertnoordzij.luxxus.events;

import java.util.ArrayList;

import nl.robbertnoordzij.luxxus.events.events.AbstractEvent;
import nl.robbertnoordzij.luxxus.events.listeners.EventListener;

public class EventHandler <L extends EventListener> {

	private ArrayList<L> listeners = new ArrayList<L>();
	
	public synchronized void addListener(L listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	public synchronized void trigger(AbstractEvent event, Callable<L> callable) {
		new Thread(() -> {
			for (L listener : listeners) {
				callable.call(listener);
				
				if (event.isStopped()) {
					break;
				}
			}
		}).start();
	}
}
