package chronoTimerMain.software;

public class Racer {
	private int number;
	private String duration;
	
	public Racer(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String raceDuration) {
		this.duration = raceDuration;
	}
}
