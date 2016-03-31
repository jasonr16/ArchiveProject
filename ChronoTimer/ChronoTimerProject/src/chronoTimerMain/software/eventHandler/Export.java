package chronoTimerMain.software.eventHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import chronoTimerMain.software.eventHandler.commands.EventCommand;

public class Export implements EventCommand{
	String timestamp;
	ChronoTimerEventHandler cTEH;
	public Export(ChronoTimerEventHandler cTEH, String timestamp) {
		this.timestamp = timestamp;
		this.cTEH = cTEH;
	}
	@Override
	public void execute(String[] args) {
		System.out.println(timestamp+" Exporting to USB.");
		System.out.println(timestamp + " Exporting run " + args[0]);
		Gson gson = new Gson();
		String raceJsonString = gson.toJson(cTEH.race);
		File runFile = new File("Race " + args[0]);
		try {
			FileWriter fW = new FileWriter(runFile, false);
			fW.write(raceJsonString);
			fW.close();
		} catch (IOException e) {
			System.out.println("Error exporting run " + cTEH.runNumber + " to file.");
			e.printStackTrace();
		}
		
		
	}

}
