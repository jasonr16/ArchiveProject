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
		startList = cTEH.race.getStartList();
		runningList = cTEH.race.getRunningList();
		finishList = cTEH.race.getFinishList();
		if(cTEH.race instanceof RaceIND) {
			displayRacerList(startList, 0, Math.min(2, startList.size()-1));//display up to the next 3 racers to start
			cTEH.display += "\n";
			displayRacerList(runningList, 0, runningList.size()-1);//display all running racers
			cTEH.display += "\n";
			displayRacerList(finishList, Math.max(0, finishList.size()-1), Math.max(-1, finishList.size()-1));//display last racer to finish
		}
		else if (cTEH.race instanceof RacePARIND) {
			displayRacerList(startList, 0, Math.min(2, startList.size()-1));
			cTEH.display += "\n";
			displayRacerList(runningList, 0, runningList.size()-1);
			cTEH.display += "\n";
			displayRacerList(finishList, Math.max(0, finishList.size()-2), Math.max(-1, finishList.size()-1));
		}
		else if (cTEH.race instanceof RaceGRP) {
			String rTime;
			if(cTEH.race.getStartTime() != null) {
				
				if(timestamp.equals("")) {
					cTEH.display += "00000 " + cTEH.timer.getRunDuration(cTEH.race.getStartTime(), cTEH.timer.getCurrentChronoTime());
				}
				else {
					cTEH.display += "00000 " + cTEH.timer.getRunDuration(cTEH.race.getStartTime(), timestamp);
				}
			}
			cTEH.display += "\n";
			displayRacerList(finishList, Math.max(0, finishList.size()-1), Math.max(-1, finishList.size()-1));//display last racer to finish
		}
		//TODO PARGRP
	}
	
	private void displayRacerList(ArrayList<Racer> raceList, int finishIndex, int startIndex) {//racers are displayed in reverse queue order
		for(int i = startIndex; i >= finishIndex; i--) {
			if(timestamp.equals("")) {
				if(raceList == finishList) {
					cTEH.display += raceList.get(i).getNumber() + " " + 
							cTEH.timer.getRunDuration(raceList.get(i).getStartTime(), raceList.get(i).getFinishTime());
				}
				else {
					cTEH.display += raceList.get(i).getNumber() + " " + 
							cTEH.timer.getRunDuration(raceList.get(i).getStartTime(), cTEH.timer.getCurrentChronoTime());
				}
			}
			else
				cTEH.display += raceList.get(i).getNumber() + " " + //for testing purposes
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
}
