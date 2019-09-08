//package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import java.util.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class Test extends Application {

	private enum UserAction {
		NONE, LEFT, RIGHT;
	}
	
	private Stack<Double> ballPositionX = new Stack<Double>();
	private Stack<Double> ballPositionY = new Stack<Double>();
	private Stack<Double> batPositionX = new Stack<Double>();
	private Stack<Double> batPositionY = new Stack<Double>();
	
	private Queue<Double> qballPositionX = new LinkedList<>();
	private Queue<Double> qballPositionY = new LinkedList<>();
	private Queue<Double> qbatPositionX = new LinkedList<>();
	private Queue<Double> qbatPositionY = new LinkedList<>();

	private static final int APP_W = 800;
	private static final int APP_H = 600;

	private static final int BALL_RADIUS = 10;
	private static final int BAT_W = 180;
	private static final int BAT_H = 20;

	private static final int BRICK_W = 50;
	private static final int BRICK_H = 10;

	private Circle ball = new Circle(BALL_RADIUS);
	private Rectangle bat = new Rectangle(BAT_W, BAT_H);
	private Rectangle brick = new Rectangle(BRICK_W, BRICK_H);
	

	private boolean ballUp = true, ballLeft = false;
	private UserAction action = UserAction.NONE;
	private Timeline timeline = new Timeline();
	private Timeline timeline2 = new Timeline();
	private Timeline timeline3 = new Timeline();//replay

	public static boolean running = true;

	Label timerLabel = new Label();
	Button pb = new Button("Pause");
	Button rb = new Button("Resume");
	Button ub = new Button("Undo");
	Button replayb = new Button("Replay");
	Button strt=new Button("Start");
	Button restart=new Button("Restart");
	
         
         public void init() {
        	ballUp = true;
     		ball.setTranslateX(APP_W / 2);
     		ball.setTranslateY(APP_H / 2);
     		
        	bat.setTranslateX(APP_W / 2);
     		bat.setTranslateY(APP_H - 250);
     		bat.setFill(Color.BLUE);

     		brick.setTranslateX(APP_W - 300);
     		brick.setTranslateY(APP_H - 100);
     		brick.setFill(Color.DARKRED);
         }
	private Parent createContent() {
		Pane root = new Pane();

		
		root.setPrefSize(APP_W, APP_H);

		
		init();
		
		setButtonActions();

		DateTimeFormatter SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

		timerLabel.setText(LocalTime.now().format(SHORT_TIME_FORMATTER));
		timerLabel.setTranslateX(APP_W - 70);
		timerLabel.setTranslateY(APP_H - 600);
		

		
		HBox hb = new HBox();
		hb.getChildren().addAll(pb,rb,ub,replayb,strt,restart);
		root.getChildren().addAll(ball, bat, brick, timerLabel,hb);

		KeyFrame frame2 = new KeyFrame(Duration.seconds(1), e -> {

			timerLabel.setText(LocalTime.now().format(SHORT_TIME_FORMATTER));
			

		});
//replay
		KeyFrame frame3 = new KeyFrame(Duration.seconds(0.016), event -> {
		replay();});
		
		KeyFrame frame = new KeyFrame(Duration.seconds(0.016), event -> {
			if (!running)
				return;
		//	
		//ballPositionX.push(ball.getTranslateX());
		//ballPositionY.push(ball.getTranslateY());
		//batPositionX.push(bat.getTranslateX());
		//batPositionY.push(bat.getTranslateY());
		//
		//qballPositionX.add(ball.getTranslateX());
		//qballPositionY.add(ball.getTranslateY());
		//qbatPositionX.add(bat.getTranslateX());
		//qbatPositionY.add(bat.getTranslateY());
			
			store();
			

			paddleMovement();

			ballMovement();	

			setBallDirection();

			ballCollide();

		});
		
//		ArrayList<KeyFrame> f=new ArrayList();
//		f.add(frame);
//		
		
		timeline.getKeyFrames().add(frame);
		timeline2.getKeyFrames().add(frame2);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline2.setCycleCount(Timeline.INDEFINITE);
		
		
		timeline3.getKeyFrames().add(frame3);
		timeline3.setCycleCount(Timeline.INDEFINITE);
	
		return root;
	}
	
	public void setButtonActions() {
		pb.setOnAction(e -> pause());
		rb.setOnAction(e->resume());
		ub.setOnMousePressed(e->undo());
		replayb.setOnAction(e->replay());
		strt.setOnAction(e->startGame());
	}
private void ballMovement() {
	ball.setTranslateX(ball.getTranslateX() + (ballLeft ? -5 : 5));
	ball.setTranslateY(ball.getTranslateY() + (ballUp ? -5 : 5));
	


}


