package chronoTimerMain.software;

import java.util.ArrayList;

public class ChronoTimerEventHandler {
	private Timer timer;
	private ArrayList<Race> raceList=new ArrayList<Race>();
	private Race race;
	private int runNumber = 1;
	private String raceType = "IND";
	private String display = "";
	
	public ChronoTimerEventHandler(Timer timer) {
		this.timer = timer;
		raceList.add(new RaceIND(runNumber, timer));
		race=raceList.get(runNumber-1);
		this.runNumber = 1;
	}
	
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
			System.out.println(timestamp + "End Run Started.");
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
	
	public void updateChronoDisplay(String timestamp) {
		ArrayList<Racer> startList = race.getStartList();
		ArrayList<Racer> runningList = race.getRunningList();
		ArrayList<Racer> finishList = race.getFinishList();
		for(int i = startList.size()-1; i >=0; i--) {
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
		for(int i = 0; i < runningList.size(); i++) {
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
		for(int i = 0; i < runningList.size(); i++) {
			if(timestamp.equals("")) {
				display += runningList.get(i).getNumber() + " " + 
			timer.getRunDuration(runningList.get(i).getStartTime(), runningList.get(i).getFinishTime());
			}
			else
				display += runningList.get(i).getNumber() + " " + timestamp;
			
			display += " F\n";
		}
	}

	public void event(String eventType){
		raceType = eventType;
	};

	public void newRun(){
		race.endRun(); // make sure previous run has ended
		if (raceType.equals("IND")){
			raceList.add(new RaceIND(++runNumber, timer));
			race=raceList.get(runNumber-1);
		
		}
		//TODO add other race types
	};
	public void endRun(){
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
	
	public void print(int number){
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
