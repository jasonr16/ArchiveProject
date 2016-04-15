package chronoTimerMain.software.eventHandler;

import chronoTimerMain.software.eventHandler.commands.EventCommand;
import chronoTimerMain.software.racetypes.RaceGRP;
import chronoTimerMain.software.racetypes.RaceIND;
import chronoTimerMain.software.racetypes.RacePARIND;
/**
 * sets the Race type
 * @param eventType
 */
public class Event implements EventCommand {
	String timestamp;
	ChronoTimerEventHandler cTEH;
	public Event(ChronoTimerEventHandler cTEH, String timestamp) {
		this.timestamp = timestamp;
		this.cTEH = cTEH;
	}

	@Override
	public void execute(String[] args) {
		System.out.println(timestamp + " Setting event type " + args[0]);
		if(args[0].equalsIgnoreCase("IND") || args[0].equalsIgnoreCase("PARIND"))
			cTEH.raceType = args[0];
		//change current racetype if not started
		if(cTEH.race.getStartList().size() != 0 && cTEH.race.getRunningList().size() == 0 && cTEH.race.getFinishList().size() == 0)
			if(args[0].equalsIgnoreCase("IND"))
				cTEH.race = new RaceIND(cTEH.runNumber, cTEH.timer, cTEH.race.getStartList());
			else if(args[0].equalsIgnoreCase("PARIND"))
				cTEH.race = new RacePARIND(cTEH.runNumber, cTEH.timer, cTEH.race.getStartList());
			else if(args[0].equalsIgnoreCase("GRP"))
				cTEH.race = new RaceGRP(cTEH.runNumber, cTEH.timer, cTEH.race.getStartList());
		//TODO pargrp
	}
}
