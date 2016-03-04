package chronoTimerMain.software;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class Timer {
	// TODO: what if time is set again? Wouldn't the system start time also have to reset 
	// so the correct durations are calculated?
	LocalDateTime setStartChronoTime;
	LocalDateTime systemStartTime; 
	public Timer() {
		setStartChronoTime = LocalDateTime.now();
		systemStartTime = LocalDateTime.now();
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
			StringTokenizer st = new StringTokenizer(hhmmssn, ":.");
			try {
			setStartChronoTime = setStartChronoTime.withHour(Integer.parseInt(st.nextToken()));
			setStartChronoTime = setStartChronoTime.withMinute(Integer.parseInt(st.nextToken()));
			setStartChronoTime = setStartChronoTime.withSecond(Integer.parseInt(st.nextToken()));
			setStartChronoTime = setStartChronoTime.withNano(Integer.parseInt(st.nextToken())*100000000);
			} catch (NumberFormatException e) {
				System.out.println("Error. Time format not hh:mm:ss.n");
			}
		}
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
		//subtract start times from finish times
		return String.format("%02d:%02d:%02d.%01d", 
				(Integer.parseInt(stFinish.nextToken())-(Integer.parseInt(stStart.nextToken()))),
				(Integer.parseInt(stFinish.nextToken())-(Integer.parseInt(stStart.nextToken()))),
				(Integer.parseInt(stFinish.nextToken())-(Integer.parseInt(stStart.nextToken()))),
				(Integer.parseInt(stFinish.nextToken())-(Integer.parseInt(stStart.nextToken()))));
	}
	
	/**
	 * Gets the current time based on the set system time of ChronoTimer. A timestamp for system commands.
	 * If no Time has been set, it uses system time.
	 * @return current ChronoTime String in hh:mm:ss.ns format.
	 */
	public String getCurrentChronoTime() {
			Duration d = Duration.between(systemStartTime, LocalDateTime.now());
			return timeToString (d.addTo(setStartChronoTime));
			
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
			setStartChronoTime = setStartChronoTime.withHour(12);
			setStartChronoTime = setStartChronoTime.withMinute(11);
			setStartChronoTime = setStartChronoTime.withSecond(10);
			setStartChronoTime = setStartChronoTime.withNano(900000000);
				assertEquals("12:11:10.9", timeToString(setStartChronoTime));
			}
		@Test
		public void testTimeSet() {
			time("02:04:45.5");
			assertEquals("02:04:45.5", timeToString(setStartChronoTime));
		}
		
		@Test 
		public void testGetChronoTime() {			
			setStartChronoTime = setStartChronoTime.withHour(0);
			setStartChronoTime = setStartChronoTime.withMinute(0);
			setStartChronoTime = setStartChronoTime.withSecond(9);
			setStartChronoTime = setStartChronoTime.withNano(0);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
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
