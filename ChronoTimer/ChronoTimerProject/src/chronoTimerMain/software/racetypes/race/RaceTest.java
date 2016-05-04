package chronoTimerMain.software.racetypes.race;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import chronoTimerMain.software.Timer.Timer;
import chronoTimerMain.software.racetypes.RaceGRP;
import chronoTimerMain.software.racetypes.RaceIND;
import chronoTimerMain.software.racetypes.RacePARIND;

public class RaceTest {
	RaceIND rI;
	RacePARIND rPI;
	RaceGRP rG;

	@Before
	public void initialize() {
		rI = new RaceIND(1, new Timer());
		rI.setChannelToggles(true);
		rI.num("1");
		rI.num("2");
		rI.num("3");
		
		rPI = new RacePARIND(2, new Timer());
		rPI.setChannelToggles(true);
		rPI.num("1");
		rPI.num("2");
		rPI.num("3");
		rPI.num("4");
		
		rG = new RaceGRP(3, new Timer());
		rG.setChannelToggles(true);
	}
	
	@Test
	public void testGetRunNumber() {
		assertEquals(1, rI.getRunNumber());
		assertEquals(2, rPI.getRunNumber());
		assertEquals(3, rG.getRunNumber());
	}
	@Test
	public void testSetRunNumber() {
		rI.setRunNumber(4);
		assertEquals(4, rI.getRunNumber());
		rPI.setRunNumber(5);
		assertEquals(5, rPI.getRunNumber());
		rG.setRunNumber(6);
		assertEquals(6, rG.getRunNumber());
	}
	@Test
	public void testGetTimer() {
		assertTrue(rI.getTimer() != null);
		assertTrue(rPI.getTimer() != null);
		assertTrue(rG.getTimer() != null);
	}
	@Test
	public void testGetStartList() {
		assertEquals(3, rI.getStartList().size());
		assertEquals("1", rI.getStartList().get(0).getNumber());
		assertEquals("2", rI.getStartList().get(1).getNumber());
		assertEquals("3", rI.getStartList().get(2).getNumber());
		
		assertEquals(4, rPI.getStartList().size());
		assertEquals("1", rPI.getStartList().get(0).getNumber());
		assertEquals("2", rPI.getStartList().get(1).getNumber());
		assertEquals("3", rPI.getStartList().get(2).getNumber());
		assertEquals("4", rPI.getStartList().get(3).getNumber());
		
		assertEquals(0, rG.getStartList().size());
	}
	@Test
	public void testGetRunningList() {
		rI.trig(1, "00:00:05.0");
		rI.trig(1, "00:00:10.0");
		rI.trig(1, "00:00:15.0");
		assertEquals(3, rI.getRunningList().size());
		assertEquals("1", rI.getRunningList().get(0).getNumber());
		assertEquals("2", rI.getRunningList().get(1).getNumber());
		assertEquals("3", rI.getRunningList().get(2).getNumber());
		
		rPI.trig(1, "00:00:05.0");
		rPI.trig(3, "00:00:05.0");
		rPI.trig(1, "00:00:10.0");
		rPI.trig(3, "00:00:10.0");
		assertEquals(4, rPI.getRunningList().size());
		assertEquals("1", rPI.getRunningList().get(0).getNumber());
		assertEquals("2", rPI.getRunningList().get(1).getNumber());
		assertEquals("3", rPI.getRunningList().get(2).getNumber());
		assertEquals("4", rPI.getRunningList().get(3).getNumber());
		
		assertEquals(0, rG.getRunningList().size());
		
	}
	@Test
	public void testGetFinishList() {
		rI.trig(1, "00:00:05.0");
		rI.trig(1, "00:00:10.0");
		rI.trig(1, "00:00:15.0");
		rI.trig(2, "00:00:20.0");
		rI.trig(2, "00:00:30.0");
		rI.trig(2, "00:00:40.0");
		assertEquals(3, rI.getFinishList().size());
		assertEquals("1", rI.getFinishList().get(0).getNumber());
		assertEquals("2", rI.getFinishList().get(1).getNumber());
		assertEquals("3", rI.getFinishList().get(2).getNumber());
		
		rPI.trig(1, "00:00:05.0");
		rPI.trig(3, "00:00:05.0");
		rPI.trig(1, "00:00:10.0");
		rPI.trig(3, "00:00:10.0");
		rPI.trig(2, "00:00:20.0");
		rPI.trig(4, "00:00:21.0");
		rPI.trig(2, "00:00:30.0");
		rPI.trig(4, "00:00:31.0");
		assertEquals(4, rPI.getFinishList().size());
		assertEquals("1", rPI.getFinishList().get(0).getNumber());
		assertEquals("2", rPI.getFinishList().get(1).getNumber());
		assertEquals("3", rPI.getFinishList().get(2).getNumber());
		assertEquals("4", rPI.getFinishList().get(3).getNumber());
		
		rG.trig(1, "00:00:05.0");
		rG.trig(2, "00:00:25.0");
		rG.trig(2, "00:00:30.0");
		rG.trig(2, "00:00:35.0");
		assertEquals(3, rG.getFinishList().size());
		assertEquals("00001", rG.getFinishList().get(0).getNumber());
		assertEquals("00002", rG.getFinishList().get(1).getNumber());
		assertEquals("00003", rG.getFinishList().get(2).getNumber());
	}
	@Test
	public void testGetRacerDuration() {
		assertEquals("Racer 0 not found", rI.getRacerDuration("0"));
		assertEquals("00:00:00.0", rI.getRacerDuration("1"));
		assertEquals("00:00:00.0", rI.getRacerDuration("2"));
		assertEquals("00:00:00.0", rI.getRacerDuration("3"));
		rI.trig(1, "00:00:05.0");
		rI.trig(1, "00:00:10.0");
		rI.trig(1, "00:00:15.0");
		rI.trig(2, "00:00:20.0");
		rI.trig(2, "00:00:30.0");
		rI.trig(2, "00:00:40.0");
		assertEquals("00:00:15.0", rI.getRacerDuration("1"));
		assertEquals("00:00:20.0", rI.getRacerDuration("2"));
		assertEquals("00:00:25.0", rI.getRacerDuration("3"));
		
		assertEquals("00:00:00.0", rPI.getRacerDuration("1"));
		assertEquals("00:00:00.0", rPI.getRacerDuration("2"));
		assertEquals("00:00:00.0", rPI.getRacerDuration("3"));
		assertEquals("00:00:00.0", rPI.getRacerDuration("4"));
		rPI.trig(1, "00:00:05.0");
		rPI.trig(3, "00:00:05.0");
		rPI.trig(1, "00:00:10.0");
		rPI.trig(3, "00:00:10.0");
		rPI.trig(2, "00:00:20.0");
		rPI.trig(4, "00:00:21.0");
		rPI.trig(2, "00:00:30.0");
		rPI.trig(4, "00:00:31.0");
		assertEquals("00:00:15.0", rPI.getRacerDuration("1"));
		assertEquals("00:00:16.0", rPI.getRacerDuration("2"));
		assertEquals("00:00:20.0", rPI.getRacerDuration("3"));
		assertEquals("00:00:21.0", rPI.getRacerDuration("4"));
		
		rG.trig(1, "00:00:05.0");
		rG.trig(2, "00:00:25.0");
		rG.trig(2, "00:00:30.0");
		rG.trig(2, "00:00:35.0");
		assertEquals("00:00:20.0", rG.getRacerDuration("00001"));
		assertEquals("00:00:25.0", rG.getRacerDuration("00002"));
		assertEquals("00:00:30.0", rG.getRacerDuration("00003"));
	}
	@Test
	public void testGetCorrectRacer() {
		assertEquals(rI.getStartList().get(0), rI.getCorrectRacer("1"));
		assertEquals(rI.getStartList().get(1), rI.getCorrectRacer("2"));
		assertEquals(rI.getStartList().get(2), rI.getCorrectRacer("3"));
		rI.trig(1, "00:00:05.0");
		rI.trig(1, "00:00:10.0");
		rI.trig(1, "00:00:15.0");
		assertEquals(rI.getRunningList().get(0), rI.getCorrectRacer("1"));
		assertEquals(rI.getRunningList().get(1), rI.getCorrectRacer("2"));
		assertEquals(rI.getRunningList().get(2), rI.getCorrectRacer("3"));
		rI.trig(2, "00:00:20.0");
		rI.trig(2, "00:00:30.0");
		rI.trig(2, "00:00:40.0");
		assertEquals(rI.getFinishList().get(0), rI.getCorrectRacer("1"));
		assertEquals(rI.getFinishList().get(1), rI.getCorrectRacer("2"));
		assertEquals(rI.getFinishList().get(2), rI.getCorrectRacer("3"));
		
		assertEquals(rPI.getStartList().get(0), rPI.getCorrectRacer("1"));
		assertEquals(rPI.getStartList().get(1), rPI.getCorrectRacer("2"));
		assertEquals(rPI.getStartList().get(2), rPI.getCorrectRacer("3"));
		assertEquals(rPI.getStartList().get(3), rPI.getCorrectRacer("4"));
		rPI.trig(1, "00:00:05.0");
		rPI.trig(3, "00:00:05.0");
		rPI.trig(1, "00:00:10.0");
		rPI.trig(3, "00:00:10.0");
		assertEquals(rPI.getRunningList().get(0), rPI.getCorrectRacer("1"));
		assertEquals(rPI.getRunningList().get(1), rPI.getCorrectRacer("2"));
		assertEquals(rPI.getRunningList().get(2), rPI.getCorrectRacer("3"));
		assertEquals(rPI.getRunningList().get(3), rPI.getCorrectRacer("4"));
		rPI.trig(2, "00:00:20.0");
		rPI.trig(4, "00:00:21.0");
		rPI.trig(2, "00:00:30.0");
		rPI.trig(4, "00:00:31.0");
		assertEquals(rPI.getFinishList().get(0), rPI.getCorrectRacer("1"));
		assertEquals(rPI.getFinishList().get(1), rPI.getCorrectRacer("2"));
		assertEquals(rPI.getFinishList().get(2), rPI.getCorrectRacer("3"));
		assertEquals(rPI.getFinishList().get(3), rPI.getCorrectRacer("4"));
		
		assertEquals(null, rG.getCorrectRacer("00001"));
		rG.trig(1, "00:00:05.0");
		assertEquals(null, rG.getCorrectRacer("00001"));
		rG.trig(2, "00:00:25.0");
		rG.trig(2, "00:00:30.0");
		rG.trig(2, "00:00:35.0");
		assertEquals(rG.getFinishList().get(0), rG.getCorrectRacer("00001"));
		assertEquals(rG.getFinishList().get(1), rG.getCorrectRacer("00002"));
		assertEquals(rG.getFinishList().get(2), rG.getCorrectRacer("00003"));
	}
	@Test
	public void testEndRun() {
		rI.endRun();
		assertTrue(rI.getFinishList().get(0).getDNF());
		assertTrue(rI.getFinishList().get(1).getDNF());
		assertTrue(rI.getFinishList().get(2).getDNF());
		
		rPI.endRun();
		assertTrue(rPI.getFinishList().get(0).getDNF());
		assertTrue(rPI.getFinishList().get(1).getDNF());
		assertTrue(rPI.getFinishList().get(2).getDNF());
		assertTrue(rPI.getFinishList().get(3).getDNF());
		
		rG.trig(1, "00:00:05.0");
		rG.trig(2, "00:00:25.0");
		rG.handleRacerDNF();
		rG.trig(2, "00:00:30.0");
		rG.trig(2, "00:00:35.0");
		rG.endRun();
		assertFalse(rG.getFinishList().get(0).getDNF());
		assertTrue(rG.getFinishList().get(1).getDNF());
		assertFalse(rG.getFinishList().get(2).getDNF());
		assertFalse(rG.getFinishList().get(3).getDNF());
	}
	@Test
	public void testPrint() {
		rI.trig(1, "00:00:05.0");
		rI.trig(1, "00:00:10.0");
		rI.trig(2, "00:00:20.0");
		rI.trig(2, "00:00:30.0");
		String expectedOutput = String.format("     %s     %s\n     %s     %s\n\n", "1", "00:00:15.0", "2", "00:00:20.0");
		assertEquals(expectedOutput, rI.print());
		
		rPI.trig(1, "00:00:05.0");
		rPI.trig(3, "00:00:05.0");
		rPI.trig(2, "00:00:20.0");
		rPI.trig(4, "00:00:21.0");
		expectedOutput = String.format("     %s     %s\n     %s     %s\n\n", "1", "00:00:15.0", "2", "00:00:16.0");
		assertEquals(expectedOutput, rPI.print());
		
		rG.trig(1, "00:00:05.0");
		rG.trig(2, "00:00:25.0");
		rG.handleRacerDNF();
		rG.trig(2, "00:00:30.0");
		expectedOutput = String.format("     %s     %s\n     %s     %s\n     %s     %s\n\n", "00001", "00:00:20.0", "00002", "DNF",
				"00003", "00:00:25.0");
		assertEquals(expectedOutput, rG.print());
	}
}