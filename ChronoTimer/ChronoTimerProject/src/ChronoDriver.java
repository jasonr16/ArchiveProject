import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

import chronoTimerMain.simulator.Simulator;

public class ChronoDriver {
	public static void main (String [] args) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String input="";
		boolean actionSuccess=false;
		Simulator Sim;
		while(!actionSuccess){
			System.out.print("File or Console input<C or F> :");
			input=br.readLine().trim().toUpperCase();
			if(input.equals("C")||input.equals("F")) actionSuccess=true;
		}
		switch(input){
		case "C":Sim = new Simulator();
				 break;
		case "F":
			while(!actionSuccess){
				try{
					System.out.print("Input File path:");
					input=br.readLine();
					Sim=new Simulator(new File(input));
					}catch(IOException e){
						System.out.println("Incorrect file path");
					}
			}
			default:Sim=new Simulator();
		}
		Timer listenTimer=new Timer();
		listenTimer.scheduleAtFixedRate(new ListenTask(Sim),(long) 0, (long) 10);
		Sim.start();
		
		
	};
	
	static class ListenTask extends TimerTask {
        Simulator sim;
		public ListenTask(Simulator sim){
        	this.sim=sim;
        }
		@Override
		public void run() {
			Command currentCommand;
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
