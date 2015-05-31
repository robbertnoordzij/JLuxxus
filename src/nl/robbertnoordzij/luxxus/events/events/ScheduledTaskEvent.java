package nl.robbertnoordzij.luxxus.events.events;

import nl.robbertnoordzij.luxxus.scheduler.Task;

public class ScheduledTaskEvent extends AbstractEvent {
	private Task task;
	
	public ScheduledTaskEvent(Task task) {
		this.task = task;
	}
	
	public Task getTask() {
		return task;
	}
}
