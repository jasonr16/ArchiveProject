package chronoTimerMain.software.eventHandler;

import java.util.ArrayList;

import chronoTimerMain.software.eventHandler.commands.EventCommand;
import chronoTimerMain.software.racetypes.RaceGRP;
import chronoTimerMain.software.racetypes.RaceIND;
import chronoTimerMain.software.racetypes.RacePARIND;
import chronoTimerMain.software.racetypes.Racer;
/**
 * sets the Race type
 * @param eventType
 */
public class Event implements EventCommand {
	String timestamp;
	ChronoTimerEventHandler cTEH;
	ArrayList<Racer> tempList;
	public Event(ChronoTimerEventHandler cTEH, String timestamp) {
		this.timestamp = timestamp;
		this.cTEH = cTEH;
	}

	@Override
	public void execute(String[] args) {
		System.out.println(timestamp + " Setting event type " + args[0]);
		if(args[0].equalsIgnoreCase("IND") || args[0].equalsIgnoreCase("PARIND") || args[0].equalsIgnoreCase("GRP"))
			cTEH.raceType = args[0];
		//change current racetype if not started
		if(cTEH.race.getStartList().size() != 0 && cTEH.race.getRunningList().size() == 0 && cTEH.race.getFinishList().size() == 0)
			tempList = new ArrayList<Racer>();
		for(int i = 0; i < cTEH.race.getStartList().size(); i++) {//clone current startlist
			tempList.add(cTEH.race.getStartList().get(i));
		}
		if(args[0].equalsIgnoreCase("IND"))
			cTEH.race = new RaceIND(cTEH.runNumber, cTEH.timer, tempList);
		else if(args[0].equalsIgnoreCase("PARIND"))
			cTEH.race = new RacePARIND(cTEH.runNumber, cTEH.timer, tempList);
		else if(args[0].equalsIgnoreCase("GRP"))
			cTEH.race = new RaceGRP(cTEH.runNumber, cTEH.timer, tempList);
		//TODO pargrp
	}
}
