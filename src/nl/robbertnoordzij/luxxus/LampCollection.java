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
	
	public LampCollection copy() {
		Lamp[] copy = new Lamp[lamps.length];
		
		for (int i = 0; i < lamps.length; i++) {
			copy[i] = new Lamp(lamps[i]);
		}
		
		return new LampCollection(copy);
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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Lamp lamp : lamps) {
			sb.append(lamp);
			sb.append(", ");
		}
		return sb.toString();
	}
	
	public boolean equals(Object object) {
		if (! (object instanceof LampCollection)) {
			return false;
		}
		
		LampCollection other = (LampCollection) object;
		
		for (int i = 0; i < lamps.length; i++) {
			if (!lamps[i].equals(other.at(i))) {
				return false;
			}
		}
		
		return true;
	}
}
