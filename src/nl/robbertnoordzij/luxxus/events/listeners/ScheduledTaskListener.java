package nl.robbertnoordzij.luxxus.events.listeners;

import nl.robbertnoordzij.luxxus.events.events.ScheduledTaskEvent;

public interface ScheduledTaskListener extends EventListener {
	public void onScheduledTask(ScheduledTaskEvent event);
}
