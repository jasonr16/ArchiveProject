package chronoTimerMain.simulator;

public class ChronoHardwareHandler {
	public void power(boolean s){};
	public void exit(){};
	public void reset() {};
	public void toggle(int channel){};
	public void conn(Sensor type, int channel){};
	public void disc(int channel){};
}
