package chronoTimerMain.software.racetypes;

import java.util.ArrayList;

import com.sun.glass.ui.Pixels.Format;


import chronoTimerMain.software.Timer.Timer;
import chronoTimerMain.software.racetypes.race.Race;
import junit.framework.TestCase;

/**
 * Represents a single race of the individual event type.
 * single	start	(signaled	on	channel	1)	and	
 * a	series	of	single	finishes	(first,	second,	third,	etc)	
 * that	are	signaled	on	channel	2
 */
public class RaceGRP extends Race {
	private int startChannel;
	private int finishChannel;
	private int numChanged;

	/**
	 * Creates a group race
	 * @param run number for the race
	 * @param Timer object used for the race
	 */
	public RaceGRP(int runNumber, Timer timer) {
		super(runNumber, timer);
		this.startChannel = 1;
		this.finishChannel = 2;
		this.numChanged = 0;
	}

	/**
	 * Creates an individual race with an existing start list of racers.
	 * @param runNumber
	 * @param timer
	 * @param startList
	 */
	public RaceGRP(int runNumber, Timer timer, ArrayList<Racer> startList) {
		super(runNumber, timer, startList);
		this.startChannel = 1;
		this.finishChannel = 2;
	}

	/**
	 * Change the racer number of the next finished racer from the default
	 * @param racer number of racer to be modified
	 * @return true if change was successful, else false
	 */
	// TODO ask jason/whoever about DNFS added after finish.
	@Override
	public boolean num(String racerNum) {
		boolean result = false;
		ArrayList<Racer> finishList = super.getFinishList();
		if(finishList.size() > 0 && finishList.size() > numChanged){
			finishList.get(numChanged).setNumber(racerNum);
			numChanged ++;
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
	public boolean removeRacerFromStart(String racerNum) {
		return false;
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
		ArrayList<Racer> finishList = super.getFinishList();
		

		if (channelNum != this.startChannel && channelNum != this.finishChannel)
			return false;
		
		// check channel is enabled
		if(super.getChannelToggles()[channelNum]){
			// if trigger on start channel, startTime is set
			if (channelNum == this.startChannel) {
				// do nothing if race already started (startTime was already set)
				if (startTime == null) {
					if (timeStamp.equals("")){
						startTime = super.getTimer().getCurrentChronoTime();
					}
					else{
						startTime = timeStamp;
					}
				}
			}

			// if trigger on finish channel, set finish time of a new racer
			else {
				Racer john = new Racer(String.format("%05d", finishList.size()+1));
				john.setStartTime(startTime);
				if (timeStamp.equals("")){
					john.setFinishTime(super.getTimer().getCurrentChronoTime());
				}
				else{
					john.setFinishTime(timeStamp);
				}
				finishList.add(john);
			}
			result = true;
		}
		return result;
	}

	/**
	 * Trigger default start channel
	 * @param time stamp from file input, null if console input
	 * @return true if startTime was set, false if not
	 */
	@Override
	public boolean start(String timeStamp) {
		return trig(this.startChannel, timeStamp);
	}

	/**
	 * Trigger default finish channel
	 * @param time stamp from file input, null if console input
	 * @return true if a racer was added to the finish list, false if not
	 */
	@Override
	public boolean finish(String timeStamp) {
		return trig(this.finishChannel, timeStamp);
	}

	/**
	 * does nothing for Group fun
	 * @return true if a racer was handled, else false
	 */
	@Override
	public boolean handleRacerCancel() {
		return false;
	}

	/**
	 * does nothing because this is a group event...and I have no idea how
	 * it should be done (says yang) (real quote here)
	 * @return false because there is no need to swap stuff
	 */
	@Override
	public boolean swapRunningRacers() {
		return false;
	}

	/**
	 * returns false as we cant do this in a group
	 * @return true if a racer was handled, else false
	 */
	@Override
	public boolean handleRacerDNF() {
		return false;
	}

	public static class raceGRPTester extends TestCase{
		RaceGRP race1, race2;
		Timer timer;

		@Override
		protected void setUp() {
			timer = new Timer();
			race1 = new RaceGRP(1,timer);
			race2 = new RaceGRP(2,timer);
			race1.setChannelToggles(true);
			race2.setChannelToggles(true);
		}	
		public void testGRP(){
			//tests adding num to finish list before start
			assertFalse(race1.num("word"));
			//tests trig
			assertFalse(race1.trig(0, ""));
			assertFalse(race1.trig(3, ""));
			assertTrue(race1.startTime == null);
			assertTrue(race1.trig(1, ""));
			assertFalse(race1.startTime == null);
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			
			assertTrue(race1.trig(2, ""));
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(1, race1.getFinishList().size());
			
			assertTrue(race1.getFinishList().get(0).getNumber().equals("00001"));
			//tests adding num to finish list after race
			assertTrue(race1.num("word"));
			assertTrue(race1.getFinishList().get(0).getNumber().equals("word"));
			
		}
		public void testStart(){
			assertTrue(race1.startTime == null);
			assertTrue(race1.start(""));
			assertEquals(0, race1.getStartList().size());
			assertEquals(0, race1.getRunningList().size());
			assertEquals(0, race1.getFinishList().size());
			assertFalse(race1.startTime == null);
			
		}
		public void testFinish(){
			assertTrue(race2.start(""));
			assertEquals(0, race2.getFinishList().size());
			
			assertTrue(race2.finish(""));
			assertEquals(0, race2.getStartList().size());
			assertEquals(0, race2.getRunningList().size());
			assertEquals(1, race2.getFinishList().size());
			assertTrue(race2.finish(""));
			assertTrue(race2.finish(""));
			assertEquals(0, race2.getStartList().size());
			assertEquals(0, race2.getRunningList().size());
			assertEquals(3, race2.getFinishList().size());
			assertTrue(race2.getFinishList().get(0).getNumber().equals("00001"));
			assertTrue(race2.getFinishList().get(1).getNumber().equals("00002"));
			assertTrue(race2.getFinishList().get(2).getNumber().equals("00003"));
		}
	}
}