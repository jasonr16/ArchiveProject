package chronoTimerMain.software.eventHandler.commands;

import chronoTimerMain.software.Timer.Timer;

public class TimerEvents implements EventCommand {
	private Timer timer;
	private String cmd;
	private String timestamp;
	public TimerEvents(String cmd, Timer timer, String timestamp) {
		this.cmd = cmd;
		this.timer = timer;
		this.timestamp = timestamp;
	}
	@Override
	public void execute(String[] args) {
		if(cmd.equalsIgnoreCase("time")) {
			System.out.println(timestamp + " Setting chrono time as " + args[0]);
			timer.time(args[0]);
		}
	}

}
