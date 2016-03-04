package chronoTimerMain.software;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a single race of the individual event type.
 * Is the default race type when the NEWRUN command is entered.
 * Contains a queue (FIFO) of racers that will be running individually.
 *
 */
public class RaceIND extends Race {
	/**
	 * Constructor
	 * @param timer
	 */
	public RaceIND(int runNumber, Timer timer) {
		super(runNumber, timer);
	}

	/**
	 * Swap the next two racers that will finish
	 * @return true if swap was successful, else false
	 */
	public boolean swap() {
		boolean result = false;
		ArrayList<Racer> runningList = super.getRunningList();
		if (runningList.size() >= 2) {
			Racer temp = runningList.remove(0);
			runningList.add(1, temp);
			result = true;
		}
		return result;
	}
	
	/**
	 * Add a new racer to the end of the start queue
	 * @param racer to be added
	 * @return true if add was successful, else false
	 */
	@Override
	public boolean addRacerToStart(Racer racer) {
		boolean result = false;
		ArrayList<Racer> startList = super.getStartList();
		if (!startList.contains(racer)) {
			startList.add(racer);
			result = true;
		}
		return result;
	}
	
	/**
	 * Remove a racer from the start queue
	 * @param racer number of racer to be removed
	 * @return true if remove was successful, else false
	 */
	@Override
	public boolean removeRacerFromStart(int racerNum) {
		boolean result = false;
		ArrayList<Racer> startList = super.getStartList();
		Racer racer = super.getCorrectRacer(racerNum);
		if (racer != null && startList.contains(racer)) {
			startList.remove(racer);
			result = true;
		}
		return result;
	}

	/**
	 * Remove the racer at the head of the running queue and add him to the finish queue.
	 * Mark the racer's dnf field as true.
	 * @return true if a racer was handled, else false
	 */
	@Override
	public boolean handleRacerDNF() {
		boolean result = false;
		ArrayList<Racer> runningList = super.getRunningList();
		ArrayList<Racer> finishList = super.getFinishList();
		Racer racer = null;
		if (runningList.size() >= 1) {
			racer = runningList.remove(0);
			racer.setDNF(true);
			finishList.add(racer);
		}
		return result;
	}

	/**
	 * Remove the racer at the end of the running queue (latest racer to start running) and add him
	 * back to the head of the start queue.
	 * Reset the racer's start time field.
	 * @return true if a racer was handled, else false
	 */
	@Override
	public boolean handleRacerCancel() {
		boolean result = false;
		ArrayList<Racer> runningList = super.getRunningList();
		ArrayList<Racer> startList = super.getStartList();
		Racer racer = runningList.remove(runningList.size()-1);
		racer.setStartTime("");
		startList.add(0, racer);
		return result;
	}

	/**
	 * Swap the two racers who are at the head of the running queue.
	 * @return true if the swap was successful, else false
	 */
	@Override
	public boolean swapRunningRacers() {
		boolean result = false;
		ArrayList<Racer> runningList = super.getRunningList();
		if (runningList.size() >= 2) {
			Racer tempRacer = runningList.get(0);
			runningList.set(0, runningList.get(1));
			runningList.set(1, tempRacer);
		}
		return result;
	}

	/**
	 * Trigger channel 1
	 */
	@Override
	public boolean start() {
		return trig(1);
	}

	/**
	 * Trigger channel 2
	 */
	@Override
	public boolean finish() {
		return trig(2);
	}

	/**
	 * Trigger a channel and handle the resulting changes in the race
	 */
	@Override
	public boolean trig(int channelNum) {
		boolean result = false;
		// for individual races, there should only be 2 connected channels
		// if this was the first trigger and there are racers in the start queue, 
		// then it must be a start event
		
		// a later trigger on a different channel must be a finish event
		return result;
	}
	
}
