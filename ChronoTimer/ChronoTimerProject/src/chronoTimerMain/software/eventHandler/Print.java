package chronoTimerMain.software.eventHandler;

import chronoTimerMain.software.eventHandler.commands.EventCommand;
/**
 * prints the race number details, including previously completed races still stored in system. 
 * No parameter method prints the current race.
 * @param number the number associated with a race, initially one and incremented every new run.
 */
public class Print implements EventCommand{
	String timestamp;
	ChronoTimerEventHandler cTEH;
	public Print(ChronoTimerEventHandler cTEH, String timestamp) {
		this.timestamp = timestamp;
		this.cTEH = cTEH;
	}

	@Override
	public void execute(String[] args) {
		System.out.println(timestamp + " Printing racer times to console\n");
		if(args[0]==null){
			cTEH.race.print();
		}else{
			try{
				cTEH.print(Integer.parseInt(args[0])-1);
			}catch(NumberFormatException e){		  
				e.printStackTrace();
			}
		}
	}
}
