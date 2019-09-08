
public class Undo {
	boolean state;
	public Undo() {
		
	}
	
	public boolean undo(boolean state) {
		this.state=false;
		return this.state;
		
	}
}