public void gameStart() {


}
private void ballCollide() {
	if (ball.getTranslateY() + BALL_RADIUS == APP_H - 100
			&& ball.getTranslateX() + BALL_RADIUS <= brick.getTranslateX() + BRICK_W
			&& ball.getTranslateX() - BALL_RADIUS >= brick.getTranslateX()) {
		
		continueGame();
	
	}

	if (ball.getTranslateY() - BALL_RADIUS == APP_H - 90
			&& ball.getTranslateX() + BALL_RADIUS <= brick.getTranslateX() + BRICK_W
			&& ball.getTranslateX() - BALL_RADIUS >= brick.getTranslateX()) {
	
		continueGame();
		
	}
}

private void setBallDirection() {
	if (ball.getTranslateX() - BALL_RADIUS == 0)
		ballLeft = false;
	else if (ball.getTranslateX() + BALL_RADIUS == APP_W)
		ballLeft = true;

	if (ball.getTranslateY() - BALL_RADIUS == 0)
		ballUp = false;
	

	if (ball.getTranslateY() + BALL_RADIUS == APP_H - 250
			&& ball.getTranslateX() + BALL_RADIUS <= bat.getTranslateX() + BAT_W
			&& ball.getTranslateX() - BALL_RADIUS >= bat.getTranslateX()) {
		ballUp = true;
	}

	if (ball.getTranslateY() + BALL_RADIUS == APP_H)
		// restartGame();
		ballUp = true;

	if (ball.getTranslateY() - BALL_RADIUS == APP_H - 240
			&& ball.getTranslateX() + BALL_RADIUS <= bat.getTranslateX() + BAT_W
			&& ball.getTranslateX() - BALL_RADIUS >= bat.getTranslateX())
		// restartGame();
		ballUp = false;
	
}
	private void restartGame() {
		stopGame();
		startGame();
	}
	private void paddleMovement() {
		switch (action) {
		case LEFT:
			if (bat.getTranslateX() - 5 >= 0) {
				bat.setTranslateX(bat.getTranslateX() - 5);
				break;
			}
		case RIGHT:
			if (bat.getTranslateX() + BAT_W + 5 <= APP_W) {
				bat.setTranslateX(bat.getTranslateX() + 5);
				break;
			}
		case NONE:
			break;
		}
	}
	private void pause() {
		//running=false;
		GameControl gc=new GameControl();
		Pause pause=new Pause();
		PauseGame pGame=new PauseGame(pause);
		gc.setCommand(pGame);
		running=gc.executeCommand(running);
	}
	private void resume() {
		//running=true;
		GameControl gc=new GameControl();
		Resume resume=new Resume();
		ResumeGame rGame=new ResumeGame(resume);
		gc.setCommand(rGame);
		running=gc.executeCommand(running);
	}
	
	private void replay() {
		if(qballPositionX.isEmpty())
			return;
		
			ball.setTranslateX(qballPositionX.poll());
			ball.setTranslateY(qballPositionY.poll());
			bat.setTranslateX(qbatPositionX.poll());
			bat.setTranslateY(qbatPositionY.poll());
		timeline3.play();
	}
	
	private void store() {
		
		ballPositionX.push(ball.getTranslateX());
		ballPositionY.push(ball.getTranslateY());
		batPositionX.push(bat.getTranslateX());
		batPositionY.push(bat.getTranslateY());
		
		qballPositionX.add(ball.getTranslateX());
		qballPositionY.add(ball.getTranslateY());
		qbatPositionX.add(bat.getTranslateX());
		qbatPositionY.add(bat.getTranslateY());
	}
	private void undo() {
		running = false;
		
		ball.setTranslateX(ballPositionX.pop());
		ball.setTranslateY(ballPositionY.pop());
		bat.setTranslateX(batPositionX.pop());
		bat.setTranslateY(batPositionY.pop());
	}
	private void stopGame() {
		// TODO Auto-generated method stub
		
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		VBox vbox = new VBox(new Text("GAME OVER"));
		vbox.setAlignment(Pos.CENTER);
		//vbox.setPadding(new Insets(50));
		dialogStage.setScene(new Scene(vbox));
		dialogStage.show();
		
		running = false;
		timeline.stop();
		timeline2.stop();
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
		timeline.stop();
		timeline2.stop(); 
	}
	
	private void startGame() {
		// TODO Auto-generated method stub
//		ballUp = true;
//		ball.setTranslateX(APP_W / 2);
//		ball.setTranslateY(APP_H / 2);
		
		
		//running = true;
		timeline.play();
		timeline2.play();
		GameControl gc=new GameControl();
		Start startObj=new Start();
		StartGame sGame=new StartGame(startObj);
		gc.setCommand(sGame);
		running=gc.executeCommand(running);
		
	}
	private void defineKeyPress(KeyEvent event) {
		
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
			
		
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createContent());
		scene.setOnKeyPressed(event ->defineKeyPress(event));

		primaryStage.setTitle("GAME OF BRICKS");
		primaryStage.setScene(scene);
		primaryStage.show();
		startGame();
	}

	public static void main(String[] args) {
		//Test t=new Test();
	
		launch(args);
	}
}