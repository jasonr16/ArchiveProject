package chronoTimerMain.software.eventHandler;

import chronoTimerMain.software.eventHandler.commands.EventCommand;
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
	}
}
