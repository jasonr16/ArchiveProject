package chronoTimerMain.software.Timer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Timer {
	LocalDateTime synchronizedChronoStartTime; //Chrono time
	LocalDateTime synchronizedSystemStartTime; //System time
	
	public Timer() {
		synchronizedChronoStartTime = LocalDateTime.now();
		synchronizedSystemStartTime = LocalDateTime.now();
	}
	
	/**
	 * getRunDuration takes two formatted chronoTime strings and returns the difference between them.
	 * @param startTime
	 * @param finishTime
	 * @return the elapsed time formatted hh:mm:ss.ns between a start and finish time.
	 */
	public String getRunDuration(String startTime, String finishTime) {
		String s="";
		LocalTime elapsedTime;
		try {
			LocalTime startT = LocalTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_TIME);
			LocalTime finishT = LocalTime.parse(finishTime, DateTimeFormatter.ISO_LOCAL_TIME);
			Duration duration = Duration.between(startT, finishT);
			elapsedTime = (LocalTime) duration.addTo(LocalTime.parse("00:00:00.0"));
			s = String.format("%02d:%02d:%02d.%01d", elapsedTime.getHour(),elapsedTime.getMinute(),
					elapsedTime.getSecond(), elapsedTime.getNano()/100000000);
		} catch (NullPointerException e) {
			System.out.println("null time value entered.");
			return "NULL ERROR";
		} catch (NoSuchElementException e) {
			System.out.println("Error with time format parsing.");
			return "PARSE ERROR";
		} catch (NumberFormatException e) {
			System.out.println("Error. Time number(s) not valid.");
			return "FORMAT ERROR";
		} catch (DateTimeParseException e) {
			System.out.println("Error with time format parsing.");
			return "PARSE ERROR";
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
	/**
	 * Helper method for getCurrentChronoTime()
	 * @param t
	 * @return
	 */
	protected String timeToString(LocalDateTime t) {
		return String.format("%02d:%02d:%02d.%01d", ((LocalDateTime) t).getHour(),((LocalDateTime) t).getMinute(),
				((LocalDateTime) t).getSecond(),((LocalDateTime) t).getNano()/100000000);
	}
}
