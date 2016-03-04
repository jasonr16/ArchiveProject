package chronoTimerMain.software;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class Timer {
	LocalDateTime synchronizedChronoStartTime;
	LocalDateTime synchronizedSystemStartTime; 
	public Timer() {
		synchronizedChronoStartTime = LocalDateTime.now();
		synchronizedSystemStartTime = LocalDateTime.now();
	}
	
	/**
	 * sets the chrono system time as different from the system's time
	 * @param hh:mm:ss.n
	 */
	public void time(String hhmmssn) {
		if(hhmmssn.length() != 10) {
			System.out.println("Error. Time not in format hh:mm:ss.n");
		}
		else {
			synchronizedSystemStartTime = LocalDateTime.now();//synchronize start time betweeen system and chrono
			StringTokenizer st = new StringTokenizer(hhmmssn, ":.");
			try {
			synchronizedChronoStartTime = synchronizedChronoStartTime.withHour(Integer.parseInt(st.nextToken()));
			synchronizedChronoStartTime = synchronizedChronoStartTime.withMinute(Integer.parseInt(st.nextToken()));
			synchronizedChronoStartTime = synchronizedChronoStartTime.withSecond(Integer.parseInt(st.nextToken()));
			synchronizedChronoStartTime = synchronizedChronoStartTime.withNano(Integer.parseInt(st.nextToken())*100000000);
			} catch (NumberFormatException e) {
				System.out.println("Error. Time format not hh:mm:ss.n");
			}
		}
		System.out.println("Time set");
	}
	
	/**
	 * getRunDuration takes two formatted chronoTime strings and returns the difference between them.
	 * @param startTime
	 * @param finishTime
	 * @return the elapsed time formatted hh:mm:ss.ns between a start and finish time.
	 */
	public String getRunDuration(String startTime, String finishTime) {
		StringTokenizer stStart = new StringTokenizer(startTime, ":.");
		StringTokenizer stFinish = new StringTokenizer(finishTime, ":.");
		String s;
		int hour, minute, second, nano; 
		try {
			//subtract start times from finish times to get duration
			 s = String.format("%02d:%02d:%02d.%01d", 
				hour =	(Integer.parseInt(stFinish.nextToken())-(Integer.parseInt(stStart.nextToken()))));
				minute =	(Integer.parseInt(stFinish.nextToken())-(Integer.parseInt(stStart.nextToken())));
				second =	(Integer.parseInt(stFinish.nextToken())-(Integer.parseInt(stStart.nextToken())));
				nano =	(Integer.parseInt(stFinish.nextToken())-(Integer.parseInt(stStart.nextToken())));
			 
				s = String.format("%02d:%02d:%02d.%01d", hour, minute, second, nano);
		} catch (NoSuchElementException e) {
			System.out.println("Error with time format parsing.");
			return "00:00:00.0";
		} catch (NumberFormatException e) {
			System.out.println("Error. Time number(s) not valid.");
			return "00:00:00.0";
		}
	
		return s;
	}

	/**
	 * Gets the current time based on the set system time of ChronoTimer. A timestamp for system commands.
	 * If no Time has been set, it uses system time.
	 * @return current ChronoTime String in hh:mm:ss.ns format.
	 */
	public String getCurrentChronoTime() {
			Duration d = Duration.between(synchronizedSystemStartTime, LocalDateTime.now());
			return timeToString (d.addTo(synchronizedChronoStartTime));
			
	}	
	private String timeToString(Temporal t) {
		int nano = ((LocalDateTime) t).getNano();
		nano = getMostSignificantDigit(nano);//print only the most significant digit
		return String.format("%02d:%02d:%02d.%01d", 
				((LocalDateTime) t).getHour(),((LocalDateTime) t).getMinute(),
				((LocalDateTime) t).getSecond(),nano);
	}
	private int getMostSignificantDigit(int parseInt) {
		if(parseInt < 10)
			return parseInt;
		else
			return getMostSignificantDigit(parseInt/10);
	}
	//Internal Unit Tests
		@Test
			public void testCorrectStringFormat() {
			synchronizedChronoStartTime = synchronizedChronoStartTime.withHour(12);
			synchronizedChronoStartTime = synchronizedChronoStartTime.withMinute(11);
			synchronizedChronoStartTime = synchronizedChronoStartTime.withSecond(10);
			synchronizedChronoStartTime = synchronizedChronoStartTime.withNano(900000000);
				assertEquals("12:11:10.9", timeToString(synchronizedChronoStartTime));
			}
		@Test
		public void testTimeSet() {
			time("02:04:45.5");
			assertEquals("02:04:45.5", timeToString(synchronizedChronoStartTime));
		}
		
		@Test 
		public void testGetChronoTime() {			
			synchronizedChronoStartTime = synchronizedChronoStartTime.withHour(0);
			synchronizedChronoStartTime = synchronizedChronoStartTime.withMinute(0);
			synchronizedChronoStartTime = synchronizedChronoStartTime.withSecond(9);
			synchronizedChronoStartTime = synchronizedChronoStartTime.withNano(0);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println(getCurrentChronoTime());
			assertEquals("00:00:1", getCurrentChronoTime().substring(0, 7));
			
			
		}
		@Test
		public void testGetRunDuration() {
			assertEquals("01:01:01.1", getRunDuration("02:02:02.2", "03:03:03.3"));
		}
}
