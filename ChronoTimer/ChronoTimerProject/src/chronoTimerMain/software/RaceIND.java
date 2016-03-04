package chronoTimerMain.software;

import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.*;

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
	 * Gets the finish channel associated with the race
	 * @return finish channel number
	 */
	public int getFinishChannel() {
		return this.finishChannel;
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
		if (super.getCorrectRacer(racerNum) == null) {
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
	 * @return true if a racer was moved from one queue to another, false if not
	 */
	@Override
	public boolean trig(int channelNum) {
		boolean result = false;
		ArrayList<Racer> startList = super.getStartList();
		ArrayList<Racer> runningList = super.getRunningList();
		ArrayList<Racer> finishList = super.getFinishList();
		Racer racer = null;
		
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
			racer.setStartTime(super.getTimer().getCurrentChronoTime());
			runningList.add(racer);
			result = true;
		}
		// if there are racers in the running queue, a finish event should move the racer at the head of the running
		// queue into the finish queue
		else if (channelNum == this.finishChannel && runningList.size() > 0) {
			finishList.add(runningList.remove(0));
			result = true;
		}
		return result;
	}
	
	/**
	 * Trigger default start channel
	 * @return true if a racer was moved from one queue to another, false if not
	 */
	@Override
	public boolean start() {
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
		boolean result = false;
		if (this.finishChannel == 0)
			result = trig(2);
		else
			result = trig(this.finishChannel);
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
			assertFalse(race1.trig(3));
			assertEquals(0, race1.getStartChannel());
			assertEquals(0, race1.getFinishChannel());
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			
			// trigger channel 1 (first racer starts)
			race1.addRacerToStart(234);
			race1.addRacerToStart(315);
			assertTrue(race1.trig(1));
			assertEquals(1, race1.getStartChannel());
			assertEquals(1, race1.getStartList().size());
			assertEquals(315, race1.getStartList().get(0).getNumber());
			assertEquals(1, race1.getRunningList().size());
			assertEquals(234, race1.getRunningList().get(0).getNumber());
			assertEquals(0, race1.getFinishList().size());
			
			// trigger channel 2 (first racer finishes)
			assertTrue(race1.trig(2));
			assertEquals(1, race1.getStartChannel());
			assertEquals(2, race1.getFinishChannel());
			assertEquals(1, race1.getStartList().size());
			assertEquals(315, race1.getStartList().get(0).getNumber());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(1, race1.getFinishList().size());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			
			// trigger channel 3 (not start or finish)
			assertFalse(race1.trig(3));
			assertEquals(1, race1.getStartChannel());
			assertEquals(2, race1.getFinishChannel());
			assertEquals(1, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(1, race1.getFinishList().size());
			
			// trigger channel 1 (second racer starts)
			assertTrue(race1.trig(1));
			assertEquals(1, race1.getStartChannel());
			assertEquals(2, race1.getFinishChannel());
			assertEquals(0, race1.getStartList().size());
			assertEquals(1, race1.getRunningList().size());
			assertEquals(315, race1.getRunningList().get(0).getNumber());
			assertEquals(1, race1.getFinishList().size());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			
			// trigger channel 2 (second racer finishes)
			assertTrue(race1.trig(2));
			assertEquals(1, race1.getStartChannel());
			assertEquals(2, race1.getFinishChannel());
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(2, race1.getFinishList().size());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			assertEquals(315, race1.getFinishList().get(1).getNumber());
			
			// trigger channels 1 and 2 (no more racers)
			assertFalse(race1.trig(1));
			assertFalse(race1.trig(2));
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
			assertTrue(race2.trig(3));
			assertTrue(race2.trig(3));
			assertTrue(race2.trig(3));
			assertTrue(race2.trig(3));
			assertEquals(3, race2.getStartChannel());
			assertEquals(0, race2.getFinishChannel());
			assertEquals(0, race2.getStartList().size());
			assertEquals(4, race2.getRunningList().size());
			assertEquals(167, race2.getRunningList().get(0).getNumber());
			assertEquals(177, race2.getRunningList().get(1).getNumber());
			assertEquals(200, race2.getRunningList().get(2).getNumber());
			assertEquals(201, race2.getRunningList().get(3).getNumber());
			assertEquals(0, race2.getFinishList().size());
			
			// trigger multiple finishes
			assertTrue(race2.trig(4));
			assertTrue(race2.trig(4));
			assertTrue(race2.trig(4));
			assertTrue(race2.trig(4));
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
		
		public void testHandleRacerDNF() {
			// no racers
			assertFalse(race1.handleRacerDNF());
			
			// no racers running
			race1.addRacerToStart(234);
			assertFalse(race1.handleRacerDNF());
		}
	}
}