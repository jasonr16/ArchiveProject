package chronoTimerMain.simulator;

import java.util.ArrayList;

public class ChronoHardwareHandler {
	
	private ArrayList<Sensor> sensors;
	
	public void inputFromSimulator(String command) {
		//TODO call from simulator
	}
	public void power(boolean s){};
	public void exit(){};
	public void reset() {};
	public void toggle(int channel){};
	public void conn(Sensor type, int channel){};
	public void disc(int channel){};
}
