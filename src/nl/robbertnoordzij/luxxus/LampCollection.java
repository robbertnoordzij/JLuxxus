package nl.robbertnoordzij.luxxus;

import java.util.Iterator;

public class LampCollection implements Iterable<Lamp>, Iterator<Lamp> {

	private Lamp[] lamps;
	
	private int current = 0;
	
	public LampCollection(Lamp[] lamps) {
		this.lamps = lamps;
	}
	
	public void reset() {
		current = 0;
	}
	
	public boolean isEmpty() {
		return lamps.length == 0;
	}
	
	public Lamp[] getRaw() {
		return lamps;
	}
	
	public Lamp at(int i) {
		return lamps[i];
	}
	
	public int getLength() {
		return lamps.length;
	}

	@Override
	public Iterator<Lamp> iterator() {
		LampCollection itarable =  new LampCollection(lamps);
		
		itarable.reset();
		
		return itarable;
	}

	@Override
	public boolean hasNext() {
		return current < lamps.length;
	}

	@Override
	public Lamp next() {
		return lamps[current++];
	}
}
