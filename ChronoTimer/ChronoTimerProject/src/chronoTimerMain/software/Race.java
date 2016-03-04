package chronoTimerMain.software;

import java.util.ArrayList;

/**
 * Represents a single generic race event with multiple participating racers.
 * A race is created via the NEWRUN command in ChronoTimer, or by default when the system
 * is first powered on.
 * Always contains a Timer object that is associated with ChronoTimer's internal clock.
 */

public abstract class Race {
	private int runNumber;
	private Timer timer;
	private ArrayList<Racer> startList; // queue of racers who are about to start
	private ArrayList<Racer> runningList; // queue of racers who are running
	private ArrayList<Racer> finishList; // queue of racers who have finished
	
	protected Race(int runNumber, Timer timer){
		this.runNumber = runNumber;
		this.timer = timer;
		this.startList = new ArrayList<Racer>();
		this.runningList = new ArrayList<Racer>();
		this.finishList = new ArrayList<Racer>();
	};
	
	/**
	 * Gets the run number of the race
	 * @return run number
	 */
	public int getRunNumber() {
		return runNumber;
	}

	/**
	 * Sets the run number of the race
	 * @param run number
	 */
	public void setRunNumber(int runNumber) {
		this.runNumber = runNumber;
	}
	
	/**
	 * Gets the timer associated with the race
	 * @return timer
	 */
	public Timer getTimer() {
		return this.timer;
	}
	
	/**
	 * Gets the start queue for the race
	 * @return startList
	 */
	protected ArrayList<Racer> getStartList() {
		return this.startList;
	}
	
	/**
	 * Gets the running queue for the race
	 * @return runningList
	 */
	protected ArrayList<Racer> getRunningList() {
		return this.runningList;
	}
	
	/**
	 * Gets the finish queue for the race
	 * @return finishList
	 */
	protected ArrayList<Racer> getFinishList() {
		return this.finishList;
	}
	
	/**
	 * Calculates the run duration of a racer who is running or finished a race
	 * @param racerNum
	 * @return the formatted time duration of a racer's run. If the racer did not start yet,
	 * returns a duration of zero.
	 */
	public String getRacerDuration(int racerNum) {
		String result = "";
		Racer racer = null;
		// if racer is in start queue, return zero duration
		for(int i = 0; i < startList.size(); i++) {
			if (startList.get(i).getNumber() == racerNum) {
				racer = startList.get(i);
				result = timer.getRunDuration(racer.getStartTime(), racer.getStartTime());
			}
		}
		
		// if racer is in running queue, return duration between current time and start time
		if (racer == null) {
			for(int i = 0; i < runningList.size(); i++) {
				if (runningList.get(i).getNumber() == racerNum) {
					racer = startList.get(i);
					result = timer.getRunDuration(racer.getStartTime(), timer.getCurrentChronoTime());
				}
			}
		}
		
		// if racer is in finish queue and actually finished, return duration between
		// finish time and start time
		if (racer == null) {
			for(int i = 0; i < finishList.size(); i++) {
				if (finishList.get(i).getNumber() == racerNum) {
					racer = finishList.get(i);
					// if racer did not finish, return "DNF"
					if (racer.getDNF())
						result = "DNF";
					else
						result = timer.getRunDuration(racer.getStartTime(), racer.getFinishTime());
				}
			}
		}
		
		// if racer not found in any queue, return "Racer not found"
		if (racer == null) {
			result = "Racer " + racerNum + " not found";
		}
		return result;
	}
	
	/**
	 * Method for finding the correct Racer in the race queues.
	 * @param racerNum
	 * @return Racer with number raceNumber, or null if does not exist
	 */
	public Racer getCorrectRacer(int racerNum) {
		for(int i = 0; i < startList.size(); i++) {
			if (startList.get(i).getNumber() == racerNum) {
				return startList.get(i);
			}
		}
		for(int i = 0; i < runningList.size(); i++) {
			if (runningList.get(i).getNumber() == racerNum) {
				return runningList.get(i);
			}
		}
		for(int i = 0; i < finishList.size(); i++) {
			if (finishList.get(i).getNumber() == racerNum) {
				return finishList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Adds a racer as the next racer to start in race
	 * Corresponds to the NUM <NUMBER> command
	 * @param racer
	 */
	public abstract boolean addRacerToStart(int racerNum);
	
	/**
	 * Removes the next racer to start from the race
	 * Corresponds to the CLR <NUMBER> command
	 * @param racerNum
	 */
	public abstract boolean removeRacerFromStart(int racerNum);

	/**
	 * Marks the next racer to finish as DNF
	 * Corresponds to the DNF command
	 */
	public abstract boolean handleRacerDNF();
	
	/**
	 * Cancels the last racer who started to run
	 * Corresponds to the CANCEL command
	 */
	public abstract boolean handleRacerCancel();
	
	/**
	 * Swaps the next two racers expected to finish
	 * Corresponds to the SWAP command
	 */
	public abstract boolean swapRunningRacers();
	
	/**
	 * Moves a racer from the start queue to the running queue
	 */
	public abstract boolean start();
	
	/**
	 * Moves a racer from the running queue to the finish queue
	 */
	public abstract boolean finish();
	
	
	public abstract boolean trig(int channelNum);

	/**
	 * Prints formatted results of the race
	 */
	public void print() {
		System.out.println("Race " + this.runNumber);
		for(int i = 0; i < finishList.size(); i++) {
			System.out.printf("\t%d\t%s\n", finishList.get(i).getNumber(),
					getRacerDuration(finishList.get(i).getNumber()));
		}
	}
}
