package view.com;

import controller.com.Main;
import controller.com.StartScreenCntrl;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StartScreen {
	
	public void show(){
		
		ImageView crownImg = new ImageView(new Image("view/com/assets/pics/crown.png"));
        crownImg.setFitWidth(500); //600
        crownImg.setPreserveRatio(true);
        crownImg.setSmooth(true);
        crownImg.setCache(true);
        
        Label title = new Label("Kings & Things");
        title.setId("title");
		
        Label playersName = new Label("Enter your Noble's name:");
        
		TextField textField = new TextField ();
		textField.setId("startTF");
		//textField.setPromptText("Enter your Noble's name");
		
		Button startButton = new Button("Play");
		startButton.setId("startButton");
		
		VBox container = new VBox();
		container.setMaxWidth(478);
		container.setSpacing(10);
		container.getChildren().addAll(title, playersName, textField, startButton);
		
		VBox root = new VBox();
		root.setId("vBox");
		root.getChildren().addAll(crownImg, container);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root);// 1000 x 600
		scene.getStylesheets().add("view/com/assets/docs/kingsnthings.css");
		scene.getStylesheets().add("view/com/assets/docs/startScreen.css");

		Stage stage = Main.getStage();
		stage.setScene(scene);
		stage.show();
		
		//fade
		FadeTransition ft = new FadeTransition(Duration.millis(2000), root);
	    ft.setFromValue(0.5);
	    ft.setToValue(1.0);
	    ft.play();

		new StartScreenCntrl(textField, startButton);
	}
	
}
