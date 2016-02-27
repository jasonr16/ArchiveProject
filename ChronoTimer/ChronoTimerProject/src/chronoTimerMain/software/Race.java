package chronoTimerMain.software;

import java.util.ArrayList;

public abstract class Race {
	private Timer timer;
	private ArrayList<Racer> racerList;
	
	public Race(Timer time){};
	/**
	 * Duration is stored in racerList-obtain with getDuration(int racerNumber). 
	 * getRacerDuration is used by ChronoTimerEventHandler's print() method to print racer times.
	 * @param racerNumber
	 * @return racerNumber's time as String in format hh:mm:ss.ss
	 */
	public String getRacerDuration(int racerNumber) {
		return null;}
	/**
	 * sets a racer to start next. Adds the racer to racerList with the specified number.
	 * @param racerNumber
	 */
	public void num(int racerNumber){}
	/**
	 * removes the specified racer from racerList
	 * @param racerNumber
	 */
	public void clr(int racerNumber){}

	public abstract void swap();

	public abstract void dnf();

	public abstract void start();

	public abstract void finish();
	public abstract void trig(int channel);
	
	
}
