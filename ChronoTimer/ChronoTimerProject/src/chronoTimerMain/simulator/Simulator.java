package chronoTimerMain.simulator;

import java.io.*;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Simulator {
	private boolean eventWaiting;
	private boolean fileinput;
	private long initialTime;
	private ConcurrentLinkedQueue<String> events=new ConcurrentLinkedQueue<String>();
	private ConcurrentLinkedQueue<Long> eventTimes;
	
	public Simulator(){
		fileinput=false;
	}
	
	public Simulator(File file) throws IOException{
		eventTimes=new ConcurrentLinkedQueue<Long>();
		captureInputLoop(file);
		fileinput=true;
	}
	
	//start the simulation
	public void start(){
		if(fileinput){
			Timer t=new Timer();
			t.schedule(new RunTask(),eventTimes.remove()-initialTime);
		}
	}
	
	public void changeEventwaiting(){
		eventWaiting=!eventWaiting;
	}
	
	public Long getNextTime(){
		return(eventTimes.remove());
	}
	
	public void captureInputLoop(File file) throws NumberFormatException, IOException{
		FileInputStream fis = new FileInputStream(file);
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String[] temp=null;
		String[] temp2=null;
		String line = null;
		while ((line = br.readLine()) != null) {
			//parse time and add to queue
			temp=line.split("/t");
			temp2=temp[0].split(".");
			long t=Time.valueOf(temp[0]).getTime();
			t+=Long.parseLong(temp2[1]);
			eventTimes.add(t);
			//add command to queue to be parsed by driver
			events.add(temp[1]);
			}
		initialTime=eventTimes.peek();
		br.close();
	};
	//TODO also switch with above
	public void readFile(File file) {};
	
	public boolean listen(){//is there an event?
		return(eventWaiting);
	}
	
	public String getEvent(){
		if(listen()){
			return(events.remove());
		}else{
			return("No command waiting");
		}
	}
}
//TODO
class RunTask extends TimerTask {
	
	@Override
	public void run() {
		Simulator.changeEventwaiting();
		
	}
}