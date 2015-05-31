package nl.robbertnoordzij.luxxus.scheduler;

import java.time.LocalTime;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import nl.robbertnoordzij.luxxus.Utility;
import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.events.ScheduledTaskEvent;
import nl.robbertnoordzij.luxxus.events.listeners.ScheduledTaskListener;

public class Scheduler implements ScheduledTaskListener {
	private EventManager eventManager = EventManager.getInstance();
	
	private volatile ConcurrentHashMap<Rule, Task> tasks = new ConcurrentHashMap<Rule, Task>();
	
	public Scheduler() {
		eventManager.addScheduledTaskListener(this);
	}
	
	public EventManager getEventManager() {
		return eventManager;
	}
	
	public void addTask(Rule rule, Task task) {
		tasks.put(rule, task);
	}
	
	public void onScheduledTask(ScheduledTaskEvent event) {
		event.getTask().execute();
	}
	
	public void start() {
		new SchedulerThread().start();
	}
	
	private class SchedulerThread extends Thread {

		@Override
		public void run() {
			while (true) {
				LocalTime currentTime = LocalTime.now();
				
				for(Entry<Rule, Task> entry : tasks.entrySet()) {
					Rule rule = entry.getKey();
					Task task = entry.getValue();
					
					if (!rule.shouldExecute(currentTime)) {
						continue;
					}
					
					eventManager.trigger(new ScheduledTaskEvent(task));
				}
				
				Utility.sleep(60 * 1000);
			}
		}
	}
}
