package chronoTimerMain.software.eventHandler;

import chronoTimerMain.software.RaceIND;
import chronoTimerMain.software.RacePARIND;
import chronoTimerMain.software.eventHandler.commands.EventCommand;
/**
 * creates a newRun, ending the previous run first
 */
public class NewRun implements EventCommand {
	String timestamp;
	ChronoTimerEventHandler cTEH;
	public NewRun(ChronoTimerEventHandler cTEH, String timestamp) {
		this.timestamp = timestamp;
		this.cTEH = cTEH;
	}

	@Override
	public void execute(String[] args) {
		System.out.println(timestamp + " New Run Started.");
		cTEH.race.endRun(); // make sure previous run has ended
		if (cTEH.raceType.equals("IND")){
			cTEH.race = new RaceIND(++cTEH.runNumber, cTEH.timer);
			cTEH.raceList.add(cTEH.race);
		}
		else if (cTEH.raceType.equals("PARIND")){
			cTEH.race = new RacePARIND(++cTEH.runNumber, cTEH.timer);
			cTEH.raceList.add(cTEH.race);
		}
		//TODO add other race types
	}
	
}
