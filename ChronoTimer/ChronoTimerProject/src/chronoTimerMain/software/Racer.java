package chronoTimerMain.software;

import java.time.Instant;

/**
 * Represents a racer who participates in a racing event.
 * A racer is assigned a number, and keeps track of a start time and finish time
 * in an event.
 */
public class Racer {
	private int number;
	private Instant startTime;
	private Instant finishTime;
	// duration can be calculated from start and finish time by passing a Racer object
	// to the Timer class?
	private String duration;
	
	public Racer(int number) {
		this.number = number;
		this.startTime = null;
		this.finishTime = null;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public Instant getStartTime() {
		return startTime;
	}

	public void setStartTime(Instant startTime) {
		this.startTime = startTime;
	}

	public Instant getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Instant finishTime) {
		this.finishTime = finishTime;
	}
	
	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String durationAsString) {
		this.duration = durationAsString;
		
	}
}
