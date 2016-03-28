package chronoTimerMain.simulator;

import java.util.ArrayList;

import chronoTimerMain.simulator.sensor.SensorElectricEye;
import chronoTimerMain.simulator.sensor.SensorGate;
import chronoTimerMain.simulator.sensor.SensorPad;
import chronoTimerMain.software.ChronoTimerEventHandler;
import chronoTimerMain.software.Timer;
/**
 * ChronoHardwareHandler is an adapter between the simulator and the rest of chronotimer.
 * This class implements all hardware-related commands that represent physical processes, such as turning the power on.
 * All non-hardware commands are sent to ChronoTimerEventHandler to be parsed and implemented there.
 * @author Jason
 *
 */
public class ChronoHardwareHandler {
	
	/**
	 * SingleEvent class is used to store all events entered into the system.
	 * It will eventually be implemented to export to XML.
	 * @author Jason
	 *
	 */
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
			new SingleEvent(timestamp, command, args);//save the input to eventlog
		command = command.toUpperCase();
		
		switch (command){
		case "POWER":
			System.out.println(timestamp + " Toggling power switch.");
			power();
			break;
		case "ON":
			System.out.println(timestamp + " Powering on.");
			ON();
			break;
		case "OFF":
			System.out.println(timestamp + " Powering off");
			OFF();
			break;
		case "EXIT":
			System.out.println(timestamp + " Exiting Simulator. Have a nice day.");
			exit();
			break;
		}
		if(power){
			//TODO add succeed/fail messages
			//TODO move CONN out of power method
		switch(command) {
			case "CONN":
				System.out.println(timestamp +" Connecting sensor " + args[0] + " at channel " + args[1]);
				try{
					conn(args[0], Integer.parseInt(args[1]));
				}catch (NumberFormatException e) {
					System.out.println("Error - Could not parse channel number");
				}
				break;
			case "DISC":
				System.out.println(timestamp + " Disconnecting channel " + args[0]);
				try{
					disc(Integer.parseInt(args[0]));
				}catch (NumberFormatException e) {
					System.out.println(timestamp + " Error - Could not parse channel number");
				}
				break;
			case "TOGGLE":
			case "TOG":
				System.out.println(timestamp + " Toggling channel " + args[0]);
				try {
					toggle(Integer.parseInt(args[0]));
				}catch (NumberFormatException e) {
					System.out.println("Error - Could not parse channel number");
				}
				break;
				
			case "RESET":
				System.out.println(timestamp + " Resetting system.");
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
	 * turns on power
	 */
	public void ON(){
		//initialize stuff
		eventLog = new ArrayList<SingleEvent>();
		eventHandler = new ChronoTimerEventHandler(time);
		this.power = true;
	}
	
	/**
	 * turns off power
	 */
	public void OFF(){
		this.power = false;

	}
	/**
	 * exits the program.
	 */
	public void exit(){
		OFF();
		System.exit(0);
	}
	/**
	 * resets chronotimer to initial states.
	 */
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
	/**
	 * adds a sensor to specified channel
	 * @param type the type of sensor
	 * @param channel the channel associated with the sensor, 1-12
	 * @return int of channel added to or -10 (outside range) on failure
	 */
	public int conn(String type, int channel){
		type = type.toUpperCase();
		switch(type) {
			case "EYE":
				sensors[channel] = new SensorElectricEye();
				return channel;
				//break;
			case "PAD":
				sensors[channel] = new SensorPad();
				return channel;
				//break;
			case "GATE":
				sensors[channel] = new SensorGate();
				return channel;
				//break;
			default: 
				System.out.println("Error. Invalid sensor type.");
				return -10;
		}
	}
	/**
	 * removes a sensor from channel
	 * @param channel the channel number to disconnect the sensor
	 */
	public int disc(int channel){
		sensors[channel] = null;
		return channel;
	};
}