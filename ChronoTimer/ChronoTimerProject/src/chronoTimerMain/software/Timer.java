/**
 * Time is meant to be encapsulated to handle all time manipulations for system times, racer times, etc. 
 * It provides strings to other classes for racer startTimes, finishTimes, and total time duration. 
 * 
 */
package chronoTimerMain.software;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import org.junit.Test;

public class Timer {
	public static final int NOT_YET_TIMED = -100000;
	private class RacerTime {
		private ChronoTimeFormatWrapper startTime;
		private ChronoTimeFormatWrapper finishTime;
		private RacerTime() {
			startTime.setHour(NOT_YET_TIMED);
			startTime.setMinute(NOT_YET_TIMED);
			startTime.setSecond(NOT_YET_TIMED);
			startTime.setNano(NOT_YET_TIMED);
			
			finishTime.setHour(NOT_YET_TIMED);
			finishTime.setMinute(NOT_YET_TIMED);
			finishTime.setSecond(NOT_YET_TIMED);
			finishTime.setNano(NOT_YET_TIMED);
		}
	}
	/**
	 * systemTimer may be changed to an offset value that modifies the system clock to print out the correct time.
	 */
	private ChronoTimeFormatWrapper adjustedTime;
	private ChronoTimeFormatWrapper setTime;
	private ChronoTimeFormatWrapper currentTime;
	private ChronoTimeFormatWrapper offsetTimeValues;

	private LinkedHashMap<Integer, RacerTime> racerTimes;
	
	public Timer() {
		adjustedTime = new ChronoTimeFormatWrapper();
		setTime = new ChronoTimeFormatWrapper();
		currentTime = new ChronoTimeFormatWrapper();
		offsetTimeValues = new ChronoTimeFormatWrapper();
		
		LocalDateTime currentSystemTime = LocalDateTime.now();
		
		setTime.setHour(currentSystemTime.getHour());
		setTime.setMinute(currentSystemTime.getMinute());
		setTime.setSecond(currentSystemTime.getSecond());
		setTime.setNano(currentSystemTime.getNano());
	}
	
	/**
	 * sets the system time
	 * @param hhmmssss
	 */
	public void time(String hhmmssss) {
		StringTokenizer st = new StringTokenizer(hhmmssss, ":.");
		setTime.setHour(Integer.parseInt(st.nextToken()));
		setTime.setMinute(Integer.parseInt(st.nextToken()));
		setTime.setSecond(Integer.parseInt(st.nextToken()));
		setTime.setNano(Integer.parseInt(st.nextToken()));
		
		setCurrentTimes();
		
		offsetTimeValues.setHour(currentTime.getHour()-setTime.getHour());
		offsetTimeValues.setMinute(currentTime.getMinute()-setTime.getMinute());
		offsetTimeValues.setSecond(currentTime.getSecond()-setTime.getSecond());
		offsetTimeValues.setNano(currentTime.getNano()-setTime.getNano());

	}
	
	private void setCurrentTimes() {
		LocalDateTime currentSystemTime = LocalDateTime.now();
		
		currentTime.setHour(currentSystemTime.getHour());
		currentTime.setMinute(currentSystemTime.getMinute());
		currentTime.setSecond(currentSystemTime.getSecond());
		currentTime.setNano(currentSystemTime.getNano());
	}
	/**
	 * Assigns a ChronoTime start time to a racer.
	 * @param racerNum
	 */
	public void setStartTimeForRacer(int racerNumber) {
		setCurrentTimes();
		findCurrentTimeRelativeToSetTimes();
		RacerTime rT = new RacerTime();
		rT.startTime.setHour(adjustedTime.getHour());
		rT.startTime.setMinute(adjustedTime.getMinute());
		rT.startTime.setSecond(adjustedTime.getSecond());
		rT.startTime.setNano(adjustedTime.getNano());
		
		racerTimes.put(racerNumber, rT);
	}
	/**
	 * Assigns a ChronoTime finish time to a racer.
	 * @param racerNum
	 */
	public void setFinishTimeForRacer(int racerNumber) {
		setCurrentTimes();
		findCurrentTimeRelativeToSetTimes();
		RacerTime rT = new RacerTime();
		rT.finishTime.setHour(adjustedTime.getHour());
		rT.finishTime.setMinute(adjustedTime.getMinute());
		rT.finishTime.setSecond(adjustedTime.getSecond());
		rT.finishTime.setNano(adjustedTime.getNano());
		
		racerTimes.put(racerNumber, rT);
	}
	/**
	 * This method gets the race duration of a racer.
	 * @param racerNum
	 * @return a Chrono Time String of a Racer in format hh:mm:ss.ns. 
	 * If the racer has finished, returns the elapsed time. 
	 * If the racer is still racing, returns elapsed time.
	 */
	public String getDurationAsString(int racerNum) {
		setCurrentTimes();
		findCurrentTimeRelativeToSetTimes();
		
		RacerTime rT = racerTimes.get(racerNum);
		
		ChronoTimeFormatWrapper durationTime = new ChronoTimeFormatWrapper();
		
		if(rT.finishTime.getHour() != NOT_YET_TIMED) {
			durationTime.setHour(rT.finishTime.getHour()-rT.startTime.getHour());
			durationTime.setMinute(rT.finishTime.getMinute()-rT.startTime.getMinute());
			durationTime.setSecond(rT.finishTime.getSecond()-rT.startTime.getSecond());
			durationTime.setNano(rT.finishTime.getNano()-rT.startTime.getNano());
		}
		else {
			durationTime.setHour(adjustedTime.getHour()-rT.startTime.getHour());
			durationTime.setHour(adjustedTime.getMinute()-rT.startTime.getMinute());
			durationTime.setHour(adjustedTime.getSecond()-rT.startTime.getSecond());
			durationTime.setHour(adjustedTime.getNano()-rT.startTime.getNano());
		}
		
		return timeToString(durationTime);
	}
	/**
	 * Gets the current time based on the set system time of ChronoTimer. A timestamp for system commands.
	 * If no Time has been set, it uses system time.
	 * @return current ChronoTime String in hh:mm:ss.ns format.
	 */
	public String getCurrentChronoTime() {
		//current system times - offsets
		setCurrentTimes();
		
		findCurrentTimeRelativeToSetTimes();
		
		return adjustedTime.getHour() + ":" + adjustedTime.getMinute() + ":" +
			adjustedTime.getSecond() + "." + adjustedTime.getNano();
		
	}
	
