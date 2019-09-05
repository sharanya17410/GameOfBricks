import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Ball implements Sprite{
	
	Coordinates coordinates;
	private double speed;
	 double radius;
	private Color color;
	public Shape circle;
	
	Ball(Coordinates coordinates,double speed,double radius,Color color){
		this.coordinates= coordinates;
		this.speed=speed;
		this.radius=radius;
		this.color=color;
		//either initialize circle here or initialize circle in draw -repainting the ball with every call to draw
		this.circle=new Circle(coordinates.getLocationX(),coordinates.getLocationY(),radius);
		this.circle.setFill(Color.AQUA);
	}
	
	
	@Override
	public void updateX(String command) {
		if(command.equals("add")) {
			coordinates.setLocationX(coordinates.getLocationX()+5);
			
			
		}
		else {
			coordinates.setLocationX(coordinates.getLocationX()-5);
			
		}
		drawX(coordinates.getLocationX());
		
	}
	
	
	public void updateY(String command) {
		if(command.equals("add")) {
			coordinates.setLocationY(coordinates.getLocationY()+5);
			
			
		}
		else {
			coordinates.setLocationY(coordinates.getLocationY()-5);
			
		}
		drawY(coordinates.getLocationY());
		
	}

	@Override
	public void drawX(double locX) {
		// TODO Auto-generated method stub
		
		circle.setTranslateX(locX);
		//paddle.setTranslateY(locY);
	}
	
	public void drawY(double locY) {
		// TODO Auto-generated method stub
		
		circle.setTranslateY(locY);
		//paddle.setTranslateY(locY);
	}

}
