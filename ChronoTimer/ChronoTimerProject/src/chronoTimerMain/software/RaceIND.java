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
	
	/**
	 * Add a new racer to the end of the start queue
	 * @param racer to be added
	 * @return true if add was successful, else false
	 */
	@Override
	public boolean addRacerToStart(Racer racer) {
		boolean result = false;
		ArrayList<Racer> startList = super.getStartList();
		if (!startList.contains(racer)) {
			startList.add(racer);
			result = true;
		}
		return result;
	}
	
	/**
	 * Remove a racer from the start queue
	 * @param racer to be removed
	 * @return true if remove was successful, else false
	 */
	@Override
	public boolean removeRacerFromStart(Racer racer) {
		boolean result = false;
		ArrayList<Racer> startList = super.getStartList();
		if (startList.contains(racer)) {
			startList.remove(racer);
			result = true;
		}
		return result;
	}

	/**
	 * Remove the racer at the head of the running queue and add him to the finish queue.
	 * Mark the racer's dnf field as true.
	 * @return true if a racer was handled, else false
	 */
	@Override
	public boolean handleRacerDNF() {
		boolean result = false;
		ArrayList<Racer> runningList = super.getRunningList();
		ArrayList<Racer> finishList = super.getFinishList();
		Racer racer = null;
		if (runningList.size() >= 1) {
			racer = runningList.remove(0);
			racer.setDNF(true);
			finishList.add(racer);
		}
		return result;
	}

	/**
	 * Remove the racer at the end of the running queue (latest racer to start) and add him
	 * back to the head of the start queue.
	 * Reset the racer's start time field.
	 * @return true if a racer was handled, else false
	 */
	@Override
	public boolean handleRacerCancel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean swapRunningRacers() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveRacerToRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveRacerToFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void trig(int channel) {
		// TODO Auto-generated method stub
		
	}
	
}
