package chronoTimerMain.software.eventHandler.commands;

import chronoTimerMain.software.racetypes.race.Race;

public class RaceEvents implements EventCommand {
	Race race;
	String cmd;
	String timestamp;
	public RaceEvents(String cmd, Race race, String timestamp) {
		this.cmd = cmd;
		this.race = race;
		this.timestamp = timestamp;
	}
	@Override
	public void execute(String[] args) {
		if(cmd.equalsIgnoreCase("num")) {
			try {
				race.addRacerToStart(Integer.parseInt(args[0]));	
				System.out.println(timestamp + " Adding racer number " +args[0]);
				} catch (NumberFormatException e) {
					System.out.println("Error - invalid number.");
				}
		}
		else if(cmd.equalsIgnoreCase("clr")) {
			try {
				race.removeRacerFromStart((Integer.parseInt(args[0])));	
				System.out.println(timestamp + " Removing racer number " + args[0]);
				} catch (NumberFormatException e) {
					System.out.println("Error - invalid number.");
				}
		}
		else if (cmd.equalsIgnoreCase("swap")) {
			System.out.println(timestamp + " Swapping racers.");
			race.swapRunningRacers();
		}
		else if (cmd.equalsIgnoreCase("start")) {
			System.out.println(timestamp + " Start triggered");
			race.start(timestamp);
		}
		else if (cmd.equalsIgnoreCase("finish")) {
			System.out.println(timestamp + " Finish triggered");
			race.finish(timestamp);
		}
		else if (cmd.equalsIgnoreCase("trig") || cmd.equalsIgnoreCase("trigger")) {
			try {
			race.trig(Integer.parseInt(args[0]), timestamp);
			System.out.println(timestamp + " Trigger event happened.");
			}catch (NumberFormatException e) {
				System.out.println(timestamp + "Error - invalid number.");
			}
		}
		else if(cmd.equalsIgnoreCase("dnf")) {
			System.out.println(timestamp + " Racer DNF");
			race.handleRacerDNF();
		}
		else if(cmd.equalsIgnoreCase("endrun")) {
			System.out.println(timestamp + " Run ended.");
			race.endRun();
		}
		else if(cmd.equalsIgnoreCase("cancel")) {
			System.out.println(timestamp+" Cancel.");
			race.handleRacerCancel();
		}
		
	}
}
