package chronoTimerMain.software.Times;

public abstract class Times {
	private int hour;
	private int minute;
	private int second;
	private int nanoseconds;
	
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public int getNanoseconds() {
		return nanoseconds;
	}
	public void setNanoseconds(int nanoseconds) {
		this.nanoseconds = nanoseconds;
	}
	
	
}
