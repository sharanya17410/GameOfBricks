
public class PauseGame implements Command{
	
	Pause pause;
	
	PauseGame(Pause pause){
		this.pause=new Pause();
	}
	@Override
	public boolean execute(boolean state) {
		// TODO Auto-generated method stub
		boolean result=pause.pause(state);
		return result;
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		
	}

}
