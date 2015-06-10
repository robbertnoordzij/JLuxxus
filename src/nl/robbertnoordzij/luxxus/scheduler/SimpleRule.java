package nl.robbertnoordzij.luxxus.scheduler;

import java.time.LocalTime;

public class SimpleRule implements Rule {

	private LocalTime time;
	
	public SimpleRule at(LocalTime time) {
		this.time = time;
		
		return this;
	}
	
	public LocalTime executeAt() {
		return time;
	}
}
