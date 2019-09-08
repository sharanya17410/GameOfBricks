
public class GameControl {
	Command currentCommand;
	
	public void setCommand(Command command) {
		this.currentCommand=command;
	}
	
	public boolean executeCommand(boolean state) {
		return currentCommand.execute(state);
	}
	
	
}
