package view.com;

import controller.com.StartScreenCntrl;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartScreen {
	
	private Stage stage;
	private Label myLabel;
	private Button startButton;

	public StartScreen(Stage stg){
		stage = stg;
	}
	
	public void show(){
		myLabel = new Label("Hello World");
		startButton = new Button("Start");
		
		VBox root = new VBox();
		root.getChildren().addAll(myLabel, startButton);
		
		Scene scene = new Scene(root, 1000, 500);
		scene.getStylesheets().add("view/com/kingsnthings.css");
		
		stage.setScene(scene);
		stage.show();
		
		new StartScreenCntrl(myLabel, startButton);
	}
	
	// Setters and Getter
	public Button getStartButton() {
		return startButton;
	}
	public void setStartButton(Button startButton) {
		this.startButton = startButton;
	}
	
}
