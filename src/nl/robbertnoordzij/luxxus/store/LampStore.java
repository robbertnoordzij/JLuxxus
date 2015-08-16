package nl.robbertnoordzij.luxxus.store;

import java.util.HashMap;

public class LampStore {

	private HashMap<String, Integer> namedLamps = new HashMap<String, Integer>();
	
	public LampStore() {
		namedLamps.put("front_left", 1680764563);
		namedLamps.put("front_right", 1680764191);
		namedLamps.put("back_left", 1661865015);
		namedLamps.put("back_right", 1661867169);
	}
	
	public int get(String name) {
		return namedLamps.get(name);
	}
	
}
