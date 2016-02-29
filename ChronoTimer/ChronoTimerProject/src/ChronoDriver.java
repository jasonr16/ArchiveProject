import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import chronoTimerMain.simulator.Simulator;

public class ChronoDriver {
	public static void main (String [] args) throws IOException{
		Simulator consoleSim = new Simulator();
		Simulator fileSim=new Simulator(new File("C:\\Users\\Owner\\workspace\\sample.txt"));
		Timer listenTimer=new Timer();
		listenTimer.scheduleAtFixedRate(new ListenTask(fileSim),(long) 0, (long) 10);
		fileSim.start();
		
		
	};
	//TODO
	
	static class ListenTask extends TimerTask {
        Simulator sim;
		public ListenTask(Simulator sim){
        	this.sim=sim;
        }
		@Override
		public void run() {
			Command currentCommand;
			//System.out.print("0");
			if(sim.listen()){
				
				
				currentCommand=new Command(sim.getEvent());
				currentCommand.execute();
			}
		}
    }
	static class Command{
		String[] args=new String[2];
		String command;
		public Command(String event){
			String[] temp=event.split(" ");
			command=temp[0];
			if(temp.length==2){
						args[0]=temp[1];
			}else if(temp.length==3){
				args[0]=temp[1];
				args[1]=temp[2];
			}
		}
		//TODO
		public void execute(){
			//temporary
			System.out.println(command);
		}
	}
}
