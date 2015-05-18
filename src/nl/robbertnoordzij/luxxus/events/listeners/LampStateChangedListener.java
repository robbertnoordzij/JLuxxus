package nl.robbertnoordzij.luxxus.events.listeners;

import nl.robbertnoordzij.luxxus.events.events.LampStateChangedEvent;


public interface LampStateChangedListener extends EventListener {
	public void onLampStateChanged(LampStateChangedEvent event);
}
