package chronoTimerMain.simulator.hardwareHandler;

/**
 * SingleEvent class is used to store all events entered into the system.
 * It will eventually be implemented to export to XML.
 * @author Jason
 *
 */

public class SingleEvent {
	private String timeStamp;
	private String command;
	private String[] args;
	protected  SingleEvent(String timeStamp, String command, String[] args) {
		this.setTimeStamp(timeStamp);
		this.setCommand(command);
		this.setArgs(args);
	}
	protected String getTimeStamp() {
		return timeStamp;
	}
	protected void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	protected String getCommand() {
		return command;
	}
	protected  void setCommand(String command) {
		this.command = command;
	}
	protected  String[] getArgs() {
		return args;
	}
	protected void setArgs(String[] args) {
		this.args = args;
	}
}
