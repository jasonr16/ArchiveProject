package chronoTimerMain.software;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a single race of the individual event type.
 * Is the default race type when the NEWRUN command is entered.
 * Contains a queue (FIFO) of racers that will be running individually.
 * @author yangxie
 *
 */

public class RaceIND extends Race {
	
	/*TODO (from Jason) I put these Queues in Race because my Timer duration methods need access to them.
	 * I made them LinkedLists because I need to access indexes, but Java LinkedLists implement Queue methods too.
		 **/
	
	public RaceIND(Timer timer) {
		super(timer);
	}

	/**
	 * Swap the next two racers that will finish
	 * @return true if swap was successful, else false
	 */
	public boolean swap() {
		boolean result = false;
		ArrayList<Racer> runningList = super.getRunningList();
		if (runningList.size() >= 2) {
			Racer temp = runningList.remove(0);
			runningList.add(1, temp);
			result = true;
		}
		return result;
	}
	
	@Override
	public void addRacer(Racer racer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeRacer(Racer racer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void markRacerDNF() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trig(int channel) {
		// TODO Auto-generated method stub
		
	}
	
}
