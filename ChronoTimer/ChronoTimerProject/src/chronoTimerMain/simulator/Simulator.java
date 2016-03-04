package chronoTimerMain.simulator;

import java.io.File;
import java.util.ArrayList;
import java.io.*;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Simulator {
	private boolean fileinput=true;
	private BufferedReader br;
	private ConcurrentLinkedQueue<String> events=new ConcurrentLinkedQueue<String>();
	private ConcurrentLinkedQueue<String> eventsInFileQueue;//this is to store commands from file
	private ConcurrentLinkedQueue<Long> eventTimes;//used for file input
	private ConcurrentLinkedQueue<String> timesInFileQueue=new ConcurrentLinkedQueue<String>();//this is to store timestamps from file
	private Timer t;//used to create events at correct times for file input
	private long lastTime;private long currentTime;
	private final int FFmultiplier=36000;//TODO faster debugging

	public Simulator(){
		fileinput=false;
	}

	public Simulator(File file) throws IOException{
		eventTimes=new ConcurrentLinkedQueue<Long>();
		eventsInFileQueue=new ConcurrentLinkedQueue<String>();
		readFile(file);
		
	}

	private void readFile(File file) throws NumberFormatException, IOException{
		FileInputStream fis = new FileInputStream(file);
		//Construct BufferedReader from InputStreamReader
		br = new BufferedReader(new InputStreamReader(fis));
		String[] temp=null;
		String[] temp2=new String[2];
		String line = null;
		
		while ((line = br.readLine()) != null) {
			//parse time and add to queue
			temp=line.split("\t");
			line=temp[0];
			timesInFileQueue.add(line);
			temp2[0]=line.substring(0,8);
			temp2[1]=line.substring(9);
			long t=Time.valueOf(temp2[0]).getTime();
			t+=Long.parseLong(temp2[1]);
			eventTimes.add(t);
			//add command to queue to be parsed by driver
			eventsInFileQueue.add(temp[1]);
		}

		br.close();
	}

	private void captureInputLoop() throws IOException {
		String line="";
		br = new BufferedReader(new InputStreamReader(System.in));
		while(!line.equals("EXIT")){
			System.out.print("Enter Command<EXIT to quit>: ");
			line=br.readLine();
			events.add(line);
		}
	}

	
	//these are for driver class to interact with simulator
	//start the simulation
	public void start() throws IOException{
		if(fileinput){
			t=new Timer();
			lastTime=eventTimes.peek();
			while(!eventTimes.isEmpty()){
				t.schedule(new RunTask(),(eventTimes.remove()-lastTime)/FFmultiplier);
			}
		}else{
			captureInputLoop();
		}
	}

	class RunTask extends TimerTask {
		@Override
		public void run() {
			events.add(eventsInFileQueue.remove());
		}
	}
	public boolean listen(){//is there an event?
		return(!events.isEmpty());
	}
	public String getEventTimestamp(){
		return(timesInFileQueue.remove());
	}
	public String getEvent(){
		if(listen()){
			return(events.remove());
		}else{
			return("No command waiting");
		}
	}

}

