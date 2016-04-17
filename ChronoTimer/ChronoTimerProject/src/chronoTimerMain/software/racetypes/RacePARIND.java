package chronoTimerMain.software.racetypes;

import java.util.ArrayList;

import junit.framework.TestCase;
import chronoTimerMain.simulator.hardwareHandler.ChronoHardwareHandler;
import chronoTimerMain.software.Timer.Timer;
import chronoTimerMain.software.racetypes.race.Race;

/**
 * Represents a single race of the parallel individual event type.
 * TODO: refactor to use more RaceIND methods once the channel ambiguity is cleared up
 */
public class RacePARIND extends Race {
	// a parallel individual race can be considered as two separate individual races,
	// each with their own start and finish channels
	private RaceIND raceA;
	private RaceIND raceB;
	
	/**
	 * Creates a parallel individual race
	 * @param run number for the race
	 * @param Timer object used for the race
	 */
	public RacePARIND(int runNumber, Timer timer) {
		super(runNumber, timer);
		this.raceA = new RaceIND(runNumber, timer);
		this.raceB = new RaceIND(runNumber, timer);
		raceA.setStartChannel(1);
		raceA.setFinishChannel(2);
		raceB.setStartChannel(3);
		raceB.setFinishChannel(4);
	}
	
	/**
	 * Creates a parallel individual race with an existing start list
	 * @param runNumber
	 * @param timer
	 * @param startList
	 */
	public RacePARIND(int runNumber, Timer timer, ArrayList<Racer> startList) {
		super(runNumber, timer, startList);
		this.raceA = new RaceIND(runNumber, timer);
		this.raceB = new RaceIND(runNumber, timer);
		raceA.setStartChannel(1);
		raceA.setFinishChannel(2);
		raceB.setStartChannel(3);
		raceB.setFinishChannel(4);
		for (Racer racer : startList) {
			// odd racers gets added to race A, even racers gets added to race B, etc.
			if ((startList.size() % 2) != 0)
				this.raceA.getStartList().add(racer);
			else
				this.raceB.getStartList().add(racer);
		}
	}
	
	/**
	 * Gets race A of the two parallel races
	 * @return race 1 object
	 */
	public RaceIND getRaceA() {
		return this.raceA;
	}
	
	/**
	 * Gets race B of the two parallel races
	 * @return race 2 object
	 */
	public RaceIND getRaceB() {
		return this.raceB;
	}

	/**
	 * Gets the start channel associated with race A
	 * @return start channel number
	 */
	public int getStartChannelA() {
		return raceA.getStartChannel();
	}
	
	/**
	 * Gets the start channel associated with race B
	 * @return start channel number
	 */
	public int getStartChannelB() {
		return raceB.getStartChannel();
	}
	
	/**
	 * Gets the finish channel associated with race A
	 * @return finish channel number
	 */
	public int getFinishChannelA() {
		return raceA.getFinishChannel();
	}
	
	/**
	 * Gets the finish channel associated with race B
	 * @return finish channel number
	 */
	public int getFinishChannelB() {
		return raceB.getFinishChannel();
	}
	
