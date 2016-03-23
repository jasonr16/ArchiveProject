package chronoTimerMain.software;

import java.util.ArrayList;
/**
 * ChronoTimerEventHandler parses the commands that are not hardware related. 
 * This class passes commands specific to a raceType to that specific Race object to be properly implemented.
 * Commands that are applicable to any race type are implemented here
 * @author Jason
 *
 */
public class ChronoTimerEventHandler {
	private Timer timer;
	private ArrayList<Race> raceList = new ArrayList<Race>();
	private Race race;
	private int runNumber = 1;
	private String raceType = "IND";
	private String display = "";
	
	public ChronoTimerEventHandler(Timer timer) {
		this.timer = timer;
		race = new RaceIND(runNumber, timer);
		raceList.add(race);
	}
	/**
	 * timeEvent processes the commands obtained from the ChronoHardwareHandler class
	 * @param s the command
	 * @param args command parameters
	 * @param timestamp time from file, if file input
	 */
	public void timeEvent(String s, String[] args, String timestamp){
		
		if(isTime(s)) {
			System.out.println(timestamp + "Setting chrono time as " + args[0]);
			timer.time(args[0]);
		}
		else if (isNum(s)) {
			System.out.println(timestamp + "Adding racer number " +args[0]);
			try {
			race.addRacerToStart(Integer.parseInt(args[0]));	
			} catch (NumberFormatException e) {
				System.out.println("Error - invalid number.");
			}
		}
		else if (isClr(s)) { 
			System.out.println(timestamp + "Removing racer number " + args[0]);
			try {
				race.removeRacerFromStart((Integer.parseInt(args[0])));	
				} catch (NumberFormatException e) {
					System.out.println("Error - invalid number.");
				}
		}
		else if (isSwap(s)) {
			System.out.println(timestamp + "Swapping racers.");
			race.swapRunningRacers();
		}
		else if (isStart(s)) {
			System.out.println(timestamp + "Start triggered");
			race.start(timestamp);
		}
		else if (isFinish(s)) {
			System.out.println(timestamp + "Finish triggered");
			race.finish(timestamp);
		}
		else if (isTrig(s)) {
			System.out.println(timestamp + "Trigger event happened.");
			try {
			race.trig(Integer.parseInt(args[0]), timestamp);
			}catch (NumberFormatException e) {
				System.out.println(timestamp + "Error - invalid number.");
			}
		}
		else if (isDNF(s)) {
			System.out.println(timestamp + "Racer DNF");
			race.handleRacerDNF();
		}
		else if (isNewRun(s)) {
			System.out.println(timestamp + "New Run Started.");
			this.newRun();
		}
		else if (isEndRun(s)) {
			System.out.println(timestamp + "Run ended.");
			this.endRun();
		}
		else if (isEvent(s)) {
			System.out.println(timestamp + "Setting event type " + args[0]);
			this.event(args[0]);
		}
		else if (isPrint(s)) {
			System.out.println(timestamp + "Printing racer times to console\n");
			if(args[0]==null){
				this.print();
			}else{
				try{
					this.print(Integer.parseInt(args[0])-1);
				}catch(NumberFormatException e){		  
					e.printStackTrace();
				}
			}
		}
		else if (s.equalsIgnoreCase("DISPLAY")) {
			System.out.println(timestamp + "Printing display.");
			updateChronoDisplay(timestamp);
			System.out.println(display);
		}
		else if(isCancel(s)){
			System.out.println(timestamp+"Cancel.");
			race.handleRacerCancel();
		}
		
	};
	/**
	 * used for gui display text showing start, running, finished racer number and times.
	 * @param timestamp timestamp from file, if file input
	 */
	public void updateChronoDisplay(String timestamp) {
		ArrayList<Racer> startList = race.getStartList();
		ArrayList<Racer> runningList = race.getRunningList();
		ArrayList<Racer> finishList = race.getFinishList();
		for(int i = startList.size()-1; i >=0; i--) {//display queued racers
			if(timestamp.equals("")) {
				display += startList.get(i).getNumber() + " " + timer.getCurrentChronoTime();
			}
			else
				display += startList.get(i).getNumber() + " " + timestamp;
			if(i==0)
				display += " <\n";
			else
				display += "\n";
		}
		display += "\n\n";
		for(int i = 0; i < runningList.size(); i++) {//display running racers
			if(timestamp.equals("")) {
				display += runningList.get(i).getNumber() + " " + 
			timer.getRunDuration(runningList.get(i).getStartTime(), timer.getCurrentChronoTime());
			}
			else
				display += runningList.get(i).getNumber() + " " + 
			timer.getRunDuration(runningList.get(i).getStartTime(), timestamp);
			
			display += " R\n";
		}
		display += "\n";
		for(int i = 0; i < finishList.size(); i++) {//display finished racers
			if(timestamp.equals("")) {
				display += finishList.get(i).getNumber() + " " + 
			timer.getRunDuration(finishList.get(i).getStartTime(), finishList.get(i).getFinishTime());
			}
			else
				display += finishList.get(i).getNumber() + " " + timestamp;
			
			display += " F\n";
		}
	}
	/**
	 * sets the Race type
	 * @param eventType
	 */
	public void event(String eventType){
		raceType = eventType;
	};
	/**
	 * creates a newRun, ending the previous run first
	 */
	public void newRun(){
		race.endRun(); // make sure previous run has ended
		if (raceType.equals("IND")){
			race = new RaceIND(++runNumber, timer);
			raceList.add(race);
		}
		//TODO add other race types
	};
	/**
	 * ends the current run by marking all non-finished racers as DNF.
	 */
	public void endRun(){ //TODO prevent new racers from being entered until new run is created.
		race.endRun();
	};
	/**
	 * print displays the racer numbers followed by a time as
	 * (number time optionalFlag).
	 *  optionalFlag is >, R, or F. time is system, elapsed, or total. 
	 * 
	 */
	public void print(){
		race.print();
	};
	/**
	 * prints the race number details, including previously completed races still stored in system.
	 * @param number the number associated with a race, initially one and incremented every new run.
	 */
	public void print(int number){//number has already been decremented in timeEvent
		raceList.get(number).print();
	}
	
	//boolean helper methods
	private boolean isPrint(String s) {
		return s.equalsIgnoreCase("print");
	}
	private boolean isEvent(String s) {
		return s.equalsIgnoreCase("event");
	}
	private boolean isEndRun(String s) {
		return s.equalsIgnoreCase("endrun");
	}
	private boolean isNewRun(String s) {
		return s.equalsIgnoreCase("newrun");
	}
	private boolean isDNF(String s) {
		return s.equalsIgnoreCase("dnf");
	}
	private boolean isTrig(String s) {
		return s.equalsIgnoreCase("trig") || s.equalsIgnoreCase("trigger");
	}
	private boolean isFinish(String s) {
		return s.equalsIgnoreCase("finish");
	}
	private boolean isStart(String s) {
		return s.equalsIgnoreCase("start");
	}
	private boolean isSwap(String s) {
		return s.equalsIgnoreCase("swap");
	}
	private boolean isClr(String s) {
		return s.equalsIgnoreCase("clr");
	}
	private boolean isNum(String s) {
		return s.equalsIgnoreCase("num");
	}
	private boolean isTime(String s) {
		return s.equalsIgnoreCase("time");
	}
	private boolean isCancel(String s){
		return s.equalsIgnoreCase("Cancel");
	}
	
}
