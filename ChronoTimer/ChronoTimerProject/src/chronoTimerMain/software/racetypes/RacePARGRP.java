package chronoTimerMain.software.racetypes;

import java.util.ArrayList;

import com.sun.glass.ui.Pixels.Format;


import chronoTimerMain.software.Timer.Timer;
import chronoTimerMain.software.racetypes.race.Race;
import junit.framework.TestCase;

/**
 * Represents a single race of the parallel group event type.
 * Single common start	(signaled	on	channel	1)	and	
 * a	multiple finishes with different finish lanes
 * that	are	signaled on channels 1-8
 * TODO: have to check that finish channels must be connected to SensorPads
 * TODO: if channel is not connected to a SensorPad or Sensor, mark DNF for racer
 * TODO: if channel is connected but racer is not assigned to that channel, ignore
 */
public class RacePARGRP extends Race {
	private int startChannel;
	private Racer[] lanes;

	/**
	 * Creates a parallel group race
	 * @param run number for the race
	 * @param Timer object used for the race
	 */
	public RacePARGRP(int runNumber, Timer timer) {
		super(runNumber, timer);
		this.startChannel = 1;
		this.lanes = new Racer[9];
	}

	/**
	 * TODO: What was this for again? Creates an parallel group race with an existing start list of racers.
	 * @param runNumber
	 * @param timer
	 * @param startList
	 */
	public RacePARGRP(int runNumber, Timer timer, ArrayList<Racer> startList) {
		super(runNumber, timer, startList);
		this.startChannel = 1;
		this.lanes = new Racer[9];
	}
	
	public Racer[] getLanes() {
		return this.lanes;
	}

