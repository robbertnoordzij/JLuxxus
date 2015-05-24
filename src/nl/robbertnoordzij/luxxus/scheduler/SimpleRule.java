package nl.robbertnoordzij.luxxus.scheduler;

import java.util.Calendar;

import nl.robbertnoordzij.luxxus.LuxxusLamp;

public class SimpleRule extends AbstractRule {

	private int hour;
	
	private int minute;
	
	public SimpleRule(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}
	
	public boolean shouldExecute() {
		Calendar now = Calendar.getInstance();
		
		return now.get(Calendar.HOUR_OF_DAY) == hour && now.get(Calendar.MINUTE) == minute;
	}
}
