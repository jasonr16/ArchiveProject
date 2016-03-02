/**
 * Time is meant to be encapsulated to handle all time manipulations for system times, racer times, etc. 
 * It provides strings to other classes for racer startTimes, finishTimes, and total time duration. 
 * 
 */
package chronoTimerMain.software;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import chronoTimerMain.software.Times.AdjustedTime;
import chronoTimerMain.software.Times.CurrentTime;
import chronoTimerMain.software.Times.CurrentToSetOffsetTime;
import chronoTimerMain.software.Times.SetTime;

public class Timer {
	private class RacerTime {
			private int startHour, startMinute, startSecond, startNano;
			private int endHour, endMinute, endSecond, endNano;
	}
	/**
	 * systemTimer may be changed to an offset value that modifies the system clock to print out the correct time.
	 */
	private AdjustedTime adjustedTime;
	private SetTime setTime;
	private CurrentTime currentTime;
	private CurrentToSetOffsetTime offsetTimeValues;
	
	private LocalDateTime currentSystemTime;

	private LinkedHashMap<Integer, RacerTime> racerTimes;
	public Timer() {
		adjustedTime = new AdjustedTime();
		setTime = new SetTime();
		currentTime = new CurrentTime();
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
		setTime.setNanoseconds(Integer.parseInt(st.nextToken()));
		
		setCurrentTimes();
		
		offsetTimeValues.setHour(currentTime.getHour()-setTime.getHour());
		offsetTimeValues.setMinute(currentTime.getMinute()-setTime.getMinute());
		offsetTimeValues.setSecond(currentTime.getSecond()-setTime.getSecond());
		offsetTimeValues.setNanoseconds(currentTime.getNanoseconds()-setTime.getNanoseconds());

	}
	
	private void setCurrentTimes() {
		currentSystemTime = LocalDateTime.now();
		
		currentTime.setHour(currentSystemTime.getHour());
		currentTime.setMinute(currentSystemTime.getMinute());
		currentTime.setSecond(currentSystemTime.getSecond());
		currentTime.setNanoseconds(currentSystemTime.getNano());
	}
	
	public String getDurationAsString(int racerNum) {
		RacerTime rT = racerTimes.get(racerNum);
		//Need to add time Formatting
		
		return null;
	}
	public String getCurrentTime() {
		//current system times - offsets
		setCurrentTimes();
		
		findCurrentTimeRelativeToSetTimes();
		
		return h + ":" + m + ":" + s + "." + ns;
		
	}
	//h,m,s,ns are used for getCurrentTime() in adjusting to the local time from the system time.
	private int h, m, s, ns;
	
	private void findCurrentTimeRelativeToSetTimes() {
		if(currentHour-offsetHour < 0) 
			h = currentHour-offsetHour+24;
		else if (currentHour-offsetHour > 24)
			h = currentHour-offsetHour-24;
		else
			h = currentHour-offsetHour;
		
		if(currentMinute-offsetMinute < 0) {
			m = currentMinute-offsetMinute+60;
			minusOneHour();
		}
		else if (currentMinute-offsetMinute >= 60) {
			m = currentMinute-offsetMinute-60;
			addOneHour();
		}
		else
			m = currentMinute-offsetMinute;
		
		if(currentSecond-offsetSecond < 0) {
			s = currentMinute-offsetMinute+60;
			minusOneMinute();
		}
		else if(currentSecond-offsetSecond >= 60) {
			s = currentMinute-offsetSecond-60;
			addOneMinute();
		}
		else 
			s = currentMinute-offsetMinute;
		
		if(currentNano-offsetNano < 0) {
			ns = currentNano-offsetNano+100;
			minusOneSecond();
		}
		else if(currentNano-offsetNano >= 100)
			ns = currentNano-offsetNano-100;
			addOneSecond();
	}

	private void addOneSecond() {
		if(s == 59 ) {
			s=0;
			addOneMinute();
		}
		else
			s++;
		
	}

	private void minusOneSecond() {
		if(s == 0) {
			s=59;
			minusOneMinute();
		}
		else s--;
		
	}

	private void minusOneMinute() {
		if(m == 0) {
			m = 59;
			minusOneHour();
		}
		else
			m--;
		
	}

	

	private void addOneMinute() {
		if(m == 59) {
			m=0;
			addOneHour();
		}
		else m++;
		
		
	}
	
	private void minusOneHour() {
		if(h==0)
			h=23;
		else h--;
		
	}

	private void addOneHour() {
		if(h == 23)
			h=0;
		else
			h++;
		
	}

	
	
	public void storeEvent(String event) {
		//TODO store all output in a file?
	}
}
