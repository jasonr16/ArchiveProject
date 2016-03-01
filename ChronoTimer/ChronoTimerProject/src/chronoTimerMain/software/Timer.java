/**
 * Time is meant to be encapsulated to handle all time manipulations for system times, racer times, etc. 
 * It provides strings to other classes for racer startTimes, finishTimes, and total time duration. 
 * 
 */
package chronoTimerMain.software;

import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedHashMap;

public class Timer {
	private class RacerTime {
			private LocalTime startTime;
			private LocalTime endTime;
	}
	/**
	 * systemTimer may be changed to an offset value that modifies the system clock to print out the correct time.
	 */
	private LocalTime systemTimer;
	private LinkedHashMap<Integer, RacerTime> racerTimes;
	/**
	 * sets the system time
	 * @param hhmmssss
	 */
	public void time(String hhmmssss) {
	}
	public String getDurationAsString(int racerNum) {
		RacerTime rT = racerTimes.get(racerNum);
		Duration d = Duration.between(rT.startTime, rT.endTime);
		//Need to add time Formatting
		
		return d.toString();
	}
	public String getCurrentTime() {
		return null;
		
	}
}
