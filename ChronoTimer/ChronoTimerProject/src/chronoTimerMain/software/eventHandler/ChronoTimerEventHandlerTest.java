package chronoTimerMain.software.eventHandler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import chronoTimerMain.software.Timer.Timer;
import chronoTimerMain.software.racetypes.RaceIND;
import chronoTimerMain.software.racetypes.RacePARIND;
import chronoTimerMain.software.racetypes.race.Race;

public class ChronoTimerEventHandlerTest {
	Timer timer;
	Race race;
	int runNumber;
	ChronoTimerEventHandler cTEV;
	private ArrayList<Race> raceList = new ArrayList<Race>();
	
	public ChronoTimerEventHandlerTest() {
		timer = new Timer();
		cTEV = new ChronoTimerEventHandler(timer);
		runNumber = 1;
		cTEV.race = new RaceIND(runNumber, timer);
		raceList.add(race);
	}
	
	@Before
	public void initialize() {
		timer = new Timer();
		cTEV = new ChronoTimerEventHandler(timer);
		runNumber = 1;
		cTEV.race = new RaceIND(runNumber, timer);
		raceList.add(race);
		
	}
	
	@Test
	//Tests that json export and retrieval restores the race class
	public void testExportAndImport() {
		cTEV.timeEvent("num", new String[] {"123"}, "");
		cTEV.timeEvent("export", new String[] {Integer.toString(runNumber)}, "");
		cTEV.timeEvent("newRun", new String[] {}, "");
		//rebuild exported object
		cTEV.timeEvent("import", new String[] {Integer.toString(runNumber)}, "");
		assertTrue(cTEV.race.getCorrectRacer(123) != null);
	}
	
	@Test
	public void testEvent() {
		assertTrue(cTEV.race instanceof RaceIND); 
		//TODO other race types
		//test IND and various text cases
		cTEV.timeEvent("event", new String[] {"IND"}, "");
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertTrue(cTEV.race instanceof RaceIND); 
		cTEV.timeEvent("event", new String[] {"ind"}, "");
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertTrue(cTEV.race instanceof RaceIND); 
		cTEV.timeEvent("event", new String[] {"Ind"}, "");
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertTrue(cTEV.race instanceof RaceIND); 
		//test PARIND and various text cases
		cTEV.timeEvent("event", new String[] {"PARIND"}, "");
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertTrue(cTEV.race instanceof RacePARIND); 
		cTEV.timeEvent("event", new String[] {"parind"}, "");
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertTrue(cTEV.race instanceof RacePARIND); 
		cTEV.timeEvent("event", new String[] {"Parind"}, "");
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertTrue(cTEV.race instanceof RacePARIND); 
		//test invalid types
		cTEV.timeEvent("event", new String[] {"INVALID"}, "");
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertTrue(cTEV.race instanceof RacePARIND); 
		cTEV.timeEvent("event", new String[] {""}, "");
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertTrue(cTEV.race instanceof RacePARIND); 
		
	}
	
	@Test
	public void testNewRun() {
		//test new run and correct run numbers
		int rNum = cTEV.runNumber;
		Timer timer = cTEV.timer;
		Race initialRace = cTEV.race;
		race = cTEV.race;
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertFalse(race == cTEV.race);
		assertTrue((rNum+1) == cTEV.runNumber);
		//test that timer is passed to new race
		assertTrue(timer == cTEV.timer);
		//test that raceList has a new entry
		assertTrue(cTEV.raceList.get(1) == initialRace);
		
	}
	
	@Test
	public void testDisplay() {
		//test that the correct racers and times are displayed - start, running, finish queue.
	}
}
