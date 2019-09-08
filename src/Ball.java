import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Ball implements Sprite{
	
	Coordinates coordinates;
	double radius;
	private Color color;
	public Shape shape;
	public Stack<Double> ballPositionX = new Stack<Double>();
	private Stack<Double> ballPositionY = new Stack<Double>();
	public Queue<Double> qballPositionX = new LinkedList<>();
	private Queue<Double> qballPositionY = new LinkedList<>();
	
	Ball(Coordinates coordinates,double radius,Color color,Shape shape){
		this.coordinates= coordinates;
		
		this.radius=radius;
		this.color=color;
		//either initialize circle here or initialize circle in draw -repainting the ball with every call to draw
		this.shape=shape;
		this.shape.setFill(color);
		shape.setTranslateX(coordinates.getLocationX());
		shape.setTranslateY(coordinates.getLocationY());
		((Circle) shape).setRadius(radius);
	}
	
	
	@Override
	public void updateX(String command) {
		if(command.equals("add")) {
			coordinates.setLocationX(shape.getTranslateX()+5);			
		}
		else {
			coordinates.setLocationX(shape.getTranslateX()-5);
			
		}
		drawX(coordinates.getLocationX());
		
	}
	
//	public boolean leftWallCollide() {
//		if (coordinates.getLocationX() - Parameters.BALL_RADIUS == 0)
//			return false;
//		else if (coordinates.getLocationX() + Parameters.BALL_RADIUS == Parameters.APP_W)
//			return true;
//		
//		return false;
//	}
	
	public void updateY(String command) {
		if(command.equals("add")) {
			coordinates.setLocationY(shape.getTranslateY()+5);
			
			
		}
		else {
			coordinates.setLocationY(shape.getTranslateY()-5);
			
		}
		drawY(coordinates.getLocationY());
		
	}

	@Override
	public void drawX(double locX) {
		// TODO Auto-generated method stub
		
		shape.setTranslateX(locX);
		//paddle.setTranslateY(locY);
	}
	
	public void drawY(double locY) {
		// TODO Auto-generated method stub
		
		shape.setTranslateY(locY);
		//paddle.setTranslateY(locY);
	}


	@Override
	public void undo() {
		// TODO Auto-generated method stub
		shape.setTranslateX(ballPositionX.pop());
		shape.setTranslateY(ballPositionY.pop());
	}


	@Override
	public void save(double x,double y) {
		// TODO Auto-generated method stub
		ballPositionX.push(x);
		ballPositionY.push(y);
		
		qballPositionX.add(x);
		qballPositionY.add(y);
	}


	@Override
	public void unsave() {
		// TODO Auto-generated method stub
		shape.setTranslateX(ballPositionX.pop());
		shape.setTranslateY(ballPositionY.pop());
	}
	
	public void redo() {
		shape.setTranslateX(qballPositionX.poll());
		shape.setTranslateY(qballPositionY.poll());
	}

}
