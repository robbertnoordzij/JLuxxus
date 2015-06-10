package nl.robbertnoordzij.luxxus.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import nl.robbertnoordzij.luxxus.sun.Calculator;
import nl.robbertnoordzij.luxxus.sun.Location;

public class SunTimeRule implements Rule {
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
	
	public LocalTime executeAt() {
		Calculator calculator = new Calculator(LocalDate.now(), location);
		
		if (notBefore != null && LocalTime.now().isBefore(notBefore)) {
			return null;
		}
		
		if (notAfter != null && LocalTime.now().isAfter(notAfter)) {
			return null;
		}
		
		LocalDateTime time;
		
		if (type == SunTime.SUNRISE) {
			time = calculator.getSunRise();
		} else {
			time = calculator.getSunSet();
		}
		
		time = time.plus(minutes, ChronoUnit.MINUTES);

		return time.toLocalTime();
	}
}
