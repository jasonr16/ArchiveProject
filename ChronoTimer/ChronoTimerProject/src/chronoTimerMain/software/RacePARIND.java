package chronoTimerMain.software;

import java.util.ArrayList;

import junit.framework.TestCase;

import chronoTimerMain.simulator.ChronoHardwareHandler;

/**
 * Represents a single race of the parallel individual event type.
 */
public class RacePARIND extends Race {
	// a parallel individual race can be considered as two separate individual races,
	// each with their own start and finish channels
	private RaceIND race1;
	private RaceIND race2;
	
	/**
	 * Creates a parallel individual race
	 * @param run number for the race
	 * @param Timer object used for the race
	 */
	public RacePARIND(int runNumber, Timer timer) {
		super(runNumber, timer);
		this.race1 = new RaceIND(runNumber, timer);
		this.race2 = new RaceIND(runNumber, timer);
		// TODO: unconventional?
		race1.setStartChannel(1);
		race1.setFinishChannel(3);
		race2.setStartChannel(2);
		race2.setFinishChannel(4);
	}
	
	/**
	 * Gets race 1 of the two parallel races
	 * @return race 1 object
	 */
	public RaceIND getRace1() {
		return this.race1;
	}
	
	/**
	 * Gets race 2 of the two parallel races
	 * @return race 2 object
	 */
	public RaceIND getRace2() {
		return this.race2;
	}

	/**
	 * Gets the start channel associated with race 1
	 * @return start channel number
	 */
	public int getStartChannel1() {
		return race1.getStartChannel();
	}
	
	/**
	 * Gets the start channel associated with race 2
	 * @return start channel number
	 */
	public int getStartChannel2() {
		return race2.getStartChannel();
	}
	
	/**
	 * Gets the finish channel associated with race 1
	 * @return finish channel number
	 */
	public int getFinishChannel1() {
		return race1.getFinishChannel();
	}
	
	/**
	 * Gets the finish channel associated with race 2
	 * @return finish channel number
	 */
	public int getFinishChannel2() {
		return race2.getFinishChannel();
	}
	
	/**
	 * Add a new racer to the end of the start queue
	 * Alternate between adding racers to race 1 and race 2
	 * @param racer number of racer to be added
	 * @return true if add was successful, else false
	 */
	@Override
	public boolean addRacerToStart(int racerNum) {
		boolean result = false;
		Racer racer = null;
		ArrayList<Racer> startList = super.getStartList();
		if (super.getCorrectRacer(racerNum) == null) { // check if racer is already in start queue
			racer = new Racer(racerNum);
			startList.add(racer);
			// first racer gets added to race 1, second racer gets added to race 2, etc.
			if ((startList.size() % 2) != 0)
				this.race1.getStartList().add(racer);
			else
				this.race2.getStartList().add(racer);
			result = true;
		}
		return result;
	}
	
	/**
	 * Remove a racer from the overall start queue
	 * as well as the individual start queue
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
			ArrayList<Racer>startList1 = this.race1.getStartList();
			ArrayList<Racer>startList2 = this.race2.getStartList();
			// also remove racer from race 1 or race 2 start queue
			if (startList1.contains(racer))
				startList1.remove(racer);
			else
				startList2.remove(racer);
			result = true;
		}
		return result;
	}
	
	/**
	 * Trigger a channel and handle the resulting changes in the race
	 * @param channel number of the triggered channel
	 * @param time stamp from file input, null if console input used
	 * @return true if a racer was moved from one queue to another, false if not
	 */
	@Override
	public boolean trig(int channelNum, String timeStamp) {
		boolean result = false;
		ArrayList<Racer> startList = super.getStartList();
		ArrayList<Racer> runningList = super.getRunningList();
		ArrayList<Racer> finishList = super.getFinishList();
		ArrayList<Racer> startList1 = this.race1.getStartList();
		ArrayList<Racer> runningList1 = this.race1.getRunningList();
		ArrayList<Racer> finishList1 = this.race1.getFinishList();
		ArrayList<Racer> startList2 = this.race2.getStartList();
		ArrayList<Racer> runningList2 = this.race2.getRunningList();
		ArrayList<Racer> finishList2 = this.race2.getFinishList();
		Racer racer = null;
		
		if (channelNum > 12)
			return false;
		
		// TODO: unconventional?
		/*
		// if no start channels have been set, set the start channel of race 1 to
		// the trigger number
		if (this.race1.getStartChannel() == 0 && this.race2.getStartChannel() == 0 &&
				startList.size() > 0) {
			this.race1.setStartChannel(channelNum);
		}
		
		// if race 1 start channel has been set, but race 2 start channel 2 has not,
		// set race 2 start channel to trigger number
		if (this.race1.getStartChannel() != 0 && this.race2.getStartChannel() == 0 &&
				startList.size() > 0) {
			this.race2.setStartChannel(channelNum);
		}
		
		// if start channels of race 1 and race 2 have both been set,
		// a later trigger on a different channel must be a finish event, so we set the finish 
		// channel to that number
		if (this.startChannel != 0 && this.startChannel != channelNum && this.finishChannel == 0
				&& runningList.size() > 0) {
			this.finishChannel = channelNum;
		}
		*/
		
		// if there are racers in the start queue, a start event should move the racer at the head of the start queue
		// into the running queue
		if (channelNum == this.race1.getStartChannel() && startList1.size() > 0) {
			racer = startList1.remove(0);
			startList.remove(racer);
			if (timeStamp.equals(""))
				racer.setStartTime(super.getTimer().getCurrentChronoTime());
			else
				racer.setStartTime(timeStamp);
			runningList.add(racer);
			runningList1.add(racer);
			result = true;
		}
		if (channelNum == this.race2.getStartChannel() && startList2.size() > 0) {
			racer = startList2.remove(0);
			startList.remove(racer);
			if (timeStamp.equals(""))
				racer.setStartTime(super.getTimer().getCurrentChronoTime());
			else
				racer.setStartTime(timeStamp);
			runningList.add(racer);
			runningList2.add(racer);
			result = true;
		}
		// if there are racers in the running queue, a finish event should move the racer at the head of the running
		// queue into the finish queue
		if (channelNum == this.race1.getFinishChannel() && runningList1.size() > 0) {
			racer = runningList1.remove(0);
			runningList.remove(racer);
			if (timeStamp.equals(""))
				racer.setFinishTime(super.getTimer().getCurrentChronoTime());
			else
				racer.setFinishTime(timeStamp);
			finishList.add(racer);
			finishList1.add(racer);
			result = true;
		}
		if (channelNum == this.race2.getFinishChannel() && runningList2.size() > 0) {
			racer = runningList2.remove(0);
			runningList.remove(racer);
			if (timeStamp.equals(""))
				racer.setFinishTime(super.getTimer().getCurrentChronoTime());
			else
				racer.setFinishTime(timeStamp);
			finishList.add(racer);
			finishList2.add(racer);
			result = true;
		}
		return result;
	}
	
