package chronoTimerMain.software.racetypes;

import java.util.ArrayList;

import chronoTimerMain.software.Timer.Timer;
import chronoTimerMain.software.racetypes.race.Race;
import junit.framework.TestCase;

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
	 * Gets the start channel associated with the race
	 * @return start channel number
	 */
	public int getStartChannel() {
		return this.startChannel;
	}
	
	/**
	 * Set the start channel
	 * @param num - start channel number
	 */
	public void setStartChannel(int num) {
		this.startChannel = num;
	}
	
	/**
	 * Gets the finish channel associated with the race
	 * @return finish channel number
	 */
	public int getFinishChannel() {
		return this.finishChannel;
	}
	
	/**
	 * Set the finish channel
	 * @param num - finish channel number
	 */
	public void setFinishChannel(int num) {
		this.finishChannel = num;
	}
	
	/**
	 * Add a new racer to the end of the start queue
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
		Racer racer = null;
		
		if (channelNum > 12)
			return false;
		
		// if the event was the first trigger of the race, it must be a start event, and we set
		// the start channel to that number
		if (this.startChannel == 0 && startList.size() > 0) {
			this.startChannel = channelNum;
		}
		
		// a later trigger on a different channel must be a finish event, so we set the finish 
		// channel to that number
		if (this.startChannel != 0 && this.startChannel != channelNum && this.finishChannel == 0
				&& runningList.size() > 0) {
			this.finishChannel = channelNum;
		}
		
		// if there are racers in the start queue, a start event should move the racer at the head of the start queue
		// into the running queue
		if (channelNum == this.startChannel && startList.size() > 0) {
			racer = startList.remove(0);
			if (timeStamp.equals(""))
				racer.setStartTime(super.getTimer().getCurrentChronoTime());
			else
				racer.setStartTime(timeStamp);
			
			runningList.add(racer);
			result = true;
		}
		// if there are racers in the running queue, a finish event should move the racer at the head of the running
		// queue into the finish queue
		else if (channelNum == this.finishChannel && runningList.size() > 0) {
			racer = runningList.remove(0);
			if (timeStamp.equals(""))
				racer.setFinishTime(super.getTimer().getCurrentChronoTime());
			else
				racer.setFinishTime(timeStamp);
			finishList.add(racer);
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
		if (this.startChannel == 0)
			result = trig(1, timeStamp);
		else
			result = trig(this.startChannel, timeStamp);
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
		if (this.finishChannel == 0)
			result = trig(2, timeStamp);
		else
			result = trig(this.finishChannel, timeStamp);
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
		if (runningList.size() > 0) {
			Racer racer = runningList.remove(runningList.size()-1);
			racer.setStartTime("00:00:00.0");
			startList.add(0, racer);
			result = true;
		}
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
			result = true;
		}
		return result;
	}
	
	public static class raceINDTester extends TestCase{
		RaceIND race1, race2;
		Timer timer;
		
		@Override
		protected void setUp() {
			timer = new Timer();
			race1 = new RaceIND(1,timer);
			race2 = new RaceIND(2,timer);
		}
		
		public void testAddRacerToStart() {
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			
			assertTrue(race1.addRacerToStart(234));
			assertEquals(1, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(234, race1.getStartList().get(0).getNumber());
			
			assertTrue(race1.addRacerToStart(315));
			assertEquals(2, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(234, race1.getStartList().get(0).getNumber());
			assertEquals(315, race1.getStartList().get(1).getNumber());
			
			// add duplicate racer
			assertFalse(race1.addRacerToStart(234));
			assertEquals(2, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(234, race1.getStartList().get(0).getNumber());
			assertEquals(315, race1.getStartList().get(1).getNumber());
			
			assertEquals(0, race2.getStartList().size());
			assertEquals(0, race2.getRunningList().size());
			assertEquals(0, race2.getFinishList().size());
			
			assertTrue(race2.addRacerToStart(167));
			assertEquals(1, race2.getStartList().size());
			assertEquals(0, race2.getRunningList().size());
			assertEquals(0, race2.getFinishList().size());
			assertEquals(167, race2.getStartList().get(0).getNumber());
			
			assertTrue(race2.addRacerToStart(177));
			assertEquals(2, race2.getStartList().size());
			assertEquals(0, race2.getRunningList().size());
			assertEquals(0, race2.getFinishList().size());
			assertEquals(167, race2.getStartList().get(0).getNumber());
			assertEquals(177, race2.getStartList().get(1).getNumber());
			
			assertTrue(race2.addRacerToStart(200));
			assertEquals(3, race2.getStartList().size());
			assertEquals(0, race2.getRunningList().size());
			assertEquals(0, race2.getFinishList().size());
			assertEquals(167, race2.getStartList().get(0).getNumber());
			assertEquals(177, race2.getStartList().get(1).getNumber());
			assertEquals(200, race2.getStartList().get(2).getNumber());
			
			assertTrue(race2.addRacerToStart(201));
			assertEquals(4, race2.getStartList().size());
			assertEquals(0, race2.getRunningList().size());
			assertEquals(0, race2.getFinishList().size());
			assertEquals(167, race2.getStartList().get(0).getNumber());
			assertEquals(177, race2.getStartList().get(1).getNumber());
			assertEquals(200, race2.getStartList().get(2).getNumber());
			assertEquals(201, race2.getStartList().get(3).getNumber());
		}
		
		public void testRemoveRacerFromStart() {
			race1.addRacerToStart(234);
			race1.addRacerToStart(315);
			
			// remove non-existent racer
			assertFalse(race1.removeRacerFromStart(111));
			assertEquals(2, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(234, race1.getStartList().get(0).getNumber());
			assertEquals(315, race1.getStartList().get(1).getNumber());
			
			assertTrue(race1.removeRacerFromStart(234));
			assertEquals(1, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(315, race1.getStartList().get(0).getNumber());
			
			assertTrue(race1.removeRacerFromStart(315));
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
		}
		
		public void testTrig() {
			// trigger when no racers in start queue
			assertFalse(race1.trig(3, ""));
			assertEquals(0, race1.getStartChannel());
			assertEquals(0, race1.getFinishChannel());
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			
			// trigger channel 1 (first racer starts)
			race1.addRacerToStart(234);
			race1.addRacerToStart(315);
			assertTrue(race1.trig(1, ""));
			assertEquals(1, race1.getStartChannel());
			assertEquals(1, race1.getStartList().size());
			assertEquals(315, race1.getStartList().get(0).getNumber());
			assertEquals(1, race1.getRunningList().size());
			assertEquals(234, race1.getRunningList().get(0).getNumber());
			assertEquals(0, race1.getFinishList().size());
			
			// trigger channel 2 (first racer finishes)
			assertTrue(race1.trig(2, ""));
			assertEquals(1, race1.getStartChannel());
			assertEquals(2, race1.getFinishChannel());
			assertEquals(1, race1.getStartList().size());
			assertEquals(315, race1.getStartList().get(0).getNumber());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(1, race1.getFinishList().size());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			
			// trigger channel 3 (not start or finish)
			assertFalse(race1.trig(3, ""));
			assertEquals(1, race1.getStartChannel());
			assertEquals(2, race1.getFinishChannel());
			assertEquals(1, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(1, race1.getFinishList().size());
			
			// trigger channel 1 (second racer starts)
			assertTrue(race1.trig(1, ""));
			assertEquals(1, race1.getStartChannel());
			assertEquals(2, race1.getFinishChannel());
			assertEquals(0, race1.getStartList().size());
			assertEquals(1, race1.getRunningList().size());
			assertEquals(315, race1.getRunningList().get(0).getNumber());
			assertEquals(1, race1.getFinishList().size());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			
			// trigger channel 2 (second racer finishes)
			assertTrue(race1.trig(2, ""));
			assertEquals(1, race1.getStartChannel());
			assertEquals(2, race1.getFinishChannel());
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(2, race1.getFinishList().size());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			assertEquals(315, race1.getFinishList().get(1).getNumber());
			
			// trigger channels 1 and 2 (no more racers)
			assertFalse(race1.trig(1, ""));
			assertFalse(race1.trig(2, ""));
			assertEquals(1, race1.getStartChannel());
			assertEquals(2, race1.getFinishChannel());
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(2, race1.getFinishList().size());
			
			// trigger multiple starts
			race2.addRacerToStart(167);
			race2.addRacerToStart(177);
			race2.addRacerToStart(200);
			race2.addRacerToStart(201);
			assertTrue(race2.trig(3, ""));
			assertTrue(race2.trig(3, ""));
			assertTrue(race2.trig(3, ""));
			assertTrue(race2.trig(3, ""));
			assertEquals(3, race2.getStartChannel());
			assertEquals(0, race2.getFinishChannel());
			assertEquals(0, race2.getStartList().size());
			assertEquals(4, race2.getRunningList().size());
			assertEquals(167, race2.getRunningList().get(0).getNumber());
			assertEquals(177, race2.getRunningList().get(1).getNumber());
			assertEquals(200, race2.getRunningList().get(2).getNumber());
			assertEquals(201, race2.getRunningList().get(3).getNumber());
			assertEquals(0, race2.getFinishList().size());
			
			assertTrue(race2.trig(4, ""));
			assertTrue(race2.trig(4, ""));
			assertTrue(race2.trig(4, ""));
			assertTrue(race2.trig(4, ""));
			assertEquals(3, race2.getStartChannel());
			assertEquals(4, race2.getFinishChannel());
			assertEquals(0, race2.getStartList().size());
			assertEquals(0, race2.getRunningList().size());
			assertEquals(4, race2.getFinishList().size());
			assertEquals(167, race2.getFinishList().get(0).getNumber());
			assertEquals(177, race2.getFinishList().get(1).getNumber());
			assertEquals(200, race2.getFinishList().get(2).getNumber());
			assertEquals(201, race2.getFinishList().get(3).getNumber());
		}
		
		public void testStart() {
			race1.addRacerToStart(234);
			race1.addRacerToStart(315);
			assertTrue(race1.start(""));
			assertEquals(1, race1.getStartChannel());
			assertEquals(1, race1.getStartList().size());
			assertEquals(315, race1.getStartList().get(0).getNumber());
			assertEquals(1, race1.getRunningList().size());
			assertEquals(234, race1.getRunningList().get(0).getNumber());
			assertEquals(0, race1.getFinishList().size());
			assertTrue(race1.start(""));
			assertEquals(0, race1.getStartList().size());
			assertEquals(2, race1.getRunningList().size());
			assertEquals(234, race1.getRunningList().get(0).getNumber());
			assertEquals(315, race1.getRunningList().get(1).getNumber());
			assertEquals(0, race1.getFinishList().size());
			
			// test intermixing of trig and start commands
			race2.addRacerToStart(166);
			race2.addRacerToStart(167);
			assertTrue(race2.trig(3, ""));
			assertTrue(race2.start(""));
			assertEquals(0, race2.getStartList().size());
			assertEquals(2, race2.getRunningList().size());
			assertEquals(166, race2.getRunningList().get(0).getNumber());
			assertEquals(167, race2.getRunningList().get(1).getNumber());
			assertEquals(0, race2.getFinishList().size());
		}
		
		public void testFinish() {
			race1.addRacerToStart(234);
			race1.addRacerToStart(315);
			assertTrue(race1.start(""));
			assertTrue(race1.start(""));
			assertTrue(race1.finish(""));
			assertTrue(race1.finish(""));
			assertFalse(race1.finish(""));
			assertEquals(1, race1.getStartChannel());
			assertEquals(2, race1.getFinishChannel());
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(2, race1.getFinishList().size());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			assertEquals(315, race1.getFinishList().get(1).getNumber());
			
			// test intermixing of trig and finish commands
			race2.addRacerToStart(166);
			race2.addRacerToStart(167);
			assertTrue(race2.start(""));
			assertTrue(race2.start(""));
			assertTrue(race2.trig(5, ""));
			assertTrue(race2.finish(""));
			assertEquals(1, race2.getStartChannel());
			assertEquals(5, race2.getFinishChannel());
			assertEquals(0, race2.getStartList().size());
			assertEquals(0, race2.getRunningList().size());
			assertEquals(2, race2.getFinishList().size());
			assertEquals(166, race2.getFinishList().get(0).getNumber());
			assertEquals(167, race2.getFinishList().get(1).getNumber());
		}
		
		public void testCancel() {
			race1.addRacerToStart(234);
			race1.addRacerToStart(315);
			
			// test cancel when no racers in running queue
			assertFalse(race1.handleRacerCancel());
			
			// test normal cancel
			assertTrue(race1.start(""));
			assertTrue(race1.start(""));
			assertEquals(0, race1.getStartList().size());
			assertEquals(2, race1.getRunningList().size());
			assertEquals(234, race1.getRunningList().get(0).getNumber());
			assertEquals(315, race1.getRunningList().get(1).getNumber());
			assertTrue(race1.handleRacerCancel());
			assertEquals(1, race1.getStartList().size());
			assertEquals(1, race1.getRunningList().size());
			assertEquals(234, race1.getRunningList().get(0).getNumber());
			assertEquals(315, race1.getStartList().get(0).getNumber());
		}
		
		public void testSwap() {
			// test swap when no racers in running queue
			assertFalse(race1.swapRunningRacers());
			
			// test normal swap
			race1.addRacerToStart(234);
			race1.addRacerToStart(315);
			assertTrue(race1.start(""));
			assertTrue(race1.start(""));
			assertTrue(race1.swapRunningRacers());
			assertEquals(0, race1.getStartList().size());
			assertEquals(2, race1.getRunningList().size());
			assertEquals(315, race1.getRunningList().get(0).getNumber());
			assertEquals(234, race1.getRunningList().get(1).getNumber());
		}
		
		public void testDNF() {
			race1.addRacerToStart(234);
			race1.addRacerToStart(315);
			
			// test DNF when no racers in running queue
			assertFalse(race1.handleRacerDNF());
			
			// test normal DNF
			assertTrue(race1.start(""));
			assertTrue(race1.start(""));
			assertEquals(0, race1.getStartList().size());
			assertEquals(2, race1.getRunningList().size());
			assertEquals(234, race1.getRunningList().get(0).getNumber());
			assertEquals(315, race1.getRunningList().get(1).getNumber());
			assertTrue(race1.handleRacerDNF());
			assertEquals(1, race1.getRunningList().size());
			assertEquals(1, race1.getFinishList().size());
			assertEquals(315, race1.getRunningList().get(0).getNumber());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			assertTrue(race1.getFinishList().get(0).getDNF());
		}
	}
}