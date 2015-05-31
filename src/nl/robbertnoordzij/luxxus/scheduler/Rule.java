package nl.robbertnoordzij.luxxus.scheduler;

import java.time.LocalTime;

public interface Rule {
	public boolean shouldExecute(LocalTime currentTime);
}
