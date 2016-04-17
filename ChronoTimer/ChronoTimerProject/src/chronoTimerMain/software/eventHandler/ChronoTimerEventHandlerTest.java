package chronoTimerMain.software.eventHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import chronoTimerMain.software.Timer.Timer;
import chronoTimerMain.software.racetypes.RaceGRP;
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
		cTEV.timeEvent("conn", new String[] {"gate", "3"}, "");
		cTEV.timeEvent("conn", new String[] {"gate", "4"}, "");
		cTEV.race.setTogsToTrue();
	}
	
	@Test
	//Tests that json export and retrieval restores the race class
	public void testExportAndImport() {
		cTEV.timeEvent("num", new String[] {"1"}, "");
		cTEV.timeEvent("num", new String[] {"2"}, "");
		cTEV.timeEvent("export", new String[] {Integer.toString(runNumber)}, "");
		
		cTEV.timeEvent("newRun", new String[] {}, "");
		//test null racer
		assertTrue(cTEV.race.getCorrectRacer("1") == null);
		assertTrue(cTEV.race.getCorrectRacer("2") == null);
	
		cTEV.timeEvent("import", new String[] {Integer.toString(runNumber)}, "");
		//test racer imported
		assertTrue(cTEV.race.getCorrectRacer("1") != null);
		assertTrue(cTEV.race.getCorrectRacer("2") != null);
		//test non-existing racer is null
		assertTrue(cTEV.race.getCorrectRacer("3") == null);
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
	public void testDisplayIND() {
		String correctDisplay = "";

		//test one start racer
		cTEV.timeEvent("num", new String[] {"1"}, "01:01:01.0");
		cTEV.timeEvent("display", new String[] {}, "01:01:01.0");
		correctDisplay = cTEV.race.getStartList().get(0).getNumber() + " " + 
						cTEV.timer.getRunDuration(cTEV.race.getStartList().get(0).getStartTime(), "01:01:01.0") +  " >\n\n\n";
		assertEquals(correctDisplay, cTEV.display);
		
		//test 4 start racers
		cTEV.timeEvent("num", new String[] {"2"}, "01:01:02.2");
		cTEV.timeEvent("num", new String[] {"3"}, "01:01:03.3");
		cTEV.timeEvent("num", new String[] {"4"}, "01:01:04.4");
		
		correctDisplay = cTEV.race.getCorrectRacer("3").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("3").getStartTime(), "01:01:05.1")+ "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("2").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("2").getStartTime(), "01:01:05.1")+ "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("1").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("1").getStartTime(), "01:01:05.1") + " >\n\n\n";
		cTEV.timeEvent("display", new String[] {}, "01:01:05.1");
		
		assertEquals(correctDisplay, cTEV.display);
		//test IND Racer
		cTEV.timeEvent("start", new String[] {""}, "01:01:06.0");
		//2, 3, and 4 in start, 1 in running
		correctDisplay = cTEV.race.getCorrectRacer("4").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("4").getStartTime(), "01:01:10.0")+ "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("3").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("3").getStartTime(), "01:01:10.0") + "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("2").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("2").getStartTime(), "01:01:10.0")+ " >\n\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("1").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("1").getStartTime(), "01:01:10.0") + " R\n\n";
		cTEV.timeEvent("display", new String[] {}, "01:01:10.0");
		
		assertEquals(correctDisplay, cTEV.display);
		
		//test 1 finish racer
		cTEV.timeEvent("finish", new String[] {""}, "01:01:11.0");
		//2, 3, 4 in start. 1 in finish
		correctDisplay = cTEV.race.getCorrectRacer("4").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("4").getStartTime(), "01:01:12.5")+ "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("3").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("3").getStartTime(), "01:01:12.5") + "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("2").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("2").getStartTime(), "01:01:12.5")+ " >\n\n\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("1").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("1").getStartTime(), "01:01:12.5") + " F\n";
		
		cTEV.timeEvent("display", new String[] {}, "01:01:12.5");
		
		assertEquals(correctDisplay, cTEV.display);
		
		//test start, running, and finish
		cTEV.timeEvent("start", new String[] {""}, "01:01:11.0");
		cTEV.timeEvent("finish", new String[] {""}, "01:01:15.0");
		cTEV.timeEvent("start", new String[] {""}, "01:01:15.1");
		
		//4 in start, 3 running, 1 and 2 finished
		correctDisplay = cTEV.race.getCorrectRacer("4").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("4").getStartTime(), "01:01:16.6")+ " >\n\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("3").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("3").getStartTime(), "01:01:16.6") + " R\n\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("2").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("2").getStartTime(), "01:01:16.6")+ " F\n";
		
		cTEV.timeEvent("display", new String[] {""}, "01:01:16.6");
		
		assertEquals(correctDisplay, cTEV.display);
		
		//test all finished
		cTEV.timeEvent("finish", new String[] {""}, "01:01:18.8");
		cTEV.timeEvent("start", new String[] {""}, "01:01:19.9");
		cTEV.timeEvent("finish", new String[] {""}, "01:01:20.0");
		
		correctDisplay = "\n\n" + cTEV.race.getCorrectRacer("4").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("4").getStartTime(), "01:01:21.0")+ " F\n";
		
		cTEV.timeEvent("display", new String[] {""}, "01:01:21.0");

		assertEquals(correctDisplay, cTEV.display);

	}
		
	@Test
	public void testDisplayPARIND() {
		String correctDisplay = "";
		cTEV.race = new RacePARIND(++runNumber, cTEV.timer);
		cTEV.race.setTogsToTrue();
		//test one start racer
		cTEV.timeEvent("num", new String[] {"1"}, "01:01:01.0");
		cTEV.timeEvent("display", new String[] {}, "01:01:01.0");
		correctDisplay = cTEV.race.getStartList().get(0).getNumber() + " " + 
						cTEV.timer.getRunDuration(cTEV.race.getStartList().get(0).getStartTime(), "01:01:01.0") +  " >\n\n\n";
		assertEquals(correctDisplay, cTEV.display);
		//test 4 start racers
		cTEV.timeEvent("num", new String[] {"2"}, "01:01:02.2");
		cTEV.timeEvent("num", new String[] {"3"}, "01:01:03.3");
		cTEV.timeEvent("num", new String[] {"4"}, "01:01:04.4");
		
		correctDisplay = cTEV.race.getCorrectRacer("3").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("3").getStartTime(), "01:01:05.1")+ "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("2").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("2").getStartTime(), "01:01:05.1")+ "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("1").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("1").getStartTime(), "01:01:05.1") + " >\n\n\n";
		cTEV.timeEvent("display", new String[] {}, "01:01:05.1");
		
		assertEquals(correctDisplay, cTEV.display);
		//test PARIND Racers
		cTEV.timeEvent("start", new String[] {""}, "01:01:06.0");
		cTEV.timeEvent("start", new String[] {""}, "01:01:06.5");
		
		correctDisplay = cTEV.race.getCorrectRacer("4").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("4").getStartTime(), "01:01:10.0")+ "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("3").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("3").getStartTime(), "01:01:10.0") + " >\n\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("2").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("2").getStartTime(), "01:01:10.0")+ " R\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("1").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("1").getStartTime(), "01:01:10.0") + " R\n\n";
		
		cTEV.timeEvent("display", new String[] {}, "01:01:10.0");
		
		assertEquals(correctDisplay, cTEV.display);
		//test 1 finish racer
		
		cTEV.timeEvent("finish", new String[] {""}, "01:01:12.0");
		
		correctDisplay = cTEV.race.getCorrectRacer("4").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("4").getStartTime(), "01:01:15.5")+ "\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("3").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("3").getStartTime(), "01:01:15.5") + " >\n\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("2").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("2").getStartTime(), "01:01:15.5")+ " R\n\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("1").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("1").getStartTime(), "01:01:15.5") + " F\n";
		
		cTEV.timeEvent("display", new String[] {}, "01:01:15.5");
		assertTrue(cTEV.race.getFinishList().size() > 0);
		assertEquals(correctDisplay, cTEV.display);
		//test start, running, and finish
		cTEV.timeEvent("finish", new String[] {""}, "01:01:20.2");
		cTEV.timeEvent("start", new String[] {""}, "01:01:20.2");
		
		correctDisplay = cTEV.race.getCorrectRacer("4").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("4").getStartTime(), "01:01:22.5")+ " >\n\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("3").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("3").getStartTime(), "01:01:22.5") + " R\n\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("2").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("2").getStartTime(), "01:01:22.5")+ " F\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("1").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("1").getStartTime(), "01:01:22.5") + " F\n";
		
		cTEV.timeEvent("display", new String[] {}, "01:01:22.5");
		
		assertEquals(correctDisplay, cTEV.display);
		//test all finished
		cTEV.timeEvent("start", new String[] {""}, "01:01:25.2");
		cTEV.timeEvent("finish", new String[] {""}, "01:01:26.2");
		cTEV.timeEvent("finish", new String[] {""}, "01:01:26.2");
		
		correctDisplay = "\n\n" + cTEV.race.getCorrectRacer("4").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("4").getStartTime(), "01:01:30.5")+ " F\n";
		
		correctDisplay += cTEV.race.getCorrectRacer("3").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("3").getStartTime(), "01:01:30.5") + " F\n";
		
		cTEV.timeEvent("display", new String[] {}, "01:01:30.5");
		
		assertEquals(correctDisplay, cTEV.display);
	}
	@Test
	public void testDisplayGRP() {
		cTEV.race = new RaceGRP(++runNumber, cTEV.timer);
		cTEV.race.setTogsToTrue();
		//test 4 start racers
		cTEV.timeEvent("num", new String[] {"1"}, "01:01:02.2");
		cTEV.timeEvent("num", new String[] {"2"}, "01:01:03.3");
		cTEV.timeEvent("num", new String[] {"3"}, "01:01:04.4");
		cTEV.timeEvent("num", new String[] {"4"}, "01:01:05.5");
		
		String correctDisplay = "00000 01:01:10.5\n";
		cTEV.timeEvent("display", new String[] {}, "01:01:10.5");
		
		assertEquals(correctDisplay, cTEV.display);//no starts displayed
		
		//test all running
		cTEV.timeEvent("start", new String[] {}, "01:01:11.0");
		correctDisplay = "00000 00:00:00.0\n";
		cTEV.timeEvent("display", new String[] {}, "01:01:11.0");
		assertEquals(correctDisplay, cTEV.display);
		
		//test 1 finish
		cTEV.timeEvent("finish", new String[] {}, "01:01:12.0");
		
		correctDisplay = "00000 00:00:11.5\n";
		correctDisplay += cTEV.race.getCorrectRacer("00001").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("00001").getStartTime(), "01:01:22.5")+ " F\n";
		
		cTEV.timeEvent("display", new String[] {}, "01:01:22.5");
		assertEquals(correctDisplay, cTEV.display);
		//test 2 finish
		cTEV.timeEvent("finish", new String[] {}, "01:01:25.0");
		
		correctDisplay = "00000 00:00:20.5\n";
		correctDisplay += cTEV.race.getCorrectRacer("00002").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("00002").getStartTime(), "01:01:31.5")+ " F\n";
		
		cTEV.timeEvent("display", new String[] {}, "01:01:31.5");
		assertEquals(correctDisplay, cTEV.display);
		//test change to num
		cTEV.timeEvent("num", new String[] {"3"}, "01:01:04.4");
		cTEV.timeEvent("num", new String[] {"4"}, "01:01:05.5");
		correctDisplay = "00000 00:00:21.0\n";
		correctDisplay += cTEV.race.getCorrectRacer("4").getNumber() + " " + 
				cTEV.timer.getRunDuration(cTEV.race.getCorrectRacer("4").getStartTime(), "01:01:32.0")+ " F\n";
		
		cTEV.timeEvent("display", new String[] {}, "01:01:32.0");
		assertEquals(correctDisplay, cTEV.display);
	}
}
