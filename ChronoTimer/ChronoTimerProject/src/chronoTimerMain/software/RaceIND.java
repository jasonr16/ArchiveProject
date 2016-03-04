package chronoTimerMain.software;

import java.util.ArrayList;

/**
 * Represents a single race of the individual event type.
 * Is the default race type when the NEWRUN command is entered.
 */
public class RaceIND extends Race {
	// for individual races, there should only be 2 connected channels
	private int startChannel; 
	private int finishChannel;
	
	/**
	 * Creates an individual race
	 * @param run number for the race
	 * @param Timer object used for the race
	 */
	public RaceIND(int runNumber, Timer timer) {
		super(runNumber, timer);
		this.startChannel = 0;
		this.finishChannel = 0;
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
		System.out.println("Adding racer to start");
		super.setStartList(startList);
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
		System.out.println("Removing racer from start");
		super.setStartList(startList);
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
		System.out.println("racer DNF");
		super.setRunningList(runningList);
		super.setFinishList(finishList);
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
		System.out.println("Cancel - false start");
		super.setStartList(startList);
		super.setRunningList(runningList);
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
		System.out.println("swapping racers");
		super.setRunningList(runningList);
		return result;
		
	}

	/**
	 * Trigger default start channel
	 * @return true if a racer was moved from one queue to another, false if not
	 */
	@Override
	public boolean start() {
		System.out.println("start triggered");
		boolean result = false;
		if (this.startChannel == 0)
			result = trig(1);
		else
			result = trig(this.startChannel);
		return result;
	}

	/**
	 * Trigger default finish channel
	 * @return true if a racer was moved from one queue to another, false if not
	 */
	@Override
	public boolean finish() {
		System.out.println("finish triggered");
		boolean result = false;
		if (this.finishChannel == 0)
			result = trig(2);
		else
			result = trig(this.finishChannel);
		return result;
	}

	/**
	 * Trigger a channel and handle the resulting changes in the race
	 * @return true if a racer was moved from one queue to another, false if not
	 */
	@Override
	public boolean trig(int channelNum) {
		System.out.println("trigger event on channel " + channelNum);
		boolean result = false;
		ArrayList<Racer> startList = super.getStartList();
		ArrayList<Racer> runningList = super.getRunningList();
		ArrayList<Racer> finishList = super.getFinishList();
		// if the event was the first trigger of the race, it must be a start event, and we set the start channel to that 
		// channel number
		if (this.startChannel == 0) {
			this.startChannel = channelNum;
		}
		// a later trigger on a different channel must be a finish event, so we set the finish channel to that number
		else if (this.startChannel != channelNum && this.finishChannel == 0) {
			this.finishChannel = channelNum;
		}
		
		// if there are racers in the start queue, a start event should move the racer at the head of the start queue
		// into the running queue
		if (channelNum == this.startChannel && startList.size() > 0) {
			runningList.add(startList.remove(0));
			result = true;
		}
		// if there are racers in the running queue, a finish event should move the racer at the head of the running
		// queue into the finish queue
		else if (channelNum == this.finishChannel) {
			finishList.add(runningList.remove(0));
			result = true;
		}
		return result;
	}
	
}
