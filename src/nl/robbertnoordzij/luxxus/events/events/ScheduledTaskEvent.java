package nl.robbertnoordzij.luxxus.events.events;

import nl.robbertnoordzij.luxxus.scheduler.Rule;

public class ScheduledTaskEvent extends AbstractEvent {
	private Rule rule;
	
	public ScheduledTaskEvent(Rule rule) {
		this.rule = rule;
	}
	
	public Rule getRule() {
		return rule;
	}
}