	/**
	 * Add a racer to start in the correct lane/channel
	 * @param racer number of racer to be modified
	 * @return true if change was successful, else false
	 */
	@Override
	public boolean num(String racerNum) {
		boolean result = false;
		Racer racer = null;
		ArrayList<Racer> startList = super.getStartList();
		// check if racer is already in start queue and that lanes are not full
		if (super.getCorrectRacer(racerNum) == null && lanes[8] == null) {
			racer = new Racer(racerNum);
			startList.add(racer);
			// first racer gets added to lane 1, second racer gets added to lane 2, etc.
			for (int i = 1; i < lanes.length; ++i) {
				if (lanes[i] == null) {
					lanes[i] = racer;
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Remove the last racer added to start
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
			for (int i = 1; i <= lanes.length; ++i) {
				if (lanes[i] == racer) {
					lanes[i] = null;
					break;
				}
			}
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
		boolean[] channelToggleArray = super.getChannelToggles();
		
		// check channel number is in range
		if (channelNum > 8 || channelNum < 1)
			return false;
		
		// check start event (race hasn't started (start time hasn't been set in Race,
		// racers haven't been moved from startList to runningList yet and 
		// lanes array should be all null)
		if (channelNum == this.startChannel && super.getStartTime() == null) {
			// move all racers in start list to running list
			if (timeStamp.equals(""))
				super.startTime = super.getTimer().getCurrentChronoTime();
			else
				super.startTime = timeStamp;
			while(!(startList.size() == 0)) {
				racer = startList.remove(0);
				// set each racers' start times
				racer.setStartTime(super.getStartTime());
				runningList.add(racer);
			}
			result = true;
		}
		// finish event (trigger on channel 1-8 and start time was already set)
		else if (super.getStartTime() != null) {
			// if channel is not connected to a pad or not toggled on, and there is a racer
			// in that lane, set that racer to DNF
			if (lanes[channelNum] != null) {
				racer = lanes[channelNum];
				if (channelToggleArray[channelNum] == false || 
						super.sensors[channelNum] == null) {
					racer.setDNF(true);
					runningList.remove(racer);
					finishList.add(racer);
				}
				else {
					// set the racer's finish time
					if(racer.getFinishTime().equals("00:00:00.0")) {//only first finish on each trigger should be processed
						if (timeStamp.equals(""))
							racer.setFinishTime(super.getTimer().getCurrentChronoTime());
						else
							racer.setFinishTime(timeStamp);
						
						// move the racer in the corresponding lane to the finish list
						runningList.remove(racer);
						finishList.add(racer);
						result = true;
					}
				}
			} 
		}
		return result;
	}

	/**
	 * Trigger start event (should only happen once)
	 * @param time stamp from file input, null if console input
	 * @return true if startTime was set, false if not
	 */
	@Override
	public boolean start(String timeStamp) {
		boolean result = false;
		if (super.startTime == null){
			trig(this.startChannel, timeStamp);
			result = true;
		}
		return result;
	}

	/**
	 * Trigger finish event (shouldn't work for parallel group races because
	 * need to specify a lane/channel number
	 * @param time stamp from file input, null if console input
	 * @return true if a racer was added to the finish list, false if not
	 */
	@Override
	public boolean finish(String timeStamp) {
		return false;
	}

	/**
	 * Cancel the latest racer that started, does nothing for Group races
	 * @return true if a racer was handled, else false
	 */
	@Override
	public boolean handleRacerCancel() {
		return false;
	}

	/**
	 * Swap does nothing because only one racer per lane, and finishes are triggered individually
	 * for each lane
	 * @return false because there is no need to swap stuff
	 */
	@Override
	public boolean swapRunningRacers() {
		return false;
	}

	/**
	 * DNF does nothing because there is no "next" racer to finish
	 * @return true if a racer was handled, else false
	 */
	@Override
	public boolean handleRacerDNF() {
		return false;
	}

	public static class racePARGRPTester extends TestCase{
		RacePARGRP race;
		Timer timer;
		Racer[] lanes;

		@Override
		protected void setUp() {
			timer = new Timer();
			race = new RacePARGRP(1,timer);
			lanes = race.getLanes();
		}	
		
		public void testNum() {
			assertEquals(0, race.getStartList().size());
			assertEquals(0, race.getRunningList().size());
			assertEquals(0, race.getFinishList().size());
			for (int i = 0; i < lanes.length; ++i) {
				assertEquals(null, lanes[i]);			
			}
			
			assertTrue(race.num("234"));
			assertEquals(1, race.getStartList().size());
			assertEquals(0, race.getRunningList().size());
			assertEquals(0, race.getFinishList().size());
			assertEquals("234", race.getStartList().get(0).getNumber());
			assertEquals("234", race.getLanes()[1].getNumber());
			
			assertTrue(race.num("315"));
			assertEquals(2, race.getStartList().size());
			assertEquals(0, race.getRunningList().size());
			assertEquals(0, race.getFinishList().size());
			assertEquals("234", race.getStartList().get(0).getNumber());
			assertEquals("234", race.getLanes()[1].getNumber());
			assertEquals("315", race.getStartList().get(1).getNumber());
			assertEquals("315", race.getLanes()[2].getNumber());
			
			// add duplicate racer
			assertEquals(2, race.getStartList().size());
			assertEquals(0, race.getRunningList().size());
			assertEquals(0, race.getFinishList().size());
			assertEquals("234", race.getStartList().get(0).getNumber());
			assertEquals("234", race.getLanes()[1].getNumber());
			assertEquals("315", race.getStartList().get(1).getNumber());
			assertEquals("315", race.getLanes()[2].getNumber());
			
			assertTrue(race.num("3"));
			assertTrue(race.num("4"));
			assertTrue(race.num("5"));
			assertTrue(race.num("6"));
			assertTrue(race.num("7"));
			assertTrue(race.num("8"));
			assertFalse(race.num("9"));
			assertEquals(8, race.getStartList().size());
			assertEquals(0, race.getRunningList().size());
			assertEquals(0, race.getFinishList().size());
			assertEquals("234", race.getStartList().get(0).getNumber());
			assertEquals("234", race.getLanes()[1].getNumber());
			assertEquals("315", race.getStartList().get(1).getNumber());
			assertEquals("315", race.getLanes()[2].getNumber());
			assertEquals("3", race.getStartList().get(2).getNumber());
			assertEquals("3", race.getLanes()[3].getNumber());
			assertEquals("4", race.getStartList().get(3).getNumber());
			assertEquals("4", race.getLanes()[4].getNumber());
			assertEquals("5", race.getStartList().get(4).getNumber());
			assertEquals("5", race.getLanes()[5].getNumber());
			assertEquals("6", race.getStartList().get(5).getNumber());
			assertEquals("6", race.getLanes()[6].getNumber());
			assertEquals("7", race.getStartList().get(6).getNumber());
			assertEquals("7", race.getLanes()[7].getNumber());
			assertEquals("8", race.getStartList().get(7).getNumber());
			assertEquals("8", race.getLanes()[8].getNumber());
		}
		
		public void testRemove() {
			race.num("234");
			race.num("315");
			
			// remove non-existent racer
			assertFalse(race.removeRacerFromStart("111"));
			assertEquals(2, race.getStartList().size());
			assertEquals(0, race.getRunningList().size());
			assertEquals(0, race.getFinishList().size());
			assertEquals("234", race.getStartList().get(0).getNumber());
			assertEquals("234", race.getLanes()[1].getNumber());
			assertEquals("315", race.getStartList().get(1).getNumber());
			assertEquals("315", race.getLanes()[2].getNumber());
			
			assertTrue(race.removeRacerFromStart("234"));
			assertEquals(1, race.getStartList().size());
			assertEquals(0, race.getRunningList().size());
			assertEquals(0, race.getFinishList().size());
			assertEquals("315", race.getStartList().get(0).getNumber());
			assertEquals("315", race.getLanes()[2].getNumber());
			assertEquals(null, race.getLanes()[1]);
			
			assertTrue(race.removeRacerFromStart("315"));
			assertEquals(0, race.getStartList().size());
			assertEquals(0, race.getRunningList().size());
			assertEquals(0, race.getFinishList().size());
			assertEquals(null, race.getLanes()[1]);
			assertEquals(null, race.getLanes()[2]);
		}
		
		public void testTrig() {
			race.num("1");
			race.num("2");
			race.num("3");
			race.num("4");
			race.num("5");
			race.num("6");
			race.num("7");
			race.num("8");
			
			// test wrong start trigger
			race.trig(2, "00:00:01.0");
			assertEquals(8, race.getStartList().size());
			assertEquals(0, race.getRunningList().size());
			
			// test normal start
			race.trig(1, "00:00:01.0");
			assertEquals(0, race.getStartList().size());
			assertEquals(8, race.getRunningList().size());
			ArrayList<Racer> runningList = race.getRunningList();
			for (Racer racer : runningList) {
				assertEquals("00:00:01.0", racer.getStartTime());
			}
			for (int i = 1; i < lanes.length; ++i) {
				assertEquals("00:00:01.0", lanes[i].getStartTime());
			}
			
			// test disconnected channels
			race.trig(1, "00:00:02.0");
			race.trig(4, "00:00:03.0");
			assertEquals(6, race.getRunningList().size());
			assertEquals(2, race.getFinishList().size());
			ArrayList<Racer> finishList = race.getFinishList();
			assertTrue(finishList.get(0).getDNF());
			assertTrue(finishList.get(1).getDNF());
			assertTrue(lanes[1].getDNF());
			assertTrue(lanes[4].getDNF());
			
			// test untoggled channels
			race.setSensorsToPad();
			race.trig(2, "00:00:02.0");
			race.trig(6, "00:00:03.0");
			assertEquals(4, race.getRunningList().size());
			assertEquals(4, race.getFinishList().size());
			finishList = race.getFinishList();
			assertTrue(finishList.get(2).getDNF());
			assertTrue(finishList.get(3).getDNF());
			assertTrue(lanes[2].getDNF());
			assertTrue(lanes[6].getDNF());
			
			// test normal finishes
			race = new RacePARGRP(1,timer);
			race.num("1");
			race.num("2");
			race.num("3");
			race.num("4");
			race.num("5");
			race.num("6");
			race.num("7");
			race.num("8");
			race.setSensorsToPad();
			race.setTogsToTrue();
			race.trig(1, "00:00:01.0");
			race.trig(1, "00:00:02.0");
			race.trig(2, "00:00:03.0");
			race.trig(3, "00:00:04.0");
			race.trig(4, "00:00:05.0");
			race.trig(5, "00:00:06.0");
			race.trig(6, "00:00:07.0");
			race.trig(7, "00:00:08.0");
			race.trig(8, "00:00:09.0");
			assertEquals(0, race.getRunningList().size());
			assertEquals(8, race.getFinishList().size());
			finishList = race.getFinishList();
			lanes = race.getLanes();
			assertEquals("00:00:02.0", finishList.get(0).getFinishTime());
			assertEquals("00:00:03.0", finishList.get(1).getFinishTime());
			assertEquals("00:00:04.0", finishList.get(2).getFinishTime());
			assertEquals("00:00:05.0", finishList.get(3).getFinishTime());
			assertEquals("00:00:06.0", finishList.get(4).getFinishTime());
			assertEquals("00:00:07.0", finishList.get(5).getFinishTime());
			assertEquals("00:00:08.0", finishList.get(6).getFinishTime());
			assertEquals("00:00:09.0", finishList.get(7).getFinishTime());
			assertEquals("00:00:02.0", lanes[1].getFinishTime());
			assertEquals("00:00:03.0", lanes[2].getFinishTime());
			assertEquals("00:00:04.0", lanes[3].getFinishTime());
			assertEquals("00:00:05.0", lanes[4].getFinishTime());
			assertEquals("00:00:06.0", lanes[5].getFinishTime());
			assertEquals("00:00:07.0", lanes[6].getFinishTime());
			assertEquals("00:00:08.0", lanes[7].getFinishTime());
			assertEquals("00:00:09.0", lanes[8].getFinishTime());
			
			// test fewer racers than channels
			race = new RacePARGRP(1,timer);
			race.num("1");
			race.num("2");
			race.num("3");
			race.num("4");
			race.setSensorsToPad();
			race.setTogsToTrue();
			race.trig(1, "00:00:01.0");
			race.trig(5, "00:00:06.0");
			race.trig(6, "00:00:07.0");
			race.trig(7, "00:00:08.0");
			race.trig(8, "00:00:09.0");
			assertEquals(4, race.getRunningList().size());
			assertEquals(0, race.getFinishList().size());
		}
		
		public void testStart(){
			race.num("1");
			race.num("2");
			race.num("3");
			race.num("4");
			
			// test normal start
			assertTrue(race.start("00:00:01.0"));
			assertEquals(0, race.getStartList().size());
			assertEquals(8, race.getRunningList().size());
			
			// test second start
			assertFalse(race.start("00:00:01.0"));
			assertEquals(0, race.getStartList().size());
			assertEquals(8, race.getRunningList().size());
		}
	}
}
