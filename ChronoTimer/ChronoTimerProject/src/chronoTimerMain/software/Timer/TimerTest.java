package chronoTimerMain.software.Timer;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class TimerTest {
	Timer timer;
	LocalDateTime saveSTime;
	LocalDateTime saveCTime;
	public TimerTest() {
		timer = new Timer();
	}
	
	@Before
	public void initialize() {
		timer = new Timer();
		saveSTime = timer.synchronizedSystemStartTime;
		saveCTime = timer.synchronizedChronoStartTime;
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
		assertEquals(0, timer.synchronizedChronoStartTime.withNano(0).withSecond(0).compareTo(
				timer.synchronizedSystemStartTime.withNano(0).withSecond(0)));
		new Time(timer, "").execute(new String[] {"02:04:45.5"});
		assertEquals("02:04:45.5", timer.timeToString(timer.synchronizedChronoStartTime));
		//test invalid format
		saveSTime = timer.synchronizedSystemStartTime;
		saveCTime = timer.synchronizedChronoStartTime;
		
		new Time(timer, "").execute(new String[] {"0000000000"});
		assertEquals(0, saveSTime.withNano(0).withSecond(0).compareTo(timer.synchronizedSystemStartTime.withNano(0).withSecond(0)));
		assertEquals(0, saveCTime.withNano(0).withSecond(0).compareTo(timer.synchronizedSystemStartTime.withNano(0).withSecond(0)));
		//test input too long
		
		//test non-integer values
		saveSTime = timer.synchronizedSystemStartTime;
		saveCTime = timer.synchronizedChronoStartTime;
		
		new Time(timer, "").execute(new String[] {"c0:0f:0w.x"});
		assertEquals(0, saveSTime.withNano(0).withSecond(0).compareTo(timer.synchronizedSystemStartTime.withNano(0).withSecond(0)));
		assertEquals(0, saveCTime.withNano(0).withSecond(0).compareTo(timer.synchronizedSystemStartTime.withNano(0).withSecond(0)));
		//test integers out of bounds of time: h,m,s,S
		//test system time is updated
		
		
	}
	@Test
	public void testGetRunDuration() {
		//test nonzero values for h, m, s, S
		assertEquals("01:01:01.1", timer.getRunDuration("02:02:02.1", "03:03:03.2"));
		//test zero h, m, s, S
		assertEquals("01:01:01.0", timer.getRunDuration("02:02:02.0", "03:03:03.0"));
		//test for negative durations
		//test for too long durations
		//test for time values that cross over into a new day
		//test invalid first arguments (same as testTime)
		//test invalid second arguments (same as testTime)
		//test invalid both arguments (same as testTime)
	}

	public void testGetCurrentChronoTime() {
		//test set and get time
		//test by delaying time
		timer.synchronizedChronoStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,9,999999999));
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println(timer.getCurrentChronoTime());
		assertEquals("00:00:1", timer.getCurrentChronoTime().substring(0, 7));
	}
}
