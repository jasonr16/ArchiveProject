package chronoTimerMain.software.racetypes.race;

import java.util.ArrayList;

import chronoTimerMain.simulator.Sensor;
import chronoTimerMain.simulator.sensor.*;
import chronoTimerMain.software.Timer.Timer;
import chronoTimerMain.software.racetypes.Racer;

/**
 * Represents a single generic race event with multiple participating racers.
 * A race is created via the NEWRUN command in ChronoTimer, or by default when the system
 * is first powered on.
 * Always contains a Timer object that is associated with ChronoTimer's internal clock.
 */

public abstract class Race {
	protected String startTime;
	private int runNumber;
	private Timer timer;
	private ArrayList<Racer> startList; // queue of racers who are about to start
	private ArrayList<Racer> runningList; // queue of racers who are running
	private ArrayList<Racer> finishList; // queue of racers who have finished
	private boolean[] channelToggles = new boolean[13];
	protected Sensor[] sensors = new Sensor[13];//no sensor stored in index 0. 12 max
	ClientHTML cHTML = new ClientHTML();

	public String getStartTime() {
		return startTime;
	}
	protected Race() {
	}
	
	protected Race(int runNumber, Timer timer){
		this.runNumber = runNumber;
		this.timer = timer;
		this.startList = new ArrayList<Racer>();
		this.runningList = new ArrayList<Racer>();
		this.finishList = new ArrayList<Racer>();
	}
	
	protected Race(int runNumber, Timer timer, ArrayList<Racer> startList) {
		this.runNumber = runNumber;
		this.timer = timer;
		this.startList = startList;
		this.runningList = new ArrayList<Racer>();
		this.finishList = new ArrayList<Racer>();
	}
	
	/**
	 * Gets the run number of the race
	 * @return run number
	 */
	public int getRunNumber() {
		return runNumber;
	}

	/**
	 * Sets the run number of the race
	 * @param run number
	 */
	public void setRunNumber(int runNumber) {
		this.runNumber = runNumber;
	}
	
	/**
	 * Gets the timer associated with the race
	 * @return timer
	 */
	public Timer getTimer() {
		return this.timer;
	}
	
	/**
	 * Gets the start queue for the race
	 * @return startList
	 */
	public ArrayList<Racer> getStartList() {
		return this.startList;
	}
	
	/**
	 * Gets the running queue for the race
	 * @return runningList
	 */
	public ArrayList<Racer> getRunningList() {
		return this.runningList;
	}
	
	/**
	 * Gets the finish queue for the race
	 * @return finishList
	 */
	public ArrayList<Racer> getFinishList() {
		return this.finishList;
	}
	
	/**
	 * Calculates the run duration of a racer who is running or finished a race
	 * @param racerNum AS A STRING
	 * @return the formatted time duration of a racer's run. If the racer did not start yet,
	 * returns a duration of zero.
	 */
	public String getRacerDuration(String racerNum) {
		String result = "";
		Racer racer = null;
		// if racer is in start queue, return zero duration
		for(int i = 0; i < startList.size(); i++) {
			if (startList.get(i).getNumber().equals(racerNum)) {
				racer = startList.get(i);
				result = timer.getRunDuration(racer.getStartTime(), racer.getStartTime());
			}
		}
		
		// if racer is in running queue, return duration between current time and start time
		if (racer == null) {
			for(int i = 0; i < runningList.size(); i++) {
				if (runningList.get(i).getNumber().equals(racerNum)) {
					racer = startList.get(i);
					result = timer.getRunDuration(racer.getStartTime(), timer.getCurrentChronoTime());
				}
			}
		}
		
		// if racer is in finish queue and actually finished, return duration between
		// finish time and start time
		if (racer == null) {
			for(int i = 0; i < finishList.size(); i++) {
				if (finishList.get(i).getNumber().equals(racerNum)) {
					racer = finishList.get(i);
					// if racer did not finish, return "DNF"
					if (racer.getDNF())
						result = "DNF";
					else
						result = timer.getRunDuration(racer.getStartTime(), racer.getFinishTime());
				}
			}
		}
		
		// if racer not found in any queue, return "Racer not found"
		if (racer == null) {
			result = "Racer " + racerNum + " not found";
		}
		return result;
	}
	
