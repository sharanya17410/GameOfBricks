
public class StartGame implements Command{
	
	Start start;
	
	StartGame(Start start){
		this.start=new Start();
	}
	@Override
	public boolean execute(boolean state) {
		// TODO Auto-generated method stub
		boolean result=start.start(state);
		return result;
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		
	}

}

