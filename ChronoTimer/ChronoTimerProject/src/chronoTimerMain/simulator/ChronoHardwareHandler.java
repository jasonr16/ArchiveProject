package chronoTimerMain.simulator;

import java.util.ArrayList;

public class ChronoHardwareHandler {
	
	private ArrayList<Sensor> sensors;
	private boolean power = false;
	
	/**
	 * 
	 * Interaction between simulator and rest of ChronoTimer
	 * @param command
	 **/
	public void inputFromSimulator(String command) {
		if(!power){
			return;
		}
		switch (command){
			
		}
	}
	public void inputFromSimulator(String command, String[] args) {
		//TODO call from simulator
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