	/**
	 * Method for finding the correct Racer in the race queues.
	 * @param racerNum
	 * @return Racer with number raceNumber, or null if does not exist
	 */
	public Racer getCorrectRacer(String racerNum) {
		for(int i = 0; i < startList.size(); i++) {
			if (startList.get(i).getNumber().equals(racerNum)) {
				return startList.get(i);
			}
		}
		for(int i = 0; i < runningList.size(); i++) {
			if (runningList.get(i).getNumber().equals(racerNum)) {
				return runningList.get(i);
			}
		}
		for(int i = 0; i < finishList.size(); i++) {
			if (finishList.get(i).getNumber().equals(racerNum)) {
				return finishList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Adds a racer as the next racer to start in race
	 * Corresponds to the NUM <NUMBER> command
	 * @param racer
	 */
	public abstract boolean num(String racerNum);
	
	/**
	 * Removes the next racer to start from the race
	 * Corresponds to the CLR <NUMBER> command
	 * @param racerNum
	 */
	public abstract boolean removeRacerFromStart(String racerNum);

	/**
	 * Marks the next racer to finish as DNF
	 * Corresponds to the DNF command
	 */
	public abstract boolean handleRacerDNF();
	
	/**
	 * Cancels the last racer who started to run
	 * Corresponds to the CANCEL command
	 */
	public abstract boolean handleRacerCancel();
	
	/**
	 * Swaps the next two racers expected to finish
	 * Corresponds to the SWAP command
	 */
	public abstract boolean swapRunningRacers();
	
	/**
	 * Moves a racer from the start queue to the running queue
	 */
	public abstract boolean start(String timeStamp);
	
	/**
	 * Moves a racer from the running queue to the finish queue
	 */
	public abstract boolean finish(String timeStamp);
	
	public abstract boolean trig(int channelNum, String timeStamp);

	/**
	 * Prints formatted results of the race
	 * @return 
	 */
	public String print() {//Returns a string now for the GUI to update - JASON
		System.out.println("     Race " + this.runNumber);
		String s = "";
		for(int i = 0; i < finishList.size(); i++) {
			s += "     " + finishList.get(i).getNumber() + "     " + getRacerDuration(finishList.get(i).getNumber()) + "\n";
		}
		s += "\n";
		System.out.print(s);
		return s;
	}

	protected void setStartList(ArrayList<Racer> startList2) {
		startList = startList2;
		
	}

	public void setRunningList(ArrayList<Racer> runningList2) {
		runningList = runningList2;
		
	}

	public void setFinishList(ArrayList<Racer> finishList2) {
		finishList = finishList2;
		
	}
	
	/**
	 * End the run, move all non-finished racers to the finish queue and mark those racers
	 * as DNF.
	 */
	public void endRun() {
		Racer racer = null;
		cHTML = new ClientHTML();
		// move remaining racers in start queue to finish queue and mark them as DNF
		while(startList.size() > 0) {
			racer = startList.remove(0);
			racer.setDNF(true);
			finishList.add(racer);
		}
		
		// move remaining racers in running queue to finish queue and mark them as DNF
		while(runningList.size() > 0) {
			racer = runningList.remove(0);
			racer.setDNF(true);
			finishList.add(racer);
		}
		for(int i = 0; i < finishList.size(); i++) {
			finishList.get(i).setRunTime(getRacerDuration(finishList.get(i).getNumber()));
			cHTML.addRacer(finishList.get(i));
			
		}
		try {
			cHTML.sendData();
		}
		catch(Exception e) {
			System.out.println("Error. Server not available.");
		}
	}
	
	/**
	 * Update race's internal representation of hardware channel toggles
	 * to reflect hardware changes (called by passToggles in eventHandler, which is in turn
	 * invoked by ChronoHardwareHandler when the command "TOG" is input)
	 * @param togArray
	 */
	public void updateTogglesInRace(boolean[] togArray) {
		this.channelToggles = togArray;
	}

	/**
	 * Returns the current toggle state of channels
	 * @return boolean array representation toggle state of channels
	 */
	public boolean[] getChannelToggles() {
		return this.channelToggles;
	}
	
	/**
	 * Used to set channel toggle state during unit tests
	 * @param bool state of all channels
	 */
	protected void setChannelToggles(boolean bool) {
		for(int i = 0; i < this.channelToggles.length; ++i) {
			this.channelToggles[i] = bool;
		}
	}
	
	/**
	 * Used to set channel-to-sensor connection for unit tests
	 */
	protected void setSensorsToPad() {
		for (int i = 0; i < this.sensors.length; ++i) {
			this.sensors[i] = new SensorPad();
		}
	}
	public void setTogsToTrue() {//can remove later
		for(int i = 0; i < this.channelToggles.length; ++i) {
			this.channelToggles[i] = true;
		}
	}
	
	public void replaceToggles(boolean [] toggles) {
		channelToggles = toggles;
	}
	public void updateSensorsInRace(Sensor[] sensors) {
		this.sensors = sensors;
	
		
	}
	public Sensor[] getSensors() {
		// TODO Auto-generated method stub
		return sensors;
	}
	public void replaceSensors(Sensor[] sensors2) {
		this.sensors = sensors2;
		
	}
}
