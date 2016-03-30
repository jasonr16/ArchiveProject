package chronoTimerMain.software.eventHandler;

import java.util.ArrayList;

import chronoTimerMain.software.Racer;
import chronoTimerMain.software.eventHandler.commands.EventCommand;
/**
 * used for gui display text showing start, running, finished racer number and times.
 * @param timestamp timestamp from file, if file input
 */
public class Display implements EventCommand{
	String timestamp;
	ChronoTimerEventHandler cTEH;
	public Display(ChronoTimerEventHandler cTEH, String timestamp) {
		this.timestamp = timestamp;
		this.cTEH = cTEH;
	}

	@Override
	public void execute(String[] args) {
		System.out.println(timestamp + " Printing display.");
		ArrayList<Racer> startList = cTEH.race.getStartList();
		ArrayList<Racer> runningList = cTEH.race.getRunningList();
		ArrayList<Racer> finishList = cTEH.race.getFinishList();
		for(int i = startList.size()-1; i >=0; i--) {//display queued racers
			if(timestamp.equals("")) {
				cTEH.display += startList.get(i).getNumber() + " " + cTEH.timer.getCurrentChronoTime();
			}
			else
				cTEH.display += startList.get(i).getNumber() + " " + timestamp;
			if(i==0)
				cTEH.display += " <\n";
			else
				cTEH.display += "\n";
		}
		cTEH.display += "\n\n";
		for(int i = 0; i < runningList.size(); i++) {//display running racers
			if(timestamp.equals("")) {
				cTEH.display += runningList.get(i).getNumber() + " " + 
						cTEH.timer.getRunDuration(runningList.get(i).getStartTime(), cTEH.timer.getCurrentChronoTime());
			}
			else
				cTEH.display += runningList.get(i).getNumber() + " " + 
						cTEH.timer.getRunDuration(runningList.get(i).getStartTime(), timestamp);
			
			cTEH.display += " R\n";
		}
		cTEH.display += "\n";
		for(int i = 0; i < finishList.size(); i++) {//display finished racers
			if(timestamp.equals("")) {
				cTEH.display += finishList.get(i).getNumber() + " " + 
						cTEH.timer.getRunDuration(finishList.get(i).getStartTime(), finishList.get(i).getFinishTime());
			}
			else
				cTEH.display += finishList.get(i).getNumber() + " " + timestamp;
			
			cTEH.display += " F\n";
		}
		System.out.println(cTEH.display);
	}

}
