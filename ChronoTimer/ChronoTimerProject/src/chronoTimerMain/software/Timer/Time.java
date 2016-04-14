package chronoTimerMain.software.Timer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.StringTokenizer;

import chronoTimerMain.software.eventHandler.commands.EventCommand;
/**
 * sets the chrono system time as different from the system's time
 * @param hh:mm:ss.n
 */
public class Time implements EventCommand {
	private Timer timer;
	private String timestamp;
	public Time(Timer timer, String timestamp) {
		this.timer = timer;
		this.timestamp = timestamp;
	}
	@Override
	public void execute(String[] args) {
		System.out.println(timestamp + " Setting chrono time as " + args[0]);
		if(args[0].length() != 10) {
			System.out.println("Error. Time not in format hh:mm:ss.n");
		}
		else {
			
			StringTokenizer st = new StringTokenizer(args[0], ":.");
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
				return;
			}
			if(h > 23 || h < 0) {
				System.out.println("Error. Hour value not a valid number");
				return;
			}
			else if(m > 59 || m < 0 || s > 59 || s < 0 ||n > 59 || n < 0) {
				System.out.println("Error. Time value(s) not a valid number");
				return;
			}
			timer.synchronizedSystemStartTime = LocalDateTime.now();//synchronize start time betweeen system and chrono
			timer.synchronizedChronoStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(h,m,s,n*100000000));
		}
	}
}
