package nl.robbertnoordzij.luxxus.store;

import java.time.LocalTime;
import java.util.HashMap;

import nl.robbertnoordzij.luxxus.scheduler.Rule;
import nl.robbertnoordzij.luxxus.scheduler.SimpleRule;
import nl.robbertnoordzij.luxxus.scheduler.SunTime;
import nl.robbertnoordzij.luxxus.scheduler.SunTimeRule;
import nl.robbertnoordzij.luxxus.sun.Location;

public class RuleStore {

	private HashMap<String, Rule> namedRules = new HashMap<String, Rule>();
	
	public RuleStore() {
		// Central Station Rotterdam
		Location location = new Location(-4.469444, 51.925);
		
		Rule beforeSunSet = new SunTimeRule(location, SunTime.SUNSET).offset(-45).notAfter(LocalTime.of(22, 59));
		namedRules.put("before_sunset", beforeSunSet);
		
		Rule afterSunSet = new SunTimeRule(location, SunTime.SUNSET).offset(+30)
				.notBefore(LocalTime.of(21, 30))
				.notAfter(LocalTime.of(22, 59));
		namedRules.put("after_sunset", afterSunSet);
		
		Rule atNight = new SimpleRule().at(LocalTime.of(23, 00));
		namedRules.put("at_night", atNight);
	}
	
	public Rule get(String name) {
		return namedRules.get(name);
	}
	
	public HashMap<String, Rule> getRules() {
		return namedRules;
	}
}
