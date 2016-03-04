package chronoTimerMain.simulator;

import java.util.ArrayList;

import chronoTimerMain.simulator.sensor.SensorElectricEye;
import chronoTimerMain.simulator.sensor.SensorGate;
import chronoTimerMain.simulator.sensor.SensorPad;
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
	private Sensor[] sensors = new Sensor[13];//no sensor stored in index 0. 12 max
	private boolean[] isEnabledSensor = new boolean[13];
	private ArrayList<SingleEvent> eventLog = new ArrayList<SingleEvent>();
	private boolean power = false;
	private ChronoTimerEventHandler eventHandler;
	
	/**
	 * Interaction between simulator and rest of ChronoTimer
	 * @param command
	 **/
	public void inputFromSimulator(String command, String[] args) {
		eventLog.add(new SingleEvent(time.getCurrentChronoTime(), command, args));
		command = command.toUpperCase();
		
		switch (command){
		case "POWER":
			power();
			break;
		case "ON":
			ON();
			break;
		case "OFF":
			OFF();
			break;
		case "EXIT":
			exit();
			break;
		}
		if(power){
			
		switch(command) {
			case "CONN":
				try{
					conn(args[0], Integer.parseInt(args[1]));
				}catch (NumberFormatException e) {
					System.out.println("Error - Could not parse channel number");
				}
				break;
			case "DISC":
				try{
					disc(Integer.parseInt(args[0]));
				}catch (NumberFormatException e) {
					System.out.println("Error - Could not parse channel number");
				}
				break;
			case "TOGGLE":
			case "TOG":
				try {
					toggle(Integer.parseInt(args[0]));
				}catch (NumberFormatException e) {
					System.out.println("Error - Could not parse channel number");
				}
				break;
				
			case "RESET":
				reset();
				break;
			default:
				eventHandler.timeEvent(command, args);
				ON();
			}
		}
	}
	
	/**
	 * Toggles the interaction between Hardware and Software
	 * 
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
	 * turns on
	 */
	public void ON(){
		if(!this.power)
			System.out.println("Power on.");
		//initialize stuff
		eventLog = new ArrayList<SingleEvent>();
		eventHandler = new ChronoTimerEventHandler(time);
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
		System.exit(0);
	}
	
	public void reset() {
		time = new Timer();
		eventHandler = new ChronoTimerEventHandler(time);
		eventLog = new ArrayList<SingleEvent>();
	
	};
	
	/**
	 * turns channel on and off
	 * @param channel
	 */
	public void toggle(int channel) {
		isEnabledSensor[channel] = !isEnabledSensor[channel];
	};
	public void conn(String type, int channel){
		switch(type) {
			case "EYE":
				sensors[channel] = new SensorElectricEye();
				break;
			case "PAD":
				sensors[channel] = new SensorPad();
				break;
			case "GATE":
				sensors[channel] = new SensorGate();
				break;
			default: 
				System.out.println("Error. Invalid sensor type.");
		}
		sensors[channel] = new SensorElectricEye();
	}
	public void disc(int channel){
		sensors[channel] = null;
	};
}
