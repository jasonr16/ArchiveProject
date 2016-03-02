/**
 * Time is meant to be encapsulated to handle all time manipulations for system times, racer times, etc. 
 * It provides strings to other classes for racer startTimes, finishTimes, and total time duration. 
 * 
 */
package chronoTimerMain.software;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class Timer {
	

	private ChronoTimeFormatWrapper adjustedChronoTime;
	private ChronoTimeFormatWrapper initialSetChronoTime;
	private ChronoTimeFormatWrapper currentSystemTime;
	private ChronoTimeFormatWrapper offsetBetweenSystemAndChronoTime;

	private LinkedHashMap<Integer, RacerTime> racerTimes = new LinkedHashMap<Integer, RacerTime>();
	
	public Timer() {
		adjustedChronoTime = new ChronoTimeFormatWrapper();
		initialSetChronoTime = new ChronoTimeFormatWrapper();
		currentSystemTime = new ChronoTimeFormatWrapper();
		offsetBetweenSystemAndChronoTime = new ChronoTimeFormatWrapper();
		
		LocalDateTime currentTime = LocalDateTime.now();
		
		initialSetChronoTime.setHour(currentTime.getHour());
		initialSetChronoTime.setMinute(currentTime.getMinute());
		initialSetChronoTime.setSecond(currentTime.getSecond());
		initialSetChronoTime.setNano(currentTime.getNano());
	}
	
	/**
	 * sets the chrono system time as different from the system's time
	 * @param hhmmssss
	 */
	public void time(String hhmmssss) {
		StringTokenizer st = new StringTokenizer(hhmmssss, ":.");
		initialSetChronoTime.setHour(Integer.parseInt(st.nextToken()));
		initialSetChronoTime.setMinute(Integer.parseInt(st.nextToken()));
		initialSetChronoTime.setSecond(Integer.parseInt(st.nextToken()));
		initialSetChronoTime.setNano(Integer.parseInt(st.nextToken()));
		
		setcurrentSysTimes();
		//find and set the offset time between the chrono time and system time.
		offsetBetweenSystemAndChronoTime.setHour(currentSystemTime.getHour()-initialSetChronoTime.getHour());
		offsetBetweenSystemAndChronoTime.setMinute(currentSystemTime.getMinute()-initialSetChronoTime.getMinute());
		offsetBetweenSystemAndChronoTime.setSecond(currentSystemTime.getSecond()-initialSetChronoTime.getSecond());
		offsetBetweenSystemAndChronoTime.setNano(currentSystemTime.getNano()-initialSetChronoTime.getNano());

	}
	
	/**
	 * getRunDuration takes two formatted chronoTime strings and returns the difference between them.
	 * @param startTime
	 * @param finishTime
	 * @return the elapsed time formatted hh:mm:ss.ns between a start and finish time.
	 */
	public String getRunDuration(String startTime, String finishTime) {
		StringTokenizer st = new StringTokenizer(startTime, ":.");
		int startTimeHour = Integer.parseInt(st.nextToken());
		int startTimeMinute = Integer.parseInt(st.nextToken());
		int startTimeSecond = Integer.parseInt(st.nextToken());
		int startTimeNano = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(finishTime, ":.");
		int finishTimeHour = Integer.parseInt(st.nextToken());
		int finishTimeMinute = Integer.parseInt(st.nextToken());
		int finishTimeSecond = Integer.parseInt(st.nextToken());
		int finishTimeNano = Integer.parseInt(st.nextToken());
		
		return (finishTimeHour-startTimeHour) + ":" + (finishTimeMinute-startTimeMinute) + ":" +
		(finishTimeSecond-startTimeSecond) + "." + (finishTimeNano-startTimeNano);
		
	}
	
	/**
	 * Gets the current time based on the set system time of ChronoTimer. A timestamp for system commands.
	 * If no Time has been set, it uses system time.
	 * @return current ChronoTime String in hh:mm:ss.ns format.
	 */
	public String getCurrentChronoTime() {
		setcurrentSysTimes();
		
		findcurrentSysTimeRelativeToSetTimes();
		
		return timeToString(adjustedChronoTime);
		
	}
	
	public void storeEvent(String event) {
		//TODO store all output in a file?
	}
	

	private void setcurrentSysTimes() {
		LocalDateTime currentTime = LocalDateTime.now();
		
		currentSystemTime.setHour(currentTime.getHour());
		currentSystemTime.setMinute(currentTime.getMinute());
		currentSystemTime.setSecond(currentTime.getSecond());
		currentSystemTime.setNano(currentTime.getNano());
	}
	
	
	private String timeToString(ChronoTimeFormatWrapper timer) {
		return timer.getHour() + ":" + timer.getMinute() + ":" +
				timer.getSecond() + "." + timer.getNano()/10000000;
	}

	/**
	 * sets the adjustedChronoTime based on system time and the chrono set time
	 */
	private void findcurrentSysTimeRelativeToSetTimes() {
		int carryout = findAndSetNanoseconds();
		carryout = findAndSetSeconds(carryout);
		carryout = findAndSetMinutes(carryout);
		findAndSetHours(carryout);
		
	}
	
	private void findAndSetHours(int carryout) {//need to account for round over
		if(currentSystemTime.getHour()-offsetBetweenSystemAndChronoTime.getHour() < 0) {
			adjustedChronoTime.setHour(currentSystemTime.getHour()-offsetBetweenSystemAndChronoTime.getHour()+24+carryout);
			
		}
		else if(currentSystemTime.getHour()-offsetBetweenSystemAndChronoTime.getHour() >= 24) {
			adjustedChronoTime.setHour(currentSystemTime.getHour()-offsetBetweenSystemAndChronoTime.getHour()-24+carryout);
			
		}
		else
			adjustedChronoTime.setHour(currentSystemTime.getHour()-offsetBetweenSystemAndChronoTime.getHour()+carryout);
		
		
	}

	private int findAndSetMinutes(int carryout) {//need to subtract or add one hour if round over occurs
		if(currentSystemTime.getMinute()-offsetBetweenSystemAndChronoTime.getMinute() < 0) {
			adjustedChronoTime.setMinute(currentSystemTime.getMinute()-offsetBetweenSystemAndChronoTime.getMinute()+60+carryout);
			return -1;
		}
		else if(currentSystemTime.getMinute()-offsetBetweenSystemAndChronoTime.getMinute() >= 60) {
			adjustedChronoTime.setMinute(currentSystemTime.getMinute()-offsetBetweenSystemAndChronoTime.getMinute()-60+carryout);
			return 1;
		}
		else
			adjustedChronoTime.setMinute(currentSystemTime.getMinute()-offsetBetweenSystemAndChronoTime.getMinute()+carryout);
		return 0;
	}

	private int findAndSetSeconds(int carryout) {//need to subtract or add one minute if round over occurs
		if(currentSystemTime.getSecond()-offsetBetweenSystemAndChronoTime.getSecond() < 0) {
			adjustedChronoTime.setSecond(currentSystemTime.getSecond()-offsetBetweenSystemAndChronoTime.getSecond()+60+carryout);
			return -1;
		}
		else if(currentSystemTime.getSecond()-offsetBetweenSystemAndChronoTime.getSecond() >= 60) {
			adjustedChronoTime.setSecond(currentSystemTime.getSecond()-offsetBetweenSystemAndChronoTime.getSecond()-60+carryout);
			return 1;
		}
		else
			adjustedChronoTime.setSecond(currentSystemTime.getSecond()-offsetBetweenSystemAndChronoTime.getSecond()+carryout);
		return 0;
	}

	private int findAndSetNanoseconds() {//need to subtract or add one second if round over occurs
		if(currentSystemTime.getNano()-offsetBetweenSystemAndChronoTime.getNano() < 0) {
			adjustedChronoTime.setNano(currentSystemTime.getNano()-offsetBetweenSystemAndChronoTime.getNano()+100);
			return -1;
		}
		else if(currentSystemTime.getNano()-offsetBetweenSystemAndChronoTime.getNano() >= 100) {
			adjustedChronoTime.setNano(currentSystemTime.getNano()-offsetBetweenSystemAndChronoTime.getNano()-100);
			return 1;
		}
		else
			adjustedChronoTime.setNano(currentSystemTime.getNano()-offsetBetweenSystemAndChronoTime.getNano());
		return 0;
	}	
	
	
		
	
	@Test
		public void testCorrectStringFormat() {
			setcurrentSysTimes();
			System.out.println(timeToString(currentSystemTime) + " - correctFormat");
		}
	@Test
	public void testTimeSet() {
		time("02:04:45.50");
		assertEquals("02:04:45.50", timeToString(initialSetChronoTime));
	}
	
	@Test 
	public void testGetChronoTime() {
		
		initialSetChronoTime.setHour(0);
		initialSetChronoTime.setMinute(0);
		initialSetChronoTime.setSecond(0);
		initialSetChronoTime.setNano(0);
		
		setcurrentSysTimes();
		
		offsetBetweenSystemAndChronoTime.setHour(currentSystemTime.getHour()-initialSetChronoTime.getHour());
		offsetBetweenSystemAndChronoTime.setMinute(currentSystemTime.getMinute()-initialSetChronoTime.getMinute());
		offsetBetweenSystemAndChronoTime.setSecond(currentSystemTime.getSecond()-initialSetChronoTime.getSecond());
		offsetBetweenSystemAndChronoTime.setNano(currentSystemTime.getNano()-initialSetChronoTime.getNano());
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(getCurrentChronoTime());
		
	}
	@Test
	public void testGetRunDuration() {
		assertEquals("1:1:1.1", getRunDuration("2:2:2.2", "3:3:3.3"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//To be removed...
	public static final int NOT_YET_TIMED = -100000;
	private class RacerTime {
		private ChronoTimeFormatWrapper startTime = new ChronoTimeFormatWrapper();
		private ChronoTimeFormatWrapper finishTime = new ChronoTimeFormatWrapper();
		RacerTime() {
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
	public void removeRacer(int racerNum) {
		racerTimes.remove(racerNum);
	}
	/**
	 * This method gets the race duration of a racer.
	 * @param racerNum
	 * @return a Chrono Time String of a Racer in format hh:mm:ss.ns. 
	 * If the racer has finished, returns the elapsed time. 
	 * If the racer is still racing, returns elapsed time.
	 */
	public String getDurationAsString(int racerNum) {
		setcurrentSysTimes();
		findcurrentSysTimeRelativeToSetTimes();
		
		RacerTime rT = racerTimes.get(racerNum);
		if(rT == null) {
			return "Error. Racer doesn't exist in racerList";
		}
		
		ChronoTimeFormatWrapper durationTime = new ChronoTimeFormatWrapper();
		
		if(rT.finishTime.getHour() != NOT_YET_TIMED) {//racer has finished
			durationTime.setHour(rT.finishTime.getHour()-rT.startTime.getHour());
			durationTime.setMinute(rT.finishTime.getMinute()-rT.startTime.getMinute());
			durationTime.setSecond(rT.finishTime.getSecond()-rT.startTime.getSecond());
			durationTime.setNano(rT.finishTime.getNano()-rT.startTime.getNano());
		}
		else {//racer still racing
			durationTime.setHour(adjustedChronoTime.getHour()-rT.startTime.getHour());
			durationTime.setHour(adjustedChronoTime.getMinute()-rT.startTime.getMinute());
			durationTime.setHour(adjustedChronoTime.getSecond()-rT.startTime.getSecond());
			durationTime.setHour(adjustedChronoTime.getNano()-rT.startTime.getNano());
		}
		if (durationTime.getHour() > 24) {
			return "Error retrieving Racer " + racerNum + " duration. Time fields may not have been set.";
		}
		return timeToString(durationTime);
	}
	/**
	 * Assigns a ChronoTime start time to a racer. Tied to RacerList class in Timer.
	 * @param racerNum
	 */
	public void setStartTimeForRacer(int racerNumber) {
		setcurrentSysTimes();
		findcurrentSysTimeRelativeToSetTimes();
		RacerTime rT = racerTimes.get(racerNumber);
		rT.startTime = new ChronoTimeFormatWrapper();
		rT.startTime.setHour(adjustedChronoTime.getHour());
		rT.startTime.setMinute(adjustedChronoTime.getMinute());
		rT.startTime.setSecond(adjustedChronoTime.getSecond());
		rT.startTime.setNano(adjustedChronoTime.getNano());
		
		racerTimes.put(racerNumber, rT);
	}
	/**
	 * Assigns a ChronoTime finish time to a racer. Tied to RacerList class in Timer
	 * @param racerNum
	 */
	public void setFinishTimeForRacer(int racerNumber) {
		setcurrentSysTimes();
		findcurrentSysTimeRelativeToSetTimes();
		RacerTime rT = racerTimes.get(racerNumber);
		rT.finishTime = new ChronoTimeFormatWrapper();
		rT.finishTime.setHour(adjustedChronoTime.getHour());
		rT.finishTime.setMinute(adjustedChronoTime.getMinute());
		rT.finishTime.setSecond(adjustedChronoTime.getSecond());
		rT.finishTime.setNano(adjustedChronoTime.getNano());
		
		racerTimes.put(racerNumber, rT);
	}
	
	@Test
	public void testDuration() {
		RacerTime rT = new RacerTime();
		racerTimes.put(1, rT);
		setStartTimeForRacer(1);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setFinishTimeForRacer(1);
	
		assertEquals("0:0:1.0", getDurationAsString(1));
		
	}
}
