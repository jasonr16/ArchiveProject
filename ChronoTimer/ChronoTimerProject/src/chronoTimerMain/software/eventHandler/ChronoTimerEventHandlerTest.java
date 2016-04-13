package chronoTimerMain.software.eventHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	
	public ChronoTimerEventHandlerTest() {
		timer = new Timer();
		cTEV = new ChronoTimerEventHandler(timer);
		runNumber = 1;
		cTEV.race = new RaceIND(runNumber, timer);
	}
	
	@Before
	public void initialize() {
		timer = new Timer();
		cTEV = new ChronoTimerEventHandler(timer);
		runNumber = 1;
		cTEV.race = new RaceIND(runNumber, timer);//create race conditions
		cTEV.timeEvent("on", new String[] {""}, "");
		cTEV.timeEvent("time", new String[] {"01:01:00.0"}, "01:01:00.0");
		
		cTEV.timeEvent("conn", new String[] {"gate", "1"}, "");
		cTEV.timeEvent("conn", new String[] {"gate", "2"}, "");
		cTEV.timeEvent("tog", new String[] {"1"}, "");
		cTEV.timeEvent("tog", new String[] {"2"}, "");
	}
	
	@Test
	//Tests that json export and retrieval restores the race class
	public void testExportAndImport() {
		cTEV.timeEvent("num", new String[] {"123"}, "");
		cTEV.timeEvent("num", new String[] {"234"}, "");
		cTEV.timeEvent("export", new String[] {Integer.toString(runNumber)}, "");
		
		cTEV.timeEvent("newRun", new String[] {}, "");
		//test null racer
		assertTrue(cTEV.race.getCorrectRacer(123) == null);
		assertTrue(cTEV.race.getCorrectRacer(234) == null);
	
		cTEV.timeEvent("import", new String[] {Integer.toString(runNumber)}, "");
		//test racer imported
		assertTrue(cTEV.race.getCorrectRacer(123) != null);
		assertTrue(cTEV.race.getCorrectRacer(234) != null);
		//test non-existing racer is null
		assertTrue(cTEV.race.getCorrectRacer(345) == null);
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
		assertEquals(1, cTEV.raceList.size());//default race stored at index zero
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertFalse(race == cTEV.race);
		assertTrue((rNum+1) == cTEV.runNumber);
		//test that timer is passed to new race
		assertTrue(timer == cTEV.timer);
		//test that raceList has a new entry
		assertTrue(cTEV.raceList.get(1) == initialRace);
		assertEquals(2, cTEV.raceList.size());
		//test second race
		Race secondRace = cTEV.race;
		cTEV.timeEvent("newRun", new String[] {}, "");
		assertTrue(cTEV.raceList.get(2) == secondRace);
		assertEquals(3, cTEV.raceList.size());
		
	}
	
	@Test
	public void testDisplay() {
		String correctDisplay = "";
		//test IND race type
		//test one start racer
		cTEV.timeEvent("num", new String[] {"123"}, "01:01:01.0");
		cTEV.timeEvent("display", new String[] {}, "01:01:01.0");
		correctDisplay = cTEV.race.getStartList().get(0).getNumber() + " " + 
						cTEV.timer.getRunDuration(cTEV.race.getStartList().get(0).getStartTime(), "01:01:01.0") +  " >\n\n\n";
		assertEquals(correctDisplay, cTEV.display);
		
		//TODO test 4 start racers
		cTEV.timeEvent("num", new String[] {"234"}, "01:01:02.2");
		cTEV.timeEvent("num", new String[] {"345"}, "01:01:03.3");
		cTEV.timeEvent("num", new String[] {"456"}, "01:01:04.4");
		
		correctDisplay = cTEV.race.getCorrectRacer(345).getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer(345).getStartTime(), "01:01:05.1")+ "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer(234).getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer(234).getStartTime(), "01:01:05.1")+ "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer(123).getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer(123).getStartTime(), "01:01:05.1") + " >\n\n\n";
		cTEV.timeEvent("display", new String[] {}, "01:01:05.1");
		
		assertEquals(correctDisplay, cTEV.display);
		//test IND Racer
		cTEV.timeEvent("start", new String[] {""}, "01:01:06.0");
		//345 and 456 in start, 123 and 234 in running
		correctDisplay = cTEV.race.getCorrectRacer(456).getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer(456).getStartTime(), "01:01:10.0")+ "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer(345).getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer(345).getStartTime(), "01:01:10.0") + "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer(234).getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer(234).getStartTime(), "01:01:10.0")+ " >\n\n";
		
		correctDisplay += cTEV.race.getCorrectRacer(123).getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer(123).getStartTime(), "01:01:10.0") + " R\n\n";
		cTEV.timeEvent("display", new String[] {}, "01:01:10.0");
		
		assertEquals(correctDisplay, cTEV.display);
		
		//test 1 finish racer
		//test second finish racer
		cTEV.timeEvent("num", new String[] {"123"}, "");
		cTEV.timeEvent("num", new String[] {"123"}, "");
		//test PARIND race type
		//TODO Jason test GRP race type
	}
}
