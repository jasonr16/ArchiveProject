package chronoTimerMain.software.eventHandler;

import chronoTimerMain.software.eventHandler.commands.EventCommand;
import chronoTimerMain.software.racetypes.RaceGRP;
import chronoTimerMain.software.racetypes.RaceIND;
import chronoTimerMain.software.racetypes.RacePARIND;
/**
 * creates a newRun, ending the previous run first
 */
public class NewRun implements EventCommand {
	String timestamp;
	ChronoTimerEventHandler cTEH;
	boolean[] toggles;
	public NewRun(ChronoTimerEventHandler cTEH, String timestamp) {
		this.timestamp = timestamp;
		this.cTEH = cTEH;
	}

	@Override
	public void execute(String[] args) {
		System.out.println(timestamp + " New Run Started.");
		cTEH.race.endRun(); // make sure previous run has ended
		if (cTEH.raceType.equals("IND")){
			toggles = cTEH.race.getChannelToggles();
			cTEH.raceList.add(cTEH.race);
			cTEH.race = new RaceIND(++cTEH.runNumber, cTEH.timer);
			cTEH.race.replaceToggles(toggles);
			
		}
		else if (cTEH.raceType.equals("PARIND")){
			cTEH.raceList.add(cTEH.race);
			toggles = cTEH.race.getChannelToggles();
			cTEH.race = new RacePARIND(++cTEH.runNumber, cTEH.timer);
			cTEH.race.replaceToggles(toggles);
			
		}
		else if (cTEH.raceType.equals("GRP")){
			cTEH.raceList.add(cTEH.race);
			toggles = cTEH.race.getChannelToggles();
			cTEH.race = new RaceGRP(++cTEH.runNumber, cTEH.timer);
			cTEH.race.replaceToggles(toggles);
			
		}
		//TODO Jason add other race types
	}
	
}
