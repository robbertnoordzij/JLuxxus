package nl.robbertnoordzij.luxxus.scheduler;

import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.events.ScheduledTaskEvent;
import nl.robbertnoordzij.luxxus.events.listeners.ScheduledTaskListener;

public class Scheduler implements ScheduledTaskListener {
	private EventManager eventManager = EventManager.getInstance();
	
	private volatile ConcurrentHashMap<Rule, Task> tasks = new ConcurrentHashMap<Rule, Task>();
	
	private Timer timer = new Timer();
	
	public Scheduler() {
		eventManager.addScheduledTaskListener(this);
	}
	
	public EventManager getEventManager() {
		return eventManager;
	}
	
	public void addTask(Rule rule, Task task) {
		if (rule == null || task == null) {
			throw new InvalidParameterException();
		}
		
		tasks.put(rule, task);
	}
	
	public void onScheduledTask(ScheduledTaskEvent event) {
		event.getTask().execute();
	}
	
	public void start() {
		timer.scheduleAtFixedRate(new SchedulerTask(), 0, 60 * 1000);
	}
	
	private class SchedulerTask extends TimerTask {
		@Override
		public void run() {
			LocalTime currentTime = LocalTime.now();
			
			for(Entry<Rule, Task> entry : tasks.entrySet()) {
				Rule rule = entry.getKey();
				Task task = entry.getValue();
				
				LocalTime executeAt = rule.executeAt();
				if (executeAt == null) {
					continue;
				}
				
				boolean execute = executeAt.getMinute() == currentTime.getMinute() 
						&& executeAt.getHour() == currentTime.getHour();
				
				if (!execute) {
					continue;
				}

				eventManager.trigger(new ScheduledTaskEvent(task));
			}
		}
	}
}
