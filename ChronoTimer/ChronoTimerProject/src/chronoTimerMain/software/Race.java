package chronoTimerMain.software;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Represents a single generic race event with multiple participating racers.
 * A race is created via the NEWRUN command in ChronoTimer, or by default when the system
 * is first powered on.
 * Always contains a Timer object that is associated with ChronoTimer's internal clock.
 * Racer queues are implemented by concrete classes that extend Race.
 *
 */

public abstract class Race {
	private int runNumber = 1;
	private Timer timer;
	private ArrayList<Racer> startList;
	private ArrayList<Racer> runningList;
	private ArrayList<Racer> finishList;
	
	protected Race(Timer timer){
		this.timer = timer;
		this.startList = new ArrayList<Racer>();
		this.runningList = new ArrayList<Racer>();
		this.finishList = new ArrayList<Racer>();
	};

	/**
	 * Duration is stored in racerList-obtain with racerList[i].getDuration(). 
	 * IMPORTANT: Call updateDuration() on each Racer in the list to get the most current duration value.
	 * getRacerDuration is used by ChronoTimerEventHandler's print() method to print racer times.
	 * @param racerNumber
	 * @return racerNumber's time as String in format hh:mm:ss.ss
	 */
	
	/**
	 * Getter method for startList
	 * @return startList
	 */
	protected ArrayList<Racer> getStartList() {
		return this.startList;
	}
	
	/**
	 * Getter method for runningList
	 * @return runningList
	 */
	protected ArrayList<Racer> getRunningList() {
		return this.runningList;
	}
	
	/**
	 * Getter method for finishList
	 * @return finishList
	 */
	protected ArrayList<Racer> getFinishList() {
		return this.finishList;
	}
	
	public String getRacerDuration(int racerNumber) {
		updateDuration(racerNumber);
		return getCorrectRacer(racerNumber).getDuration();
	}
	/**
	 * Method for finding the correct Racer in racerlists.
	 * @param racerNumber
	 * @return Racer with number raceNumber, or null if does not exist
	 */
	public Racer getCorrectRacer(int racerNumber) {
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
	public abstract void markRacerCancel();
	public abstract boolean swap();
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
