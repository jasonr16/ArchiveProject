import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import chronoTimerMain.simulator.Simulator;

public class ChronoDriver {
	public static void main (String [] args) throws IOException{
		Simulator consoleSim = new Simulator();
		Simulator fileSim=new Simulator(new File("PLACEHOLDER"));
		Timer listenTimer=new Timer();
		listenTimer.scheduleAtFixedRate(new ListenTask(fileSim),(long) 0, (long) 10);
		
		
		
	};
	//TODO
	public void parseCommand(String command){
		
	}
	static class ListenTask extends TimerTask {
        Simulator sim;
		public ListenTask(Simulator sim){
        	this.sim=sim;
        }
		@Override
		public void run() {
			listen(sim);
		}
		public void listen(Simulator sim){
			if(sim.listen()){
				String event=sim.getEvent();
				switch(event){
				case "Tog 1":
				}//etc, translate event strings to chrono timer commands
			}
		}
    }
	
}
