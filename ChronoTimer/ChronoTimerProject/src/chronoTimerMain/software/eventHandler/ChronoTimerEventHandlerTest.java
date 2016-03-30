package chronoTimerMain.software.eventHandler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import chronoTimerMain.software.Race;
import chronoTimerMain.software.RaceIND;
import chronoTimerMain.software.RacePARIND;
import chronoTimerMain.software.Timer.Timer;

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
	public void testExport() {
		cTEV.timeEvent("num", new String[] {"123"}, "");
		cTEV.timeEvent("export", new String[] {Integer.toString(runNumber)}, "");
		cTEV.timeEvent("newRun", new String[] {}, "");
		//rebuild exported object
		cTEV.timeEvent("import", new String[] {Integer.toString(runNumber)}, "");
		assertTrue(cTEV.race.getCorrectRacer(123) != null);
	}
	
	@Test
	public void testRaceINDType() {
		assertTrue(cTEV.race instanceof RaceIND); 
		cTEV.timeEvent("event", new String[] {"IND"}, "");
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertTrue(cTEV.race instanceof RaceIND); 
		cTEV.timeEvent("event", new String[] {"PARIND"}, "");
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertTrue(cTEV.race instanceof RacePARIND); 
		cTEV.timeEvent("event", new String[] {"INVALID"}, "");
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertTrue(cTEV.race instanceof RacePARIND); 
		//TODO other race types
	}
	
	@Test
	public void testNewRunCreated() {
		race = cTEV.race;
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertFalse(race == cTEV.race);
	}
	
	@Test
	public void testRemainingDNF() {
		cTEV.timeEvent("num", new String[] {"123"}, "");
		cTEV.race.endRun();
		assertTrue(cTEV.race.getFinishList().size() != 0);
	}
}
