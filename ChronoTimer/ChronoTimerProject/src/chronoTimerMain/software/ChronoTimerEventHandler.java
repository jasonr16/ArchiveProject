package chronoTimerMain.software;


public class ChronoTimerEventHandler {
	private Timer timer;
	private Race race;
	
	public void timeEvent(){};
	public void event(){};
	public void newRun(){};
	public void endRun(){};
	/**
	 * print displays the racer numbers followed by a time as
	 * (number time optionalFlag).
	 *  optionalFlag is >, R, or F. time is system, elapsed, or total. 
	 * 
	 * Implementation? Access Racers in racerList (Race class) and retrieve/print each racer number and time
	 */
	public void print(){};
}
