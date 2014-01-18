package controller.com;


import view.com.GameScreen;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{
	
	static final double WIDTH = 1000;
	static final double HEIGHT = 600;
	public static Stage stage;
	
	

	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		/*URL url = new URL("com/xyz/resources/camera.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		getFrame().setIconImage(img);
	*/
		
		// TODO Auto-generated method stub
		Main.stage = stage;
		stage.setTitle("Kings & Things");
		stage.setMinHeight(HEIGHT);
		stage.setMinWidth(WIDTH);
		
		//stage.setIconified(true);
		//stage.getIcons().
		stage.getIcons().add(new Image("view/com/assets/pics/icon.png"));
		//stage.setMaxHeight(HEIGHT);
		//stage.setMaxWidth(WIDTH);
		//stage.setFullScreen(true);
		//new StartScreen().show();

//		view.show(); //temporary
		
	    GameScreen view = new GameScreen();
	    // Model is taken from GameService
	    GameScreenCntrl ctrl = new GameScreenCntrl(view);
	    view.setController(ctrl);
	    
	    ctrl.initialize();
	}
	
	public static Stage getStage() {
		return stage;
	}

}