	/**
	 * Trigger default start channel
	 * @param time stamp from file input, null if console input
	 * @return true if a racer was moved from one queue to another, false if not
	 */
	@Override
	public boolean start(String timeStamp) {
		boolean result = false;
		Racer racer;
		ArrayList<Racer> startList = super.getStartList();
		ArrayList<Racer> startList1 = this.race1.getStartList();
		ArrayList<Racer> startList2 = this.race2.getStartList();
		// the next person to start is the person at the head of the
		// overall start queue
		if (startList.size() > 0) {
			racer = startList.get(0);
			if (startList1.contains(racer)) {
				result = trig(this.race1.getStartChannel(), timeStamp);
			}
			else if (startList2.contains(racer)) {
				result = trig(this.race2.getStartChannel(), timeStamp);
			}
		}
		return result;
	}

	/**
	 * Trigger default finish channel
	 * @param time stamp from file input, null if console input
	 * @return true if a racer was moved from one queue to another, false if not
	 */
	@Override
	public boolean finish(String timeStamp) {
		boolean result = false;
		Racer racer;
		ArrayList<Racer> runningList = super.getRunningList();
		ArrayList<Racer> runningList1 = this.race1.getRunningList();
		ArrayList<Racer> runningList2 = this.race2.getRunningList();
		// the next person to finish is the person at the head of the
		// overall running queue
		if (runningList.size() > 0) {
			racer = runningList.get(0);
			if (runningList1.contains(racer)) {
				result = trig(this.race1.getFinishChannel(), timeStamp);
			}
			else if (runningList2.contains(racer)) {
				result = trig(this.race2.getFinishChannel(), timeStamp);
			}
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
		ArrayList<Racer> runningList1 = this.race1.getRunningList();
		ArrayList<Racer> runningList2 = this.race2.getRunningList();
		ArrayList<Racer> startList = super.getStartList();
		ArrayList<Racer> startList1 = this.race1.getStartList();
		ArrayList<Racer> startList2 = this.race2.getStartList();
		if (runningList.size() > 0) {
			Racer racer = runningList.remove(runningList.size()-1);
			racer.setStartTime("00:00:00.0");
			startList.add(0, racer);
			if (runningList1.contains(racer)) {
				runningList1.remove(racer);
				startList1.add(0, racer);
			}
			else if (runningList2.contains(racer)) {
				runningList2.remove(racer);
				startList2.add(0, racer);
			}
			result = true;
		}
		return result;
	}

	/**
	 * TODO: does swap supposed to work for PARIND?
	 * Swap the two racers who are at the head of the running queue.
	 * @return true if the swap was successful, else false
	 */
	@Override
	public boolean swapRunningRacers() {
		return false;
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
		ArrayList<Racer> runningList1 = this.race1.getRunningList();
		ArrayList<Racer> runningList2 = this.race2.getRunningList();
		ArrayList<Racer> finishList = super.getFinishList();
		ArrayList<Racer> finishList1 = this.race1.getFinishList();
		ArrayList<Racer> finishList2 = this.race2.getFinishList();
		Racer racer = null;
		if (runningList.size() > 0) {
			racer = runningList.remove(0);
			racer.setDNF(true);
			finishList.add(racer);
			if (runningList1.contains(racer)) {
				runningList1.remove(racer);
				finishList1.add(racer);
			}
			else if (runningList2.contains(racer)) {
				runningList2.remove(racer);
				finishList2.add(racer);
			}
			result = true;
		}
		return result;
	}
}