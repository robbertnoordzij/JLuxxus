package nl.robbertnoordzij.luxxus.scheduler;

import java.time.LocalTime;

public class SimpleRule extends AbstractRule {

	private LocalTime time;
	
	public SimpleRule at(LocalTime time) {
		this.time = time;
		
		return this;
	}
	
	public boolean shouldExecute() {
		LocalTime now = LocalTime.now();
		
		return now.getHour() == time.getHour() && now.getMinute() == time.getMinute();
	}
}
