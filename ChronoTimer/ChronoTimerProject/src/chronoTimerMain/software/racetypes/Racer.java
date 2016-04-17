package chronoTimerMain.software.racetypes;

import java.time.Instant;

/**
 * Represents a racer who participates in a racing event.
 * A racer is assigned a number, and keeps track of its start time and finish time
 * in an event, as well as other special conditions such as DNF.
 */
public class Racer {
	private String number;
	private String startTime; // timestamp of start time
	private String finishTime; // timestamp of finish time
	private boolean dnf; // whether the racer did not finish a race
	
	public Racer(String number) {
		this.number = number;
		this.setStartTime("00:00:00.0");
		this.setFinishTime("00:00:00.0");
		this.dnf = false;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public boolean getDNF() {
		return this.dnf;
	}
	
	public void setDNF(boolean dnf) {
		this.dnf = dnf;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
}
