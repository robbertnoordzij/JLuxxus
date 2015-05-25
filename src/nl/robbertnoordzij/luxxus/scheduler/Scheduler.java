package nl.robbertnoordzij.luxxus.scheduler;

import java.util.ArrayList;

import nl.robbertnoordzij.luxxus.LuxxusController;
import nl.robbertnoordzij.luxxus.LuxxusLamp;
import nl.robbertnoordzij.luxxus.Utility;
import nl.robbertnoordzij.luxxus.events.EventManager;
import nl.robbertnoordzij.luxxus.events.events.ScheduledTaskEvent;
import nl.robbertnoordzij.luxxus.events.listeners.ScheduledTaskListener;

public class Scheduler implements ScheduledTaskListener {
	private EventManager eventManager = EventManager.getInstance();
	
	private volatile ArrayList<Rule> rules = new ArrayList<Rule>();
	
	private LuxxusController controller;
	
	public Scheduler(LuxxusController controller) {
		this.controller = controller;
		
		eventManager.addScheduledTaskListener(this);
	}
	
	public EventManager getEventManager() {
		return eventManager;
	}
	
	public void addRule(Rule rule) {
		rules.add(rule);
	}
	
	public void onScheduledTask(ScheduledTaskEvent event) {
		LuxxusLamp[] lamps = controller.getLamps();
		event.getRule().execute(lamps);
		controller.updateLamps(lamps);
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
