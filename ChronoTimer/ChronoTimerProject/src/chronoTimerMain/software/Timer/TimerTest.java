package chronoTimerMain.software.Timer;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import chronoTimerMain.software.eventHandler.ChronoTimerEventHandler;

public class TimerTest {
	ChronoTimerEventHandler cTEH;
	Timer timer;
	LocalDateTime saveSTime;
	LocalDateTime saveCTime;
	public TimerTest() {
		timer = new Timer();
	}
	
	@Before
	public void initialize() {
		cTEH = new ChronoTimerEventHandler();
		timer = new Timer();
		saveSTime = timer.synchronizedSystemStartTime.withNano(0);
		saveCTime = timer.synchronizedChronoStartTime.withNano(0);
	}
	@Test
	public void testTimeToString() {
		//test correct string format
		timer.synchronizedChronoStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12,11,10,900000000));
		assertEquals("12:11:10.9", timer.timeToString(timer.synchronizedChronoStartTime));
		//test 0 time to string
		timer.synchronizedChronoStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0,0));
		assertEquals("00:00:00.0", timer.timeToString(timer.synchronizedChronoStartTime));
	}
	
	@Test
	public void testTime() {
		//test 0 time
		assertEquals(0, timer.synchronizedChronoStartTime.withNano(0).compareTo(
				timer.synchronizedSystemStartTime.withNano(0)));
		
		//set time for following tests
		new Time(timer, "").execute(new String[] {"02:04:45.5"});
		assertEquals("02:04:45.5", timer.timeToString(timer.synchronizedChronoStartTime));
		
		//test invalid format
		saveSTime = timer.synchronizedSystemStartTime.withNano(0);
		saveCTime = timer.synchronizedChronoStartTime.withNano(0);
		
		new Time(timer, "").execute(new String[] {"0000000000"});
		assertEquals(0, saveSTime.withNano(0).compareTo(timer.synchronizedSystemStartTime.withNano(0)));
		assertEquals(0, saveCTime.withNano(0).compareTo(timer.synchronizedChronoStartTime.withNano(0)));
		//test input too long
		saveSTime = timer.synchronizedSystemStartTime.withNano(0);
		saveCTime = timer.synchronizedChronoStartTime.withNano(0);
		
		new Time(timer, "").execute(new String[] {"00:00:00.00"});
		assertEquals(0, saveSTime.withNano(0).compareTo(timer.synchronizedSystemStartTime.withNano(0)));
		assertEquals(0, saveCTime.withNano(0).compareTo(timer.synchronizedChronoStartTime.withNano(0)));
		
		//test non-integer values
		saveSTime = timer.synchronizedSystemStartTime.withNano(0);
		saveCTime = timer.synchronizedChronoStartTime.withNano(0);
		
		new Time(timer, "").execute(new String[] {"c0:0f:0w.x"});
		assertEquals(0, saveSTime.withNano(0).compareTo(timer.synchronizedSystemStartTime.withNano(0)));
		assertEquals(0, saveCTime.withNano(0).compareTo(timer.synchronizedChronoStartTime.withNano(0)));
		
		//test integers out of bounds of time: h,m,s,S
		new Time(timer, "").execute(new String[] {"25:61:98.0"});
		assertEquals(0, saveSTime.withNano(0).compareTo(timer.synchronizedSystemStartTime.withNano(0)));
		assertEquals(0, saveCTime.withNano(0).compareTo(timer.synchronizedChronoStartTime.withNano(0)));
		
		//test system time is updated
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		new Time(timer, "").execute(new String[] {"05:05:05.5"});
		assertTrue(saveSTime.withNano(0).compareTo(timer.synchronizedSystemStartTime.withNano(0)) != 0);
		
	}
	@Test
	public void testGetRunDuration() {
		//test nonzero values for h, m, s, S
		assertEquals("01:01:01.1", timer.getRunDuration("02:02:02.1", "03:03:03.2"));
		//test zero h, m, s, S
		assertEquals("01:01:01.0", timer.getRunDuration("02:02:02.0", "03:03:03.0"));
		//test for time values that cross over into a new day
		assertEquals("00:01:02.4", timer.getRunDuration("23:59:59.0", "00:01:01.4"));
		//test invalid first arguments (same as testTime)
		assertEquals("NULL ERROR", timer.getRunDuration(null, "01:01:01.0"));
		assertEquals("PARSE ERROR", timer.getRunDuration("02:02:020.0", "01:01:01.0"));
		assertEquals("PARSE ERROR", timer.getRunDuration("0000000000", "01:01:01.0"));
		//test invalid second arguments (same as testTime)
		assertEquals("PARSE ERROR", timer.getRunDuration("02:02:02.0", "aj:01:01.0"));
		assertEquals("PARSE ERROR", timer.getRunDuration("02:02:02.0", "35:75:34.0"));
		assertEquals("NULL ERROR", timer.getRunDuration("02:02:02.0", null));
	}
	
	@Test
	public void testGetCurrentChronoTime() {
		//test by delaying time
		timer.synchronizedChronoStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,9,999999999));
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println(timer.getCurrentChronoTime());
		assertEquals("00:00:1", timer.getCurrentChronoTime().substring(0, 7));
		
		//test set and get time
		cTEH.timeEvent("time", new String[] {"01:01:01.1"}, "01:01:01.1");
		assertEquals("01:01:01.1", cTEH.getTimer().getCurrentChronoTime());
	}
}
