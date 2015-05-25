package nl.robbertnoordzij.luxxus.sun;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZonedDateTime;
import java.time.temporal.JulianFields;

public class Calculator {
	
	LocalDate date;
	
	Location location;
	
	private final static double JULIAN_2000 = 2451545;
	
	private final static double JULIAN_CORRECTION = JULIAN_2000 + 0.0009;
	
	public Calculator(int year, int month, int dayOfMonth, Location location) {
		this(year, Month.of(month), dayOfMonth, location);
	}
	
	public Calculator(int year, Month month, int dayOfMonth, Location location) {
		this(LocalDate.of(year, month, dayOfMonth), location);
	}
	
	public Calculator(LocalDate date, Location location) {
		this.date = date;
		this.location = location;
	}
	
	public LocalDateTime getSunRise() {
		return toDateTime(getJulianSunRise());
	}
	
	public LocalDateTime getSunSet() {
		return toDateTime(getJulianSunSet());
	}
	
	private LocalDateTime toDateTime(double time) {
		double fraction = time - 0.5 - Math.floor(time - 0.5);
		
		double precision = 1000;
		fraction = Math.round(fraction * precision) / precision;
		
		int hours = (int) (24 * fraction);
		int minutes = (int) ((fraction - (double) hours / 24) * 1440);
		int seconds = (int) ((fraction - (double) hours / 24 - (double) minutes / 1440) * 86400);
		
		int offset = ZonedDateTime.now().getOffset().getTotalSeconds();
		
		return LocalDateTime.of(date, LocalTime.of(hours + (offset / 3600), minutes, seconds));
	}
	
	private double getCurrentJulianCycle() {
		return Math.round(date.getLong(JulianFields.JULIAN_DAY) - JULIAN_CORRECTION - location.getLongitude() / 360 + 0.5);
	}
	
	private double getSolarNoon() {
		return Math.round(JULIAN_CORRECTION + (location.getLongitude() / 360) + getCurrentJulianCycle());
	}
	
	private double getSolarMeanAnomaly() {
		return (357.5291 + 0.98560028 * (getSolarNoon() - JULIAN_2000)) % 360;
	}
	
	private double getEquationOfCenter() {
		double M = getSolarMeanAnomaly();
		
		return 1.9148 * Math.sin(Math.toRadians(M)) + 0.0200 * Math.sin(Math.toRadians(2  * M)) + 0.0003 * Math.sin(Math.toRadians(3 * M));
	}
	
	private double getEclipticLongitude() {
		return (getSolarMeanAnomaly() + 102.9372 + getEquationOfCenter() + 180) % 360;
	}
	
	private double getTransit() {
		return getSolarNoon() + 0.0053 * Math.sin(Math.toRadians(getSolarMeanAnomaly())) - 0.0069 * Math.sin(Math.toRadians(2 * getEclipticLongitude()));
	}
	
	private double getDeclinationOfTheSun() {
		return Math.asin(Math.sin(Math.toRadians(getEclipticLongitude())) * Math.sin(Math.toRadians(23.45)));
	}
	
	private double getHourAngle() {
		double d = getDeclinationOfTheSun();
		
		double a = Math.sin(Math.toRadians(-0.83)) - Math.sin(Math.toRadians(location.getLatitude())) * Math.sin(d);
		double b = Math.cos(Math.toRadians(location.getLatitude())) * Math.cos(d);
		
		return Math.toDegrees(Math.acos(a / b));
	}
	
	private double getJulianSunSet() {
		return (getTransit() + getHourAngle()/360);
	}
	

	private double getJulianSunRise() {
		return (getTransit() - getHourAngle()/360);
	}
}
