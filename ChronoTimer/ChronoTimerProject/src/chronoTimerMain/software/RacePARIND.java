package chronoTimerMain.software;

public class RacePARIND extends Race{
	private int startChannel; 
	private int finishChannel;
	
	
	/**
	 * Creates an individual race
	 * @param run number for the race
	 * @param Timer object used for the race
	 */
	public RacePARIND(int runNumber, Timer timer) {
		super(runNumber, timer);
		this.startChannel = 0;
		this.finishChannel = 0;
	}

	@Override
	public boolean removeRacerFromStart(int racerNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handleRacerDNF() {
		// TODO Auto-generated method stub
		return false;
	}

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
	public boolean start(String timeStamp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean finish(String timeStamp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean trig(int channelNum, String timeStamp) {
		// TODO Auto-generated method stub
		return false;
	}
	//not used in sprint 1

	@Override
	public boolean addRacerToStart(int racerNum) {
		// TODO Auto-generated method stub
		return false;
	}
}
