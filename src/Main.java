import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
//import Breakout.UserAction;
//import Breakout.UserAction;

public class Main extends Application{
	
	private enum UserAction {
		NONE, LEFT, RIGHT;
	}

	public int APP_W = 800;
	public  int APP_H = 600;
	
	Ball ball;
	Paddle bat;
	Brick brick;
	
	Label timerLabel = new Label();
	Button pb = new Button("Pause");
	Button rb = new Button("Resume");
	Button ub = new Button("Undo");
	Button replayb = new Button("Replay");
	Button strt=new Button("Start");
	Button restart=new Button("Restart");
	
//	
	
	private static final int BAT_W = 180;
	private static final int BAT_H = 20;
	
	
	
	
	private static final int BRICK_W = 50;
	private static final int BRICK_H = 10;
	
	
	
/*Creating ball with radius*/
	private static final int BALL_RADIUS = 10;


	private boolean ballUp = true, ballLeft = false;
	private Timeline timeline = new Timeline();

	/*Creating a timeline for clock*/
	
	private Timeline timeline2 = new Timeline();
	
	public Timeline timeline3 = new Timeline();

	private boolean running = false;
	
	private UserAction action = UserAction.NONE;
	KeyFrame frame;

	public void init() {
		Circle circle = new Circle();
		Rectangle rect_paddle = new Rectangle(BAT_W, BAT_H);
		Coordinates brickCoordinates = new Coordinates(APP_W - 300, APP_H - 100);
		brick = new Brick(brickCoordinates, BRICK_W, BRICK_H, Color.DARKRED);

		Coordinates batCoordinates = new Coordinates(APP_W / 2, APP_H - 250);
		bat = new Paddle(BAT_W, BAT_H, Color.BLACK, batCoordinates,rect_paddle);
		Coordinates ballCoordinates = new Coordinates(APP_W / 2, APP_H / 2);
		ball = new Ball(ballCoordinates,BALL_RADIUS,Color.BLACK,circle);
	}
	
	public void paddleMovement() {
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
	}
	public void ballDirection() {
		

		if (ball.coordinates.getLocationX() - BALL_RADIUS == 0)
		ballLeft = false;
		else if (ball.coordinates.getLocationX() + BALL_RADIUS == APP_W)
			ballLeft = true;

		if (ball.coordinates.getLocationY() - BALL_RADIUS == 0)
			ballUp = false;
	
		

		if (ball.coordinates.getLocationY() + BALL_RADIUS == APP_H)
	
			ballUp = true;


		
	}
	
	public void paddleCollision() {
		if (ball.coordinates.getLocationY() + BALL_RADIUS == APP_H - 250
				&& ball.coordinates.getLocationX() + BALL_RADIUS <= bat.coordinates.getLocationX() + BAT_W
				&& ball.coordinates.getLocationX() - BALL_RADIUS >= bat.coordinates.getLocationX()) {
			ballUp = true;
		}
		if (ball.coordinates.getLocationY() - BALL_RADIUS == APP_H - 240
				&& ball.coordinates.getLocationX() + BALL_RADIUS <= bat.coordinates.getLocationX() + BAT_W
				&& ball.coordinates.getLocationX() - BALL_RADIUS >= bat.coordinates.getLocationX())
			// restartGame();
			ballUp = false;
	}
	public void ballMovement() {
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
	}
	
	public void brickCollision() {
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
	}
	private Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize(APP_W, APP_H);
		
		
		HBox hb = new HBox();
		hb.getChildren().addAll(pb,rb,ub,replayb,strt,restart);
		
		root.getChildren().addAll(brick.rectangle, ball.shape,bat.shape,hb);
		
		
		setButtonActions();
		
		
		/*Ball & Game*/

		//timeline2.setCycleCount(Timeline.INDEFINITE);
		return root;
	}
	
	public void setButtonActions() {
		pb.setOnAction(e -> pause());
		rb.setOnAction(e->resume());
		ub.setOnMousePressed(e->undo());
		replayb.setOnAction(e->replay());
		strt.setOnAction(e->startGame1());
		restart.setOnAction(e->restart());
	}
	
	public void replay() {
		KeyFrame frame3 = new KeyFrame(Duration.seconds(0.016), event -> {
			if(ball.qballPositionX.isEmpty())
				return;
			
				ball.redo();
				
				bat.redo();
			});
		timeline3.getKeyFrames().add(frame3);
		timeline3.setCycleCount(Timeline.INDEFINITE);
		timeline3.play();
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
	
	public void store() {
		ball.save(ball.coordinates.getLocationX(),ball.coordinates.getLocationY());
		bat.save(bat.coordinates.getLocationX(),bat.coordinates.getLocationY());

//		qbatPositionY.add(bat.getTranslateY());
	}
	
	public void startGame1() {
		
		GameControl gc=new GameControl();
		Start s=new Start();
		StartGame sGame=new StartGame(s);
		gc.setCommand(sGame);
		running=gc.executeCommand(running);
		
		frame = new KeyFrame(Duration.seconds(0.016), event -> {
			if (!running)
				return;
			
			store();
			
			ballMovement();
			
			paddleMovement();
			
/*Ball*/	ballDirection();
		/*Hits the brick*/
			brickCollision();
			paddleCollision();
			
		/*Hits the brick*/
		/*Doesnt Hit the brick*/

		/*Doesnt Hit the brick*/
		});
		
		timeline.getKeyFrames().add(frame);
		//timeline2.getKeyFrames().add(frame2);

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	private void undo() {
		//running = false;
		GameControl gc=new GameControl();
		Undo undo=new Undo();
		UndoGame uGame=new UndoGame(undo);
		gc.setCommand(uGame);
		running=gc.executeCommand(running);
		ball.unsave();
		bat.unsave();

	}
	
	private void continueGame() {
		// TODO Auto-generated method stub
		
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		VBox vbox = new VBox(new Text("Game Over !! Do you want to Restart the game?"));
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
				startGame1();
				dialogStage.close();
			}
		});
		running = false;
		timeline.stop();
	//	timeline2.stop(); 
	}
	public void restart() {
		init();
		running=true;
		timeline.play();
		startGame1();
	}
//	private void startGame() {
//		// TODO Auto-generated method stub
//		ballUp = true;
////		ball.setTranslateX(APP_W / 2);
////		ball.setTranslateY(APP_H / 2);
//
//		
//		
//		timeline.play();
//		timeline2.play();
//		running = true;
//	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		init();
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
		//startGame();
	}

	public static void main(String[] args) {
		launch(args);
	}


	
}