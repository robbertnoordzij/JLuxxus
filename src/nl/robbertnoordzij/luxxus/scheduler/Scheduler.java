package nl.robbertnoordzij.luxxus.scheduler;

import java.util.ArrayList;

import nl.robbertnoordzij.luxxus.Utility;
import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.events.ScheduledTaskEvent;

public class Scheduler {
	private EventManager eventManager = EventManager.getInstance();
	
	private volatile ArrayList<Rule> rules = new ArrayList<Rule>();
	
	public EventManager getEventManager() {
		return eventManager;
	}
	
	public void addRule(Rule rule) {
		rules.add(rule);
	}
	
	public void start() {
		new Thread(() -> {
			while (true) {
				for (Rule rule : rules) {
					if (rule.shouldExecute()) {
						eventManager.trigger(new ScheduledTaskEvent(rule));
					}
				}
				Utility.sleep(60 * 1000);
			}
		}).start();
	}
}
