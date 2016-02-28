package chronoTimerMain.software;

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
	Queue<Racer> startQueue;
	Queue<Racer> runningQueue;
	Queue<Racer> finishQueue;
	
	public RaceIND(Timer time) {
		startQueue = new LinkedList<Racer>();
	}

	public void swap() {
		// TODO Auto-generated method stub	
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
