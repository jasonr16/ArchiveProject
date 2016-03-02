package chronoTimerMain.software;

import java.time.Instant;

/**
 * Represents a racer who participates in a racing event.
 * A racer is assigned a number, and keeps track of a start time and finish time
 * in an event.
 */
public class Racer {
	private int number;
	// duration can be calculated from start and finish time by passing a Racer object
	// to the Timer class?
	private String duration;
	private boolean dnf;
	
	public Racer(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getDuration() {
		String result;
		if (dnf) {
			result = "DNF";
		}
		else {
			result = this.duration;
		}
		return result;
	}

	public void setDuration(String durationAsString) {
		this.duration = durationAsString;
		
	}
	
	public boolean getDNF() {
		return this.dnf;
	}
	
	public void setDNF(boolean dnf) {
		this.dnf = dnf;
	}
}
