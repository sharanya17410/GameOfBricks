
public class UndoGame  implements Command{

	Undo undo;
	
	public UndoGame(Undo undo) {
		this.undo=undo;
		
	}
	
	public boolean execute(boolean state) {
		boolean result=undo.undo(state);
		return result;
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		
	}
}
