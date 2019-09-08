
public interface Command {
	public boolean execute(boolean state);
	public void unexecute();
}
