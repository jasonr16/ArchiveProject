package chronoTimerMain.software.eventHandler;


import java.util.ArrayList;

import chronoTimerMain.software.Timer.Timer;
import chronoTimerMain.software.eventHandler.commands.EventCommand;
import chronoTimerMain.software.racetypes.RaceIND;
import chronoTimerMain.software.racetypes.race.Race;
import gui.ChronoGUI;

/**
 * ChronoTimerEventHandler parses the commands that are not hardware related. 
 * This class passes commands specific to a raceType to that specific Race object to be properly implemented.
 * Commands that are applicable to any race type are implemented here
 * 
 * @author Jason
 *
 */
public class ChronoTimerEventHandler {
	ChronoGUI cGUI;
	Timer timer;
	ArrayList<Race> raceList = new ArrayList<Race>();
	Race race;
	int runNumber = 1;
	String raceType = "IND";
	String display = "";
	public String getDisplay() {
		return display;
	}

	EventCommandFactory eCmdFactory;
	EventCommand currentCommand;
	
	public ChronoTimerEventHandler() {
		timer = new Timer();
		race = new RaceIND(runNumber, timer);
		raceList.add(race);//add default race for zero index arraylist
		eCmdFactory = new EventCommandFactory(this);
		
	}
	public ChronoTimerEventHandler(Timer timer) {
		this.timer = timer;
		race = new RaceIND(runNumber, timer);
		raceList.add(race);//add default race for zero index arraylist
		eCmdFactory = new EventCommandFactory(this);
		

	}
	/** 
	 * Invokes the event passed to this class.
	 * @param s
	 * @param args
	 * @param timestamp
	 */
	public void timeEvent(String s, String[] args, String timestamp) {
		timestamp = timestamp.trim();
		currentCommand = eCmdFactory.getTimeEvent(s, timestamp);
		currentCommand.execute(args);
	}

	/**
	 * prints the race number details, including previously completed races still stored in system.
	 * @param number the number associated with a race, initially one and incremented every new run.
	 */
	public void print(int number){//number has already been decremented in timeEvent
		raceList.get(number).print();
	}
}
