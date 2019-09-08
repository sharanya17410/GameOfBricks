
public class ResumeGame implements Command{
	
	Resume resume;
	
	ResumeGame(Resume resume){
		this.resume=new Resume();
	}
	@Override
	public boolean execute(boolean state) {
		// TODO Auto-generated method stub
		boolean result=resume.resume(state);
		return result;
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		
	}

}
