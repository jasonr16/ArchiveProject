package chronoTimerMain.software.eventHandler;

import chronoTimerMain.software.Timer.Time;
import chronoTimerMain.software.Timer.Timer;
import chronoTimerMain.software.eventHandler.commands.EventCommand;
import chronoTimerMain.software.eventHandler.commands.RaceEvents;

public class EventCommandFactory {
	/** EventCommandFactory creates the proper event command class to execute. 
	 * If it is not a command eventhandler
	 * executes, it passes the correct class to handle it with the command (Race or Timer) object 
	 * from the EventHandler class parameter.  **/
	private ChronoTimerEventHandler cTEH;
	public EventCommandFactory(ChronoTimerEventHandler cTEH) {
		this.cTEH = cTEH;
	}
	
	public EventCommand getTimeEvent(String cmd, String timestamp) {
		if(cmd.equalsIgnoreCase("newrun")) {
			return new NewRun(cTEH, timestamp);
		}
		else if(cmd.equalsIgnoreCase("event")) {
			return new Event(cTEH, timestamp);
		}
		else if(cmd.equalsIgnoreCase("print")) {
			return new Print(cTEH, timestamp);
		}
		else if(cmd.equalsIgnoreCase("display")) {
			return new Display(cTEH, timestamp);
		}
		else if(cmd.equalsIgnoreCase("export")) {
			return new Export(cTEH, timestamp);
		}
		else if (cmd.equalsIgnoreCase("import")) {
			return new Import(cTEH, timestamp);
		}
		else if(cmd.equalsIgnoreCase("time")) {
			return new Time(cTEH.timer, timestamp);
		}
		else 
			return new RaceEvents(cmd, cTEH.race, timestamp);
		
		
	}
}
