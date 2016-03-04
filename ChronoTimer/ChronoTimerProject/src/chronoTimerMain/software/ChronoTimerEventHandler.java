package chronoTimerMain.software;


public class ChronoTimerEventHandler {
	private Timer timer;
	private Race race;
	private int runNumber = 1;
	private String raceType;
	
	public ChronoTimerEventHandler(Timer timer) {
		this.timer = timer;
		race = new RaceIND(runNumber, timer);
	}
	
	public void timeEvent(String s, String[] args){
		
		if(isTime(s)) {
			timer.time(args[0]);
		}
		else if (isNum(s)) {
			try {
			race.addRacerToStart(Integer.parseInt(args[0]));	
			} catch (NumberFormatException e) {
				System.out.println("Error - invalid number.");
			}
		}
		else if (isClr(s)) {
			try {
				race.removeRacerFromStart((Integer.parseInt(args[0])));	
				} catch (NumberFormatException e) {
					System.out.println("Error - invalid number.");
				}
		}
		else if (isSwap(s)) {
			race.swapRunningRacers();
		}
		else if (isStart(s)) {
			race.start();
		}
		else if (isFinish(s)) {
			race.finish();
		}
		else if (isTrig(s)) {
			try {
			race.trig(Integer.parseInt(args[0]));
			}catch (NumberFormatException e) {
				System.out.println("Error - invalid number.");
			}
		}
		else if (isDNF(s)) {
			race.handleRacerDNF();
		}
		else if (isNewRun(s)) {
			this.newRun();
		}
		else if (isEndRun(s)) {
			this.endRun();
		}
		else if (isEvent(s)) {
			this.event(args[0]);
		}
		else if (isPrint(s)) {
			this.print();
		}
	};
	
	public void event(String string){
		System.out.println("Creating new event " + string);
		raceType = string;
	};

	public void newRun(){
		race.setRunNumber(race.getRunNumber()+1);
		//TODO add other race types
		race = new RaceIND(runNumber, timer);
		
		System.out.println("A new race has begun.");
	};
	public void endRun(){
		System.out.println("The race has ended.");
	};
	/**
	 * print displays the racer numbers followed by a time as
	 * (number time optionalFlag).
	 *  optionalFlag is >, R, or F. time is system, elapsed, or total. 
	 * 
	 */
	public void print(){
		System.out.println("Printing run data.");
		race.print();
	};
	
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
	
}
