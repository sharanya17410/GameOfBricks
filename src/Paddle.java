import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Paddle implements Sprite{
	
	Coordinates coordinates;
	private double length;
	private double breadth;
	private Color color;
	public Shape shape;
	private Stack<Double> batPositionX = new Stack<Double>();
	private Stack<Double> batPositionY = new Stack<Double>();
	private Queue<Double> qbatPositionX = new LinkedList<>();
	private Queue<Double> qbatPositionY = new LinkedList<>();
	
	Paddle(double length,double breadth,Color color,Coordinates coordinates,Shape shape){
		this.coordinates=coordinates;
		this.length=length;
		this.breadth=breadth;
		this.color=color;
		this.shape=new Rectangle(length,breadth);
		System.out.println(coordinates.getLocationX()+" "+coordinates.getLocationX());
		this.shape.setTranslateX(coordinates.getLocationX());
		this.shape.setTranslateY(coordinates.getLocationY());
		this.shape.setFill(color);
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
		// TODO Auto-generated method stub
		
//		this.locationX+=5;
//		this.locationY+=5;
//		this.draw(locationX, locationY);
	}

	@Override
	public void drawX(double locX) {
		// TODO Auto-generated method stub
		
		shape.setTranslateX(locX);
		//paddle.setTranslateY(locY);
	}

	@Override
	public void updateY(String command) {
		// TODO Auto-generated method stub
		coordinates.setLocationX(0);
		drawY(coordinates.getLocationY());
	}

	@Override
	public void drawY(double locY) {
		// TODO Auto-generated method stub
		shape.setTranslateY(locY);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		shape.setTranslateX(batPositionX.pop());
		shape.setTranslateY(batPositionY.pop());
	}

	@Override
	public void save(double x, double y) {
		// TODO Auto-generated method stub
		batPositionX.push(x);
		batPositionY.push(y);
		
		qbatPositionX.add(x);
		qbatPositionY.add(y);
	}
	
	@Override
	public void unsave() {
		// TODO Auto-generated method stub
		System.out.println(batPositionX.peek());
		shape.setTranslateX(batPositionX.pop());
		shape.setTranslateY(batPositionY.pop());
	}
	
	public void redo() {
		shape.setTranslateX(qbatPositionX.poll());
		shape.setTranslateY(qbatPositionY.poll());
	}
}