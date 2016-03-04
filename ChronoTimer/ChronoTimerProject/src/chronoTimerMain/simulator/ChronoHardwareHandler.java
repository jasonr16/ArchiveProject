package chronoTimerMain.simulator;

import java.util.ArrayList;

import chronoTimerMain.software.ChronoTimerEventHandler;
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
	private boolean power = false;
	private ChronoTimerEventHandler eventHandler = new ChronoTimerEventHandler(time);
	/**
	 * 
	 * Interaction between simulator and rest of ChronoTimer
	 * @param command
	 **/
	public void inputFromSimulator(String command, String[] args) {
		eventLog.add(new SingleEvent(time.getCurrentChronoTime(), command, args));
		if(!power){
			return;
		}
		switch (command){
			default:
				eventHandler.timeEvent(command, args);
		}
	}
	
	/**
	 * Toggles the interaction between Hardware and Software
	 * "Its over 9,000!"
	 * @return boolean representing the state of the machine
	 */
	public boolean power(){
		//do the opposite of power state
		if(this.power)
			this.OFF();
		else this.ON();
		//return power...
		return this.power;
	}
	
	/**
	 * does exactly what you think it will :P
	 */
	public void ON(){
		if(!this.power)
			System.out.println("Powered up and ready to go!");
		this.power = true;
	}
	
	/**
	 * same as ! ON()...
	 */
	public void OFF(){
		if(this.power)
			System.out.println("Goodbye");
		this.power = false;
	}
	
	public void exit(){
		OFF();
	}
	public void reset() {
		power();
		power();
	};
	public void toggle(int channel){};
	public void conn(Sensor type, int channel){};
	public void disc(int channel){};
}
