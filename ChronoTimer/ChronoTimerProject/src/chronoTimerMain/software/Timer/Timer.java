package chronoTimerMain.software.Timer;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class Timer {
	LocalDateTime synchronizedChronoStartTime; //Chrono time
	LocalDateTime synchronizedSystemStartTime; //System time
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
			
			StringTokenizer st = new StringTokenizer(hhmmssn, ":.");
			if(st.countTokens() != 4) {
				return;
			}
			
			int h = 0,m = 0,s = 0,n = 0;
			try {
				 h = Integer.parseInt(st.nextToken());
				 m = Integer.parseInt(st.nextToken());
				 s = Integer.parseInt(st.nextToken());
				 n = Integer.parseInt(st.nextToken());
			} catch (NumberFormatException e) {
				System.out.println("Error. Time format not hh:mm:ss.n");
			}
			if(h > 23 || h < 0) {
				System.out.println("Error. Hour value not a valid number");
				return;
			}
			else if(m > 59 || m < 0 || s > 59 || s < 0 ||n > 59 || n < 0) {
				System.out.println("Error. Time value(s) not a valid number");
				return;
			}
			synchronizedSystemStartTime = LocalDateTime.now();//synchronize start time betweeen system and chrono
			synchronizedChronoStartTime = synchronizedChronoStartTime.withHour(h);
			synchronizedChronoStartTime = synchronizedChronoStartTime.withMinute(m);
			synchronizedChronoStartTime = synchronizedChronoStartTime.withSecond(s);
			synchronizedChronoStartTime = synchronizedChronoStartTime.withNano(n*100000000);
		}
	}
	
	/**
	 * getRunDuration takes two formatted chronoTime strings and returns the difference between them.
	 * @param startTime
	 * @param finishTime
	 * @return the elapsed time formatted hh:mm:ss.ns between a start and finish time.
	 */
	public String getRunDuration(String startTime, String finishTime) {
		String s="";
		try {
			LocalTime startT = LocalTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_TIME);
			LocalTime finishT = LocalTime.parse(finishTime, DateTimeFormatter.ISO_LOCAL_TIME);
			Duration duration = Duration.between(startT, finishT);
			LocalTime elapsedTime = (LocalTime) duration.addTo(LocalTime.parse("00:00:00.0"));
			s = String.format("%02d:%02d:%02d.%01d", elapsedTime.getHour(),elapsedTime.getMinute(),
					elapsedTime.getSecond(), elapsedTime.getNano()/100000000);
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
	 * @return current ChronoTime String in hh:mm:ss.S format.
	 */
	public String getCurrentChronoTime() {
			Duration d = Duration.between(synchronizedSystemStartTime, LocalDateTime.now());
			return timeToString ((LocalDateTime) d.addTo(synchronizedChronoStartTime));
			
	}	
	protected String timeToString(Temporal t) {
		return String.format("%02d:%02d:%02d.%01d", ((LocalDateTime) t).getHour(),((LocalDateTime) t).getMinute(),
				((LocalDateTime) t).getSecond(),((LocalDateTime) t).getNano()/100000000);
	}
	
	//Internal Unit Tests
		
}
