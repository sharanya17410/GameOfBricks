import javafx.scene.canvas.GraphicsContext;

public interface Sprite {
	public void updateX(String command);
	public void drawX(double locX);
	public void updateY(String command);
	public void drawY(double locY);
	public void undo();
	public void save(double x,double y);
	public void unsave();
}
