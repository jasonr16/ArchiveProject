package chronoTimerMain.simulator;

import java.util.ArrayList;

import chronoTimerMain.software.Timer;

public class ChronoHardwareHandler {
	
	private class SingleEvent {
		private String timeStamp;
		private String command;
		private String[] args;
		public SingleEvent(String timeStamp, String command, String[] args) {
			this.setTimeStamp(timeStamp);
			this.setCommand(command);
			this.setArgs(args);
		}
		public String getTimeStamp() {
			return timeStamp;
		}
		public void setTimeStamp(String timeStamp) {
			this.timeStamp = timeStamp;
		}
		public String getCommand() {
			return command;
		}
		public void setCommand(String command) {
			this.command = command;
		}
		public String[] getArgs() {
			return args;
		}
		public void setArgs(String[] args) {
			this.args = args;
		}
	}
	
	private Timer time = new Timer();
	private ArrayList<? extends Sensor> sensors;
	private ArrayList<SingleEvent> eventLog = new ArrayList<SingleEvent>();
	
	public void inputFromSimulator(String command, String[] args) {
		eventLog.add(new SingleEvent(time.getCurrentChronoTime(), command, args));
	}
	
	public void power(boolean s){};
	public void exit(){};
	public void reset() {};
	public void toggle(int channel){};
	public void conn(Sensor type, int channel){};
	public void disc(int channel){};
}
