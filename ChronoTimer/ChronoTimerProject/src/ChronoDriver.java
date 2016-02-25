import java.io.File;

import chronoTimerMain.simulator.Simulator;

public class ChronoDriver {
	public static void main (String [] args){
		Simulator consoleSim = new Simulator();
		Simulator fileSim = new Simulator(new File("PLACEHOLDER"));
	};
}
