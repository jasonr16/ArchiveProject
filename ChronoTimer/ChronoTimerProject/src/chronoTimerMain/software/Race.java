package chronoTimerMain.software;

import java.util.ArrayList;

/**
 * Represents a single generic race event with multiple participating racers.
 * A race is created via the NEWRUN command in ChronoTimer.
 * Always contains a Timer object that is associated with ChronoTimer's internal clock.
 * Racer queues are implemented by concrete classes that extend Race.
 * @author yangxie
 *
 */

public abstract class Race {
	private Timer timer;
	
	/**
	 * Duration is stored in racerList-obtain with racerList[i].getDuration(). 
	 * IMPORTANT: Call updateDuration() on each Racer in the list to get the most current duration value.
	 * getRacerDuration is used by ChronoTimerEventHandler's print() method to print racer times.
	 * @param racerNumber
	 * @return racerNumber's time as String in format hh:mm:ss.ss
	 */
	public String getRacerDuration(int racerNumber) {
		updateDuration(racerNumber);
		return null;}
	
	/** 
	 * updateDuration updates the duration field in racerList's racers.
	 *  It uses the timer's getDurationAsString(int racernum) 
	 *  to update racerNumber racer with setDuration(String duration) for Racers.
	 * @param racerNumber
	 */
	public void updateDuration(int racerNumber) {
		
	}
	
	/**
	 * Adds a racer as the next racer to start in race
	 * Corresponds to the NUM <NUMBER> command
	 * @param racer
	 */
	public abstract void addRacer(Racer racer);
	
	/**
	 * Removes the next racer to start from the race
	 * Corresponds to the CLR <NUMBER> command
	 * @param racerNumber
	 */
	public abstract void removeRacer(Racer racer);

	/**
	 * Marks the next racer to finish as DNF
	 * Corresponds to the DNF
	 */
	public abstract void markRacerDNF();

	public abstract void start();

	public abstract void finish();
	public abstract void trig(int channel);
}
