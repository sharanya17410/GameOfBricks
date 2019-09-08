import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Brick implements Sprite{
	
	Coordinates coordinates;
	private double speed;
	private double length;
	private double breadth;
	private Color color;
	public Shape rectangle;
	
	Brick(Coordinates coordinates,double length,double breadth,Color color){
		this.coordinates = coordinates;
		this.speed=speed;
		this.length=length;
		this.breadth=breadth;
		this.color=color;
		this.rectangle=new Rectangle(coordinates.getLocationX(),coordinates.getLocationY(),length,breadth);
		this.rectangle.setFill(color);
	}
	
	@Override
	public void updateX(String command) {
		// TODO Auto-generated method stub
//		
//		this.locationX+=5;
//		this.locationY+=5;
//		this.draw(locationX, locationY);
	}

	@Override
	public void drawX(double locX) {
		// TODO Auto-generated method stub
		
		
	}
	@Override
	public void updateY(String command) {
		// TODO Auto-generated method stub
//		
//		this.locationX+=5;
//		this.locationY+=5;
//		this.draw(locationX, locationY);
	}

	@Override
	public void drawY(double locX) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsave() {
		// TODO Auto-generated method stub
		
	}

}