	private void findCurrentTimeRelativeToSetTimes() {
		int carryout = findAndSetNanoseconds();
		carryout = findAndSetSeconds(carryout);
		carryout = findAndSetMinutes(carryout);
		findAndSetHours(carryout);
		
	}
	
	private void findAndSetHours(int carryout) {
		if(currentTime.getHour()-offsetTimeValues.getHour() < 0) {
			adjustedTime.setHour(currentTime.getHour()-offsetTimeValues.getHour()+24+carryout);
			
		}
		else if(currentTime.getHour()-offsetTimeValues.getHour() >= 24) {
			adjustedTime.setHour(currentTime.getHour()-offsetTimeValues.getHour()-24+carryout);
			
		}
		else
			adjustedTime.setMinute(currentTime.getHour()-offsetTimeValues.getHour()+carryout);
		
		
	}

	private int findAndSetMinutes(int carryout) {
		if(currentTime.getMinute()-offsetTimeValues.getMinute() < 0) {
			adjustedTime.setMinute(currentTime.getMinute()-offsetTimeValues.getMinute()+60+carryout);
			return -1;
		}
		else if(currentTime.getMinute()-offsetTimeValues.getMinute() >= 60) {
			adjustedTime.setMinute(currentTime.getMinute()-offsetTimeValues.getMinute()-60+carryout);
			return 1;
		}
		else
			adjustedTime.setMinute(currentTime.getMinute()-offsetTimeValues.getMinute()+carryout);
		return 0;
	}

	private int findAndSetSeconds(int carryout) {
		if(currentTime.getSecond()-offsetTimeValues.getSecond() < 0) {
			adjustedTime.setSecond(currentTime.getSecond()-offsetTimeValues.getSecond()+60+carryout);
			return -1;
		}
		else if(currentTime.getSecond()-offsetTimeValues.getSecond() >= 60) {
			adjustedTime.setSecond(currentTime.getSecond()-offsetTimeValues.getSecond()-60+carryout);
			return 1;
		}
		else
			adjustedTime.setSecond(currentTime.getSecond()-offsetTimeValues.getSecond()+carryout);
		return 0;
	}

	private int findAndSetNanoseconds() {
		if(currentTime.getNano()-offsetTimeValues.getNano() < 0) {
			adjustedTime.setNano(currentTime.getNano()-offsetTimeValues.getNano()+100);
			return -1;
		}
		else if(currentTime.getNano()-offsetTimeValues.getNano() >= 100) {
			adjustedTime.setNano(currentTime.getNano()-offsetTimeValues.getNano()-100);
			return 1;
		}
		else
			adjustedTime.setNano(currentTime.getNano()-offsetTimeValues.getNano());
		return 0;
	}	
	
	public String timeToString(ChronoTimeFormatWrapper timer) {
		return timer.getHour() + ":" + timer.getMinute() + ":" +
				timer.getSecond() + "." + timer.getNano()/10000000;
	}

	public void storeEvent(String event) {
		//TODO store all output in a file?
	}
		
	
	@Test
		public void testCorrectStringFormat() {
			setCurrentTimes();
			System.out.println(timeToString(currentTime));
		}
	@Test
	public void testTimeSet() {
		time("02:04:45.50");
		System.out.println(timeToString(setTime));
	}
	@Test
	public void testDuration() {
		RacerTime rT = new RacerTime();
		racerTimes.put(1, rT);
		
	}
}
