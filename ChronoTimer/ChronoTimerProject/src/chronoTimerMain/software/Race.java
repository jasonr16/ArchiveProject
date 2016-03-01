package chronoTimerMain.software;

import java.util.LinkedList;

/**
 * Represents a single generic race event with multiple participating racers.
 * A race is created via the NEWRUN command in ChronoTimer.
 * Always contains a Timer object that is associated with ChronoTimer's internal clock.
 * Racer queues are implemented by concrete classes that extend Race.
 * @author yangxie
 *
 */

public abstract class Race {
	private int runNumber = 1;
	private Timer timer;
	private LinkedList<Racer> startList;
	private LinkedList<Racer> runningList;
	private LinkedList<Racer> finishList;
	
	public Race(Timer time){};
	
	/**
	 * Duration is stored in racerList-obtain with racerList[i].getDuration(). 
	 * IMPORTANT: Call updateDuration() on each Racer in the list to get the most current duration value.
	 * getRacerDuration is used by ChronoTimerEventHandler's print() method to print racer times.
	 * @param racerNumber
	 * @return racerNumber's time as String in format hh:mm:ss.ss
	 */
	public String getRacerDuration(int racerNumber) {
		updateDuration(racerNumber);
		return getCorrectRacer(racerNumber).getDuration();
	}
	/**
	 * helper method for finding the correct Racer in racerlist.
	 * @param racerNumber
	 * @return Racer with number raceNumber, or null if does not exist
	 */
	private Racer getCorrectRacer(int racerNumber) {
		for(int i = 0; i < startList.size(); i++) {
			if (startList.get(i).getNumber() == racerNumber) {
				return startList.get(i);
			}
		}
		for(int i = 0; i < runningList.size(); i++) {
			if (runningList.get(i).getNumber() == racerNumber) {
				return runningList.get(i);
			}
		}
		for(int i = 0; i < finishList.size(); i++) {
			if (finishList.get(i).getNumber() == racerNumber) {
				return finishList.get(i);
			}
		}
		return null;
	}
	
	/** 
	 * updateDuration updates the duration field in racerList's racers.
	 *  It uses the timer's getDurationAsString(int racernum) 
	 *  to update racerNumber racer with setDuration(String duration) for Racers.
	 * @param racerNumber
	 */
	public void updateDuration(int racerNumber) {
		getCorrectRacer(racerNumber).setDuration(timer.getDurationAsString(racerNumber));
	}

	
	/**
	 * Adds a racer as the next racer to start in race
	 * Corresponds to the NUM <NUMBER> command
	 * @param racer
	 */
	public abstract void addRacer(Racer racer);
	/**
	 * Adds a racer as the next racer to start in race
	 * Corresponds to the NUM <NUMBER> command
	 * @param racer
	 */
	public abstract void addRacer(int RacerNumber);
	// I need add racers that take an int for racernumber. -Jason
	
	
	/**
	 * Removes the next racer to start from the race
	 * Corresponds to the CLR <NUMBER> command
	 * @param racerNumber
	 */
	public abstract void removeRacer(Racer racer);
	/**
	 * Removes the next racer to start from the race
	 * Corresponds to the CLR <NUMBER> command
	 * @param racerNumber
	 */
	public abstract void removeRacer(int racerNumber);
	// I need remove racers that take an int for racernumber. -Jason

	/**
	 * Marks the next racer to finish as DNF
	 * Corresponds to the DNF
	 */
	public abstract void markRacerDNF();
	public abstract void swap();
	public abstract void start();

	public abstract void finish();
	public abstract void trig(int channel);

	public int getRunNumber() {
		return runNumber;
	}

	public void setRunNumber(int runNumber) {
		this.runNumber = runNumber;
	}

	public void print() {
		System.out.println("Race " + runNumber);
		for(int i = 0; i < finishList.size(); i++) {
			System.out.println(finishList.get(i).getNumber() + "     "
					+ getRacerDuration(finishList.get(i).getNumber()));
		}
		
	}
}
