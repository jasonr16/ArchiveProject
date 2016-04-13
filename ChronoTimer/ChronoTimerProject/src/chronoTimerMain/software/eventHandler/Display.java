package chronoTimerMain.software.eventHandler;

import java.util.ArrayList;

import chronoTimerMain.software.eventHandler.commands.EventCommand;
import chronoTimerMain.software.racetypes.Racer;
import chronoTimerMain.software.racetypes.RaceGRP;
import chronoTimerMain.software.racetypes.RaceIND;
import chronoTimerMain.software.racetypes.RaceIND.raceINDTester;
import chronoTimerMain.software.racetypes.RacePARIND;
/**
 * used for gui display text showing start, running, finished racer number and times.
 * @param timestamp timestamp from file, if file input
 */
public class Display implements EventCommand{
	String timestamp;
	ChronoTimerEventHandler cTEH;
	ArrayList<Racer> startList ;
	ArrayList<Racer> runningList;
	ArrayList<Racer> finishList;
	public Display(ChronoTimerEventHandler cTEH, String timestamp) {
		this.timestamp = timestamp;
		this.cTEH = cTEH;
	}

	@Override
	public void execute(String[] args) {
		cTEH.display = "";
		System.out.println(timestamp + " Printing display.");
		startList = cTEH.race.getStartList();
		runningList = cTEH.race.getRunningList();
		finishList = cTEH.race.getFinishList();
		if(cTEH.race instanceof RaceIND) {
			displayRacerList(startList, 0, Math.min(3, startList.size()));
			cTEH.display += "\n";
			displayRacerList(runningList, 0, runningList.size());
			cTEH.display += "\n";
			displayRacerList(finishList, getFirstRacerFinishIndexToDisplay(finishList), Math.min(1, finishList.size()));
		}
		else if (cTEH.race instanceof RacePARIND) {
			displayRacerList(startList, 0, Math.min(2, startList.size()));
			cTEH.display += "\n";
			displayRacerList(runningList, 0, runningList.size());
			cTEH.display += "\n";
			displayRacerList(finishList, getFirstRacerFinishIndexToDisplay(finishList), Math.min(2, finishList.size()));
		}
//TODO		else if (cTEH.race instanceof RaceGRP) {
//			displayRacerList(runningList, 0, runningList.size()-1);
//			cTEH.display += "\n";
//			displayRacerList(finishList, getFirstRacerFinishIndexToDisplay(finishList), Math.min(1, finishList.size()));
//		}
		//TODO PARGRP
	}
	
	private void displayRacerList(ArrayList<Racer> raceList, int firstRacerIndex, int numOfRacers) {
		for(int i = numOfRacers-1; i >= 0; i--) {
			if(timestamp.equals("")) {
				cTEH.display += raceList.get(i).getNumber() + " " + 
						cTEH.timer.getRunDuration(raceList.get(i).getStartTime(), cTEH.timer.getCurrentChronoTime());
			}
			else
				cTEH.display += raceList.get(i).getNumber() + " " + 
						cTEH.timer.getRunDuration(raceList.get(i).getStartTime(), timestamp);
			
			if(raceList == startList && i == 0) 
			cTEH.display += " >\n";
			else if(raceList == runningList) 
				cTEH.display += " R\n";
			else if (raceList == finishList)
				cTEH.display += " F\n";
			else
				cTEH.display += "\n";
		}
	}
	
	private int getFirstRacerFinishIndexToDisplay(ArrayList<Racer> raceList) {
		int numRacers = 0;
		//only finishList needs to be adjusted for the last racers in the array
		if(cTEH.race instanceof RaceIND /*TODO || cTEH.race instanceof RaceGRP*/) {
			
			if(raceList == finishList) {
				if (raceList.size() > 1) {
					numRacers = raceList.size()-1;//IND should display last one to finish
				}
			}
		}
		else if (cTEH.race instanceof RacePARIND) {
			if(raceList == finishList) {
				if (raceList.size() > 2) {
					numRacers =  raceList.size()-2;//PARIND should display last two to finish
				}
			}
		}
		else {
			//TODO Jason PARGRP
		}
		return numRacers;//default value
	}
}
