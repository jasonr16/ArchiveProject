import java.awt.FileDialog;
import java.awt.Frame;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.*;

import chronoTimerMain.simulator.Simulator;
import chronoTimerMain.simulator.hardwareHandler.ChronoHardwareHandler;

public class ChronoDriver {
	public static void main (String [] args) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		JFileChooser choose=new JFileChooser();
		ChronoHardwareHandler hardware=new ChronoHardwareHandler();
		String input="";
		boolean actionSuccess=false;
		Simulator Sim=new Simulator();
		while(!actionSuccess) {
			System.out.print("File or Console input <C or F> : ");
			input=br.readLine().trim().toUpperCase();
			if(!input.equals("C")&&!input.equals("F")) continue;
			switch(input){
			case "C":Sim = new Simulator();
					actionSuccess=true;
					 break;
			case "F":
				String osName = System.getProperty("os.name");
			    String homeDir = System.getProperty("user.home");
			    if (osName.equals("Mac OS X")) {
			    	JFrame frame = new JFrame();
					frame.setAlwaysOnTop(true);
					frame.setVisible(true);
					FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
			        fd.setDirectory(homeDir);
			        fd.setVisible(true);
			        String filepath = fd.getDirectory();
			        String filename = fd.getFile();
			        File selectedPath = new File(filepath+filename); 
			        if (filename == null) {
			            System.out.println("You cancelled the choice");
			            actionSuccess = false;
			        } else {
			            System.out.println("You chose " + filename);
			            actionSuccess=true;
			            Sim = new Simulator(selectedPath);
			        }
			        
			        frame.setVisible(false);
			     
					break;
			    } else {
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
					choose.setFileFilter(filter);
					int returnVal=choose.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						System.out.println("Running Simulator from file: " +
						choose.getSelectedFile().getName()+ " ...");
						Sim=new Simulator(choose.getSelectedFile());
						actionSuccess=true;
						break;
					}
			    }
			}
			
		}
		
		Timer listenTimer=new Timer();
		listenTimer.scheduleAtFixedRate(new ListenTask(Sim,hardware),(long) 0, (long) 10);
		Sim.start();
		
		
	};
	
	static class ListenTask extends TimerTask {
        Simulator sim;
        ChronoHardwareHandler x;
		public ListenTask(Simulator sim, ChronoHardwareHandler hardware){
        	this.sim=sim;
        	x=hardware;
        }
		@Override
		public void run() {
			Command currentCommand;
			if(sim.listen()){
				currentCommand=new Command(sim.getEvent(),sim.getEventTimestamp());
				currentCommand.execute(x);
			}
		}
    }
	static class Command{
		String[] args=new String[2];
		String command;
		String timestamp;
		public Command(String event,String timeStamp){
			String[] temp=event.split(" ");
			timestamp=timeStamp;
			command=temp[0];
			if(temp.length==2){
						args[0]=temp[1];
			}else if(temp.length==3){
				args[0]=temp[1];
				args[1]=temp[2];
			}
		}
		
		public void execute(ChronoHardwareHandler hardware){
			hardware.inputFromSimulator(command, args,timestamp);
		}
	}
}
