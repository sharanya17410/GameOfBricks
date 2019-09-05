//import Breakout.UserAction;
//import Breakout.UserAction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{
	private enum UserAction {
		NONE, LEFT, RIGHT;
	}
	
	
	boolean ballUp=true;
	boolean ballLeft=false;
	
	private static final int APP_W = 800;
	private static final int APP_H = 600;
	
	private static final int BAT_W = 180;
	private static final int BAT_H = 20;

	private static final int BRICK_W = 50;
	private static final int BRICK_H = 10;
/*Creating ball with radius*/
	private static final int BALL_RADIUS = 10;
	private UserAction action = UserAction.NONE;
	private Timeline timeline = new Timeline();
	boolean running=true;
	
	Coordinates coordinates = new Coordinates(APP_W/2,APP_H/2);
	//coordinates.setLocationX(1.22);
	//coordinates.setLocationY(0);
	Ball ball= new Ball(coordinates, 5,BALL_RADIUS,Color.ALICEBLUE);
	Coordinates batCoordinates=new Coordinates(APP_W/2,APP_H-250);
	Paddle bat=new Paddle(5,10,40,Color.BLUEVIOLET,batCoordinates);
	Coordinates brickCoordinates=new Coordinates(APP_W-300,APP_H-300);
	Brick brick=new Brick(brickCoordinates,5,10,80,Color.DARKGREEN);
	
	
	public void startGame() {
		ballUp=true;
		//ball.coordinates.setLocationX(APP_W/2);
	//	ball.coordinates.setLocationY(APP_H/2);
		timeline.play();
	}
	public Parent createContent() {
		Pane root = new Pane();
		
		//Rectangle brick = new Rectangle(BRICK_W, BRICK_H);
		
		root.setPrefSize(APP_W, APP_H);
	
//		if (ball.coordinates.getLocationX() - ball.radius == 0)
//			ballLeft = false;
//		else if (ball.coordinates.getLocationX() + ball.radius == APP_W)
//			ballLeft = true;
//
//		if (ball.coordinates.getLocationX() - ball.radius == 0)
//			ballUp = false;
		root.getChildren().addAll(ball.circle, bat.paddle, brick.rectangle);
		KeyFrame frame = new KeyFrame(Duration.seconds(0.016), event -> {
			if (!running)
				return;

			switch (action) {
			case LEFT:
				if (bat.coordinates.getLocationX() - 5 >= 0) {
					bat.updateX("subtract");
					break;
				}
			case RIGHT:
				if (bat.coordinates.getLocationX() + BAT_W + 5 <= APP_W) {
					bat.updateX("add");
					break;
				}
			case NONE:
				break;
			}
			if(ballLeft) {
				ball.updateX("subtract");
			} else {
				ball.updateX("add");
			}
			
			if(ballUp) {
				ball.updateY("subtract");
			} else {
				ball.updateY("add");
			}
			

			if (ball.coordinates.getLocationX() - BALL_RADIUS == 0)
				ballLeft = false;
			else if (ball.coordinates.getLocationX() + BALL_RADIUS == APP_W)
				ballLeft = true;

			if (ball.coordinates.getLocationY() - BALL_RADIUS == 0)
				ballUp = false;
			
/*Ball*/
		/*Hits the brick*/
			if (ball.coordinates.getLocationY() + BALL_RADIUS == APP_H - 100
					&& ball.coordinates.getLocationX() + BALL_RADIUS <= brick.coordinates.getLocationX() + BRICK_W
					&& ball.coordinates.getLocationX() - BALL_RADIUS >= brick.coordinates.getLocationX()) {
			
				continueGame();
			
			}

			if (ball.coordinates.getLocationY() - BALL_RADIUS == APP_H - 90
					&& ball.coordinates.getLocationX() + BALL_RADIUS <= brick.coordinates.getLocationX() + BRICK_W
					&& ball.coordinates.getLocationX() - BALL_RADIUS >= brick.coordinates.getLocationX()) {
			
				continueGame();
			
			}
		/*Hits the brick*/
		/*Doesnt Hit the brick*/
			if (ball.coordinates.getLocationY() + BALL_RADIUS == APP_H - 250
					&& ball.coordinates.getLocationX() + BALL_RADIUS <= bat.coordinates.getLocationX() + BAT_W
					&& ball.coordinates.getLocationX() - BALL_RADIUS >= bat.coordinates.getLocationX()) {
				ballUp = true;
			}

			if (ball.coordinates.getLocationY() + BALL_RADIUS == APP_H)
		
				ballUp = true;

			if (ball.coordinates.getLocationY() - BALL_RADIUS == APP_H - 240
					&& ball.coordinates.getLocationX() + BALL_RADIUS <= bat.coordinates.getLocationX() + BAT_W
					&& ball.coordinates.getLocationX() - BALL_RADIUS >= bat.coordinates.getLocationX())
				// restartGame();
				ballUp = false;
		/*Doesnt Hit the brick*/
		});
		timeline.getKeyFrames().add(frame);
		//timeline2.getKeyFrames().add(frame2);

		timeline.setCycleCount(Timeline.INDEFINITE);
		//timeline2.setCycleCount(Timeline.INDEFINITE);
		return root;
		
/*Ball*/
	}
	private void continueGame() {
		// TODO Auto-generated method stub
		
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		VBox vbox = new VBox(new Text("Restart game?"));
	    vbox.setAlignment(Pos.CENTER);
	    Button yesButton = new Button();
	    vbox.getChildren().addAll(yesButton);
		vbox.setAlignment(Pos.CENTER);
		//vbox.setPadding(new Insets(50));
		dialogStage.setScene(new Scene(vbox));
		dialogStage.show();
		yesButton.setText("Yes");
		yesButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				startGame();
				dialogStage.close();
			}
		});
		running = false;
		//timeline.stop();
	//	timeline2.stop(); 
	}
    public static void main(String[] args) {
       // HelloFX.main(args);
       // MultipleScenes.main(args);
    //	AlertBox.main(args);
    	launch(args);
    	
    }
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createContent());
		scene.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case A:
				action = UserAction.LEFT;
				break;
			case D:
				action = UserAction.RIGHT;
				break;
			default:
				break;
			}
		});

		primaryStage.setTitle("GAME OF BRICKS");
		primaryStage.setScene(scene);
		primaryStage.show();
		startGame();
	}
}