package chronoTimerMain.software.eventHandler;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import chronoTimerMain.software.RaceIND;
import chronoTimerMain.software.RacePARIND;
import chronoTimerMain.software.eventHandler.commands.EventCommand;

public class Import implements EventCommand{
	String timestamp;
	ChronoTimerEventHandler cTEH;
	public Import(ChronoTimerEventHandler cTEH, String timestamp) {
		this.timestamp = timestamp;
		this.cTEH = cTEH;
	}
	@Override
	public void execute(String[] args) {
		Gson gson = new Gson();
		System.out.println(timestamp+" Importing race # " + args[0] + ".");
		try {
			if(cTEH.raceType.equalsIgnoreCase("IND")) 
				cTEH.race = gson.fromJson(new FileReader("Race " + args[0]), RaceIND.class);
			else if(cTEH.raceType.equalsIgnoreCase("PARIND"))
				cTEH.race = gson.fromJson(new FileReader("Race " + args[0]), RacePARIND.class);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
