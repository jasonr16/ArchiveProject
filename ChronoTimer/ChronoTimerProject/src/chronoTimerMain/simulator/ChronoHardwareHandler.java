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
	 * @param timestamp 
	 **/
	public void inputFromSimulator(String command, String[] args, String timestamp) {
		if(timestamp == null) {
			eventLog.add(new SingleEvent(time.getCurrentChronoTime(), command, args));
		}
		else
			new SingleEvent(timestamp, command, args);
		command = command.toUpperCase();
		
		switch (command){
		case "POWER":
			System.out.println(timestamp + "Toggling power switch.");
			power();
			break;
		case "ON":
			System.out.println(timestamp + "Powering on.");
			ON();
			break;
		case "OFF":
			System.out.println(timestamp + "Powering off");
			OFF();
			break;
		case "EXIT":
			System.out.println(timestamp + "Exiting Simulator. Have a nice day.");
			exit();
			break;
		}
		if(power){
			//TODO add succeed/fail messages
		switch(command) {
			case "CONN":
				System.out.println(timestamp +"Connecting sensor " + args[0] + " at channel " + args[1]);
				try{
					conn(args[0], Integer.parseInt(args[1]));
				}catch (NumberFormatException e) {
					System.out.println("Error - Could not parse channel number");
				}
				break;
			case "DISC":
				System.out.println(timestamp + "Disconnecting channel " + args[0]);
				try{
					disc(Integer.parseInt(args[0]));
				}catch (NumberFormatException e) {
					System.out.println(timestamp + "Error - Could not parse channel number");
				}
				break;
			case "TOGGLE":
			case "TOG":
				System.out.println(timestamp + "Toggling channel " + args[0]);
				try {
					toggle(Integer.parseInt(args[0]));
				}catch (NumberFormatException e) {
					System.out.println("Error - Could not parse channel number");
				}
				break;
				
			case "RESET":
				System.out.println(timestamp + "Resetting system.");
				reset();
				break;
			default:
				eventHandler.timeEvent(command, args, timestamp);
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
		//initialize stuff
		eventLog = new ArrayList<SingleEvent>();
		eventHandler = new ChronoTimerEventHandler(time);
		this.power = true;
	}
	
	/**
	 * same as ! ON()...
	 */
	public void OFF(){
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
	}
	public void disc(int channel){
		sensors[channel] = null;
	};
}
