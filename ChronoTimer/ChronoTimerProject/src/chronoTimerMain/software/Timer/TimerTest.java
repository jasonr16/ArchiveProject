package chronoTimerMain.software.Timer;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class TimerTest {
	Timer timer;
	public TimerTest() {
		timer = new Timer();
	}
	
	@Before
	public void initialize() {
		timer = new Timer();
	}
	@Test
	public void testCorrectStringFormat() {
		timer.synchronizedChronoStartTime = timer.synchronizedChronoStartTime.withHour(12);
		timer.synchronizedChronoStartTime = timer.synchronizedChronoStartTime.withMinute(11);
		timer.synchronizedChronoStartTime = timer.synchronizedChronoStartTime.withSecond(10);
		timer.synchronizedChronoStartTime = timer.synchronizedChronoStartTime.withNano(900000000);
		assertEquals("12:11:10.9", timer.timeToString(timer.synchronizedChronoStartTime));
	}
	@Test
	public void testTimeSet() {
		timer.time("02:04:45.5");
	assertEquals("02:04:45.5", timer.timeToString(timer.synchronizedChronoStartTime));
	}

	@Test 
	public void testGetChronoTime() {	
		//test for delayed time
		timer.synchronizedChronoStartTime = timer.synchronizedChronoStartTime.withHour(0);
		timer.synchronizedChronoStartTime = timer.synchronizedChronoStartTime.withMinute(0);
		timer.synchronizedChronoStartTime = timer.synchronizedChronoStartTime.withSecond(9);
		timer.synchronizedChronoStartTime = timer.synchronizedChronoStartTime.withNano(0);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println(timer.getCurrentChronoTime());
		assertEquals("00:00:1", timer.getCurrentChronoTime().substring(0, 7));
		
	}
	@Test
	public void testGetRunDuration() {
		assertEquals("01:01:01.0", timer.getRunDuration("02:02:02.2", "03:03:03.2"));
		//test zero nanoseconds
		assertEquals("01:01:01.0", timer.getRunDuration("02:02:02.0", "03:03:03.0"));
	}
	@Test
	public void test0Time() {
		assertEquals(0, timer.synchronizedChronoStartTime.withNano(0).withSecond(0).compareTo(
				timer.synchronizedSystemStartTime.withNano(0).withSecond(0)));
	}
	@Test
	public void testInvalidTimeFormat() {
		LocalDateTime saveSTime = timer.synchronizedSystemStartTime;
		LocalDateTime saveCTime = timer.synchronizedChronoStartTime;
		
		timer.time("0000000000");
		assertEquals(0, saveSTime.withNano(0).withSecond(0).compareTo(timer.synchronizedSystemStartTime.withNano(0).withSecond(0)));
		assertEquals(0, saveCTime.withNano(0).withSecond(0).compareTo(timer.synchronizedSystemStartTime.withNano(0).withSecond(0)));
	}
	@Test
	public void TestNotNumberValue() {
		LocalDateTime saveSTime = timer.synchronizedSystemStartTime;
		LocalDateTime saveCTime = timer.synchronizedChronoStartTime;
		
		timer.time("c0:0f:0w.x");
		assertEquals(0, saveSTime.withNano(0).withSecond(0).compareTo(timer.synchronizedSystemStartTime.withNano(0).withSecond(0)));
		assertEquals(0, saveCTime.withNano(0).withSecond(0).compareTo(timer.synchronizedSystemStartTime.withNano(0).withSecond(0)));
	}
}
