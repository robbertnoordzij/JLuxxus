package nl.robbertnoordzij.luxxus.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import nl.robbertnoordzij.luxxus.sun.Calculator;
import nl.robbertnoordzij.luxxus.sun.Location;

public class SunTimeRule extends AbstractRule {
	private Location location;
	
	private SunTime type;
	
	private LocalTime notBefore;
	
	private LocalTime notAfter;
	
	private long minutes;
	
	public SunTimeRule(Location location, SunTime type) {
		this.location = location;
		this.type = type;
	}
	
	public SunTimeRule notBefore(LocalTime notBefore) {
		this.notBefore = notBefore;
		
		return this;
	}
	
	public SunTimeRule notAfter(LocalTime notAfter) {
		this.notAfter = notAfter;
		
		return this;
	}
	
	public SunTimeRule offset(long minutes) {
		this.minutes = minutes;
		
		return this;
	}
	
	public boolean shouldExecute() {
		LocalTime now = LocalTime.now();
		
		Calculator calculator = new Calculator(LocalDate.now(), location);
		
		if (notBefore != null && now.isBefore(notBefore)) {
			return false;
		}
		
		if (notAfter != null && now.isAfter(notAfter)) {
			return false;
		}
		
		LocalDateTime time;
		
		if (type == SunTime.SUNRISE) {
			time = calculator.getSunRise();
		} else {
			time = calculator.getSunSet();
		}
		
		time = time.plus(minutes, ChronoUnit.MINUTES);
		
		return now.getHour() == time.getHour() && now.getMinute() == time.getMinute();
	}

}