	/**
	 * Add a new racer to the end of the start queue
	 * Alternate between adding racers to race A and race B
	 * @param racer number of racer to be added
	 * @return true if add was successful, else false
	 */
	@Override
	public boolean num(String racerNum) {
		boolean result = false;
		Racer racer = null;
		ArrayList<Racer> startList = super.getStartList();
		if (super.getCorrectRacer(racerNum) == null) { // check if racer is already in start queue
			racer = new Racer(racerNum);
			startList.add(racer);
			// first racer gets added to race A, second racer gets added to race B, etc.
			if ((startList.size() % 2) != 0)
				this.raceA.getStartList().add(racer);
			else
				this.raceB.getStartList().add(racer);
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
	public boolean removeRacerFromStart(String racerNum) {
		boolean result = false;
		ArrayList<Racer> startList = super.getStartList();
		Racer racer = super.getCorrectRacer(racerNum);
		if (racer != null && startList.contains(racer)) {
			startList.remove(racer);
			ArrayList<Racer>startListA = this.raceA.getStartList();
			ArrayList<Racer>startListB = this.raceB.getStartList();
			// also remove racer from race 1 or race 2 start queue
			if (startListA.contains(racer))
				startListA.remove(racer);
			else
				startListB.remove(racer);
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
		ArrayList<Racer> startListA = this.raceA.getStartList();
		ArrayList<Racer> runningListA = this.raceA.getRunningList();
		ArrayList<Racer> finishListA = this.raceA.getFinishList();
		ArrayList<Racer> startListB = this.raceB.getStartList();
		ArrayList<Racer> runningListB = this.raceB.getRunningList();
		ArrayList<Racer> finishListB = this.raceB.getFinishList();
		Racer racer = null;
		boolean[] channelToggleArray = super.getChannelToggles();
		
		if (channelNum > 12 || channelToggleArray[channelNum] == false)
			return false;
		
		// if there are racers in the start queue, a start event should move the racer at the head of the start queue
		// into the running queue
		if (channelNum == this.raceA.getStartChannel() && startListA.size() > 0) {
			racer = startListA.remove(0);
			startList.remove(racer);
			if (timeStamp.equals(""))
				racer.setStartTime(super.getTimer().getCurrentChronoTime());
			else
				racer.setStartTime(timeStamp);
			runningList.add(racer);
			runningListA.add(racer);
			result = true;
		}
		if (channelNum == this.raceB.getStartChannel() && startListB.size() > 0) {
			racer = startListB.remove(0);
			startList.remove(racer);
			if (timeStamp.equals(""))
				racer.setStartTime(super.getTimer().getCurrentChronoTime());
			else
				racer.setStartTime(timeStamp);
			runningList.add(racer);
			runningListB.add(racer);
			result = true;
		}
		// if there are racers in the running queue, a finish event should move the racer at the head of the running
		// queue into the finish queue
		if (channelNum == this.raceA.getFinishChannel() && runningListA.size() > 0) {
			racer = runningListA.remove(0);
			runningList.remove(racer);
			if (timeStamp.equals(""))
				racer.setFinishTime(super.getTimer().getCurrentChronoTime());
			else
				racer.setFinishTime(timeStamp);
			finishList.add(racer);
			finishListA.add(racer);
			result = true;
		}
		if (channelNum == this.raceB.getFinishChannel() && runningListB.size() > 0) {
			racer = runningListB.remove(0);
			runningList.remove(racer);
			if (timeStamp.equals(""))
				racer.setFinishTime(super.getTimer().getCurrentChronoTime());
			else
				racer.setFinishTime(timeStamp);
			finishList.add(racer);
			finishListB.add(racer);
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
		ArrayList<Racer> startListA = this.raceA.getStartList();
		ArrayList<Racer> startListB = this.raceB.getStartList();
		// the next person to start is the person at the head of the
		// overall start queue
		if (startList.size() > 0) {
			racer = startList.get(0);
			if (startListA.contains(racer)) {
				result = trig(this.raceA.getStartChannel(), timeStamp);
			}
			else if (startListB.contains(racer)) {
				result = trig(this.raceB.getStartChannel(), timeStamp);
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
		ArrayList<Racer> runningListA = this.raceA.getRunningList();
		ArrayList<Racer> runningListB = this.raceB.getRunningList();
		// the next person to finish is the person at the head of the
		// overall running queue
		if (runningList.size() > 0) {
			racer = runningList.get(0);
			if (runningListA.contains(racer)) {
				result = trig(this.raceA.getFinishChannel(), timeStamp);
			}
			else if (runningListB.contains(racer)) {
				result = trig(this.raceB.getFinishChannel(), timeStamp);
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
		ArrayList<Racer> runningListA = this.raceA.getRunningList();
		ArrayList<Racer> runningListB = this.raceB.getRunningList();
		ArrayList<Racer> startList = super.getStartList();
		ArrayList<Racer> startListA = this.raceA.getStartList();
		ArrayList<Racer> startListB = this.raceB.getStartList();
		if (runningList.size() > 0) {
			Racer racer = runningList.remove(runningList.size()-1);
			racer.setStartTime("00:00:00.0");
			startList.add(0, racer);
			if (runningListA.contains(racer)) {
				runningListA.remove(racer);
				startListA.add(0, racer);
			}
			else if (runningListB.contains(racer)) {
				runningListB.remove(racer);
				startListB.add(0, racer);
			}
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
		ArrayList<Racer> runningListA = this.raceA.getRunningList();
		ArrayList<Racer> runningListB = this.raceB.getRunningList();
		ArrayList<Racer> finishList = super.getFinishList();
		ArrayList<Racer> finishListA = this.raceA.getFinishList();
		ArrayList<Racer> finishListB = this.raceB.getFinishList();
		Racer racer = null;
		if (runningList.size() > 0) {
			racer = runningList.remove(0);
			racer.setDNF(true);
			finishList.add(racer);
			if (runningListA.contains(racer)) {
				runningListA.remove(racer);
				finishListA.add(racer);
			}
			else if (runningListB.contains(racer)) {
				runningListB.remove(racer);
				finishListB.add(racer);
			}
			result = true;
		}
		return result;
	}
	
	public static class racePARINDTester extends TestCase{
		RacePARIND race1, race2;
		Timer timer;
		
		@Override
		protected void setUp() {
			timer = new Timer();
			race1 = new RacePARIND(1,timer);
			race2 = new RacePARIND(2,timer);
		}
		
		public void testnum() {
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(0, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			
			assertTrue(race1.num("234"));
			assertEquals(1, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(1, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(0, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(234, race1.getStartList().get(0).getNumber());
			assertEquals(234, race1.getRaceA().getStartList().get(0).getNumber());
			
			assertTrue(race1.num("315"));
			assertEquals(2, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(1, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(1, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(234, race1.getStartList().get(0).getNumber());
			assertEquals(315, race1.getStartList().get(1).getNumber());
			assertEquals(234, race1.getRaceA().getStartList().get(0).getNumber());
			assertEquals(315, race1.getRaceB().getStartList().get(0).getNumber());
			
			assertTrue(race1.num("361"));
			assertEquals(3, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(2, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(1, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(234, race1.getStartList().get(0).getNumber());
			assertEquals(315, race1.getStartList().get(1).getNumber());
			assertEquals(361, race1.getStartList().get(2).getNumber());
			assertEquals(234, race1.getRaceA().getStartList().get(0).getNumber());
			assertEquals(361, race1.getRaceA().getStartList().get(1).getNumber());
			assertEquals(315, race1.getRaceB().getStartList().get(0).getNumber());
			
			assertTrue(race1.num("431"));
			assertEquals(4, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(2, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(2, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(234, race1.getStartList().get(0).getNumber());
			assertEquals(315, race1.getStartList().get(1).getNumber());
			assertEquals(361, race1.getStartList().get(2).getNumber());
			assertEquals(431, race1.getStartList().get(3).getNumber());
			assertEquals(234, race1.getRaceA().getStartList().get(0).getNumber());
			assertEquals(361, race1.getRaceA().getStartList().get(1).getNumber());
			assertEquals(315, race1.getRaceB().getStartList().get(0).getNumber());
			assertEquals(431, race1.getRaceB().getStartList().get(1).getNumber());
			
			// add duplicate racer
			assertFalse(race1.num("234"));
			assertEquals(4, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(2, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(2, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(234, race1.getStartList().get(0).getNumber());
			assertEquals(315, race1.getStartList().get(1).getNumber());
			assertEquals(361, race1.getStartList().get(2).getNumber());
			assertEquals(431, race1.getStartList().get(3).getNumber());
			assertEquals(234, race1.getRaceA().getStartList().get(0).getNumber());
			assertEquals(361, race1.getRaceA().getStartList().get(1).getNumber());
			assertEquals(315, race1.getRaceB().getStartList().get(0).getNumber());
			assertEquals(431, race1.getRaceB().getStartList().get(1).getNumber());
		}

		public void testRemoveRacerFromStart() {
			race1.num("234");
			race1.num("315");
			
			// remove non-existent racer
			assertFalse(race1.removeRacerFromStart("111"));
			assertEquals(2, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(234, race1.getStartList().get(0).getNumber());
			assertEquals(315, race1.getStartList().get(1).getNumber());
			assertEquals(1, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(1, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(234, race1.getRaceA().getStartList().get(0).getNumber());
			assertEquals(315, race1.getRaceB().getStartList().get(0).getNumber());
			
			// valid remove
			assertTrue(race1.removeRacerFromStart("234"));
			assertEquals(1, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(315, race1.getStartList().get(0).getNumber());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(1, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(315, race1.getRaceB().getStartList().get(0).getNumber());
			
			assertTrue(race1.removeRacerFromStart("315"));
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(0, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
		}

		public void testTrig() {
			// test conventional start/finish channel setup
			assertEquals(1, race1.getRaceA().getStartChannel());
			assertEquals(2, race1.getRaceA().getFinishChannel());
			assertEquals(3, race1.getRaceB().getStartChannel());
			assertEquals(4, race1.getRaceB().getFinishChannel());
			
			// trigger when no racers in start queue
			assertFalse(race1.trig(1, ""));
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(0, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			
			// add racers
			race1.num("234");
			race1.num("315");
			// trigger channel 1 (racer starts in race A)
			assertTrue(race1.trig(1, ""));
			assertEquals(1, race1.getStartList().size());
			assertEquals(315, race1.getStartList().get(0).getNumber());
			assertEquals(1, race1.getRunningList().size());
			assertEquals(234, race1.getRunningList().get(0).getNumber());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(1, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(234, race1.getRaceA().getRunningList().get(0).getNumber());
			assertEquals(1, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(315, race1.getRaceB().getStartList().get(0).getNumber());
			
			// trigger channel 2 (racer finishes in race A)
			assertTrue(race1.trig(2, ""));
			assertEquals(1, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(1, race1.getFinishList().size());
			assertEquals(315, race1.getStartList().get(0).getNumber());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(1, race1.getRaceA().getFinishList().size());
			assertEquals(234, race1.getRaceA().getFinishList().get(0).getNumber());
			assertEquals(1, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(315, race1.getRaceB().getStartList().get(0).getNumber());
			
			// trigger channel 5 (not start or finish)
			assertFalse(race1.trig(5, ""));
			assertEquals(1, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(1, race1.getFinishList().size());
			assertEquals(315, race1.getStartList().get(0).getNumber());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(1, race1.getRaceA().getFinishList().size());
			assertEquals(234, race1.getRaceA().getFinishList().get(0).getNumber());
			assertEquals(1, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(315, race1.getRaceB().getStartList().get(0).getNumber());
			
			// trigger channel 3 (racer starts in race B)
			assertTrue(race1.trig(3, ""));
			assertEquals(0, race1.getStartList().size());
			assertEquals(1, race1.getRunningList().size());
			assertEquals(1, race1.getFinishList().size());
			assertEquals(315, race1.getRunningList().get(0).getNumber());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(1, race1.getRaceA().getFinishList().size());
			assertEquals(234, race1.getRaceA().getFinishList().get(0).getNumber());
			assertEquals(0, race1.getRaceB().getStartList().size());
			assertEquals(1, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(315, race1.getRaceB().getRunningList().get(0).getNumber());
			
			// trigger channel 4 (racer finishes in race B)
			assertTrue(race1.trig(4, ""));
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(2, race1.getFinishList().size());
			assertEquals(315, race1.getFinishList().get(1).getNumber());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(1, race1.getRaceA().getFinishList().size());
			assertEquals(234, race1.getRaceA().getFinishList().get(0).getNumber());
			assertEquals(0, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(1, race1.getRaceB().getFinishList().size());
			assertEquals(315, race1.getRaceB().getFinishList().get(0).getNumber());
			
			// trigger channels 1 and 2 (no more racers to start and finish)
			assertFalse(race1.trig(1, ""));
			assertFalse(race1.trig(2, ""));
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(2, race1.getFinishList().size());
			assertEquals(315, race1.getFinishList().get(1).getNumber());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(1, race1.getRaceA().getFinishList().size());
			assertEquals(234, race1.getRaceA().getFinishList().get(0).getNumber());
			assertEquals(0, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(1, race1.getRaceB().getFinishList().size());
			assertEquals(315, race1.getRaceB().getFinishList().get(0).getNumber());
			
			race2.num("167");
			race2.num("177");
			race2.num("200");
			race2.num("201");
			
			// trigger multiple consecutive starts
			assertTrue(race2.trig(1, ""));
			assertTrue(race2.trig(3, ""));
			assertTrue(race2.trig(1, ""));
			assertTrue(race2.trig(3, ""));
			assertEquals(0, race2.getStartList().size());
			assertEquals(4, race2.getRunningList().size());
			assertEquals(0, race2.getFinishList().size());
			assertEquals(167, race2.getRunningList().get(0).getNumber());
			assertEquals(177, race2.getRunningList().get(1).getNumber());
			assertEquals(200, race2.getRunningList().get(2).getNumber());
			assertEquals(201, race2.getRunningList().get(3).getNumber());
			assertEquals(0, race2.getRaceA().getStartList().size());
			assertEquals(2, race2.getRaceA().getRunningList().size());
			assertEquals(0, race2.getRaceA().getFinishList().size());
			assertEquals(167, race2.getRaceA().getRunningList().get(0).getNumber());
			assertEquals(200, race2.getRaceA().getRunningList().get(1).getNumber());
			assertEquals(0, race2.getRaceB().getStartList().size());
			assertEquals(2, race2.getRaceB().getRunningList().size());
			assertEquals(0, race2.getRaceB().getFinishList().size());
			assertEquals(177, race2.getRaceB().getRunningList().get(0).getNumber());
			assertEquals(201, race2.getRaceB().getRunningList().get(1).getNumber());
			
			// trigger multiple consecutive finishes
			assertTrue(race2.trig(2, ""));
			assertTrue(race2.trig(4, ""));
			assertTrue(race2.trig(2, ""));
			assertTrue(race2.trig(4, ""));
			assertEquals(0, race2.getStartList().size());
			assertEquals(0, race2.getRunningList().size());
			assertEquals(4, race2.getFinishList().size());
			assertEquals(167, race2.getFinishList().get(0).getNumber());
			assertEquals(177, race2.getFinishList().get(1).getNumber());
			assertEquals(200, race2.getFinishList().get(2).getNumber());
			assertEquals(201, race2.getFinishList().get(3).getNumber());
			assertEquals(0, race2.getRaceA().getStartList().size());
			assertEquals(0, race2.getRaceA().getRunningList().size());
			assertEquals(2, race2.getRaceA().getFinishList().size());
			assertEquals(167, race2.getRaceA().getFinishList().get(0).getNumber());
			assertEquals(200, race2.getRaceA().getFinishList().get(1).getNumber());
			assertEquals(0, race2.getRaceB().getStartList().size());
			assertEquals(0, race2.getRaceB().getRunningList().size());
			assertEquals(2, race2.getRaceB().getFinishList().size());
			assertEquals(177, race2.getRaceB().getFinishList().get(0).getNumber());
			assertEquals(201, race2.getRaceB().getFinishList().get(1).getNumber());
			
			// test redundant starts and finishes
			// trig 1, 2, 2, 3, 3, 4, 4
			race2 = new RacePARIND(2, timer);
			race2.num("167");
			race2.num("177");
			race2.num("200");
			race2.num("201");
			assertTrue(race2.trig(1, ""));
			assertTrue(race2.trig(2, ""));
			assertFalse(race2.trig(2, ""));
			assertTrue(race2.trig(3, ""));
			assertTrue(race2.trig(3, ""));
			assertTrue(race2.trig(4, ""));
			assertTrue(race2.trig(4, ""));
			assertEquals(1, race2.getStartList().size());
			assertEquals(3, race2.getFinishList().size());
			// race A should have 167 in finish, 200 in start
			assertEquals(1, race2.getRaceA().getStartList().size());
			assertEquals(1, race2.getRaceA().getFinishList().size());
			assertEquals(167, race2.getRaceA().getFinishList().get(0).getNumber());
			assertEquals(200, race2.getRaceA().getStartList().get(0).getNumber());
			// race B should have 177, 201 in finish
			assertEquals(0, race2.getRaceB().getStartList().size());
			assertEquals(2, race2.getRaceB().getFinishList().size());
			assertEquals(177, race2.getRaceB().getFinishList().get(0).getNumber());
			assertEquals(201, race2.getRaceB().getFinishList().get(1).getNumber());
		}

		public void testStart() {
			race1.num("234");
			race1.num("315");
			assertTrue(race1.start(""));
			assertEquals(1, race1.getStartList().size());
			assertEquals(315, race1.getStartList().get(0).getNumber());
			assertEquals(1, race1.getRunningList().size());
			assertEquals(234, race1.getRunningList().get(0).getNumber());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(1, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(234, race1.getRaceA().getRunningList().get(0).getNumber());
			assertEquals(1, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(315, race1.getRaceB().getStartList().get(0).getNumber());
			
			assertTrue(race1.start(""));
			assertEquals(0, race1.getStartList().size());
			assertEquals(2, race1.getRunningList().size());
			assertEquals(234, race1.getRunningList().get(0).getNumber());
			assertEquals(315, race1.getRunningList().get(1).getNumber());
			assertEquals(0, race1.getFinishList().size());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(1, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(234, race1.getRaceA().getRunningList().get(0).getNumber());
			assertEquals(0, race1.getRaceB().getStartList().size());
			assertEquals(1, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(315, race1.getRaceB().getRunningList().get(0).getNumber());
		}

		public void testFinish() {
			race1.num("234");
			race1.num("315");
			assertTrue(race1.start(""));
			assertTrue(race1.start(""));
			assertTrue(race1.finish(""));
			assertTrue(race1.finish(""));
			assertFalse(race1.finish(""));
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(2, race1.getFinishList().size());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			assertEquals(315, race1.getFinishList().get(1).getNumber());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(1, race1.getRaceA().getFinishList().size());
			assertEquals(234, race1.getRaceA().getFinishList().get(0).getNumber());
			assertEquals(0, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(1, race1.getRaceB().getFinishList().size());
			assertEquals(315, race1.getRaceB().getFinishList().get(0).getNumber());
		}

		public void testCancel() {
			race1.num("234");
			race1.num("315");
			
			// test cancel when no racers in running queue
			assertFalse(race1.handleRacerCancel());
			
			// test normal cancel
			assertTrue(race1.start(""));
			assertTrue(race1.start(""));
			assertTrue(race1.handleRacerCancel());
			assertEquals(1, race1.getStartList().size());
			assertEquals(1, race1.getRunningList().size());
			assertEquals(234, race1.getRunningList().get(0).getNumber());
			assertEquals(315, race1.getStartList().get(0).getNumber());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(1, race1.getRaceA().getRunningList().size());
			assertEquals(0, race1.getRaceA().getFinishList().size());
			assertEquals(234, race1.getRaceA().getRunningList().get(0).getNumber());
			assertEquals(1, race1.getRaceB().getStartList().size());
			assertEquals(0, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(315, race1.getRaceB().getStartList().get(0).getNumber());
			assertEquals("00:00:00.0", race1.getRaceB().getStartList().get(0).getStartTime());
		}
		
		public void testSwap() {
			assertFalse(race1.swapRunningRacers());
		}

		public void testDNF() {
			race1.num("234");
			race1.num("315");
			
			// test DNF when no racers in running queue
			assertFalse(race1.handleRacerDNF());
			
			// test normal DNF
			assertTrue(race1.start(""));
			assertTrue(race1.start(""));
			assertTrue(race1.handleRacerDNF());
			assertEquals(1, race1.getRunningList().size());
			assertEquals(1, race1.getFinishList().size());
			assertEquals(315, race1.getRunningList().get(0).getNumber());
			assertEquals(234, race1.getFinishList().get(0).getNumber());
			assertTrue(race1.getFinishList().get(0).getDNF());
			assertEquals(0, race1.getRaceA().getStartList().size());
			assertEquals(0, race1.getRaceA().getRunningList().size());
			assertEquals(1, race1.getRaceA().getFinishList().size());
			assertEquals(234, race1.getRaceA().getFinishList().get(0).getNumber());
			assertTrue(race1.getRaceA().getFinishList().get(0).getDNF());
			assertEquals(0, race1.getRaceB().getStartList().size());
			assertEquals(1, race1.getRaceB().getRunningList().size());
			assertEquals(0, race1.getRaceB().getFinishList().size());
			assertEquals(315, race1.getRaceB().getRunningList().get(0).getNumber());
		}
	}

}