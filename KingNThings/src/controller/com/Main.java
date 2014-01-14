package controller.com;


import view.com.StartScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("Kings N Things");
		new StartScreen(stage).show();
	}

}
