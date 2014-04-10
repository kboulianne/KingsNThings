package com.main;

import java.io.File;

import com.model.Player;
import com.presenter.StartScreenPresenter;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NetworkedMain extends Application {
	private static Stage primaryStage;
	private static Scene scene;

	// The player which owns/plays on this client.
	private static Player player;
	private static String roomName;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		String host = "127.0.0.1";
		Integer port = null;

		// Initialize the client with command line arguments if present
		if (getParameters().getNamed().containsKey("host")) {
			host = getParameters().getNamed().get("host");
		}
		if (getParameters().getNamed().containsKey("port")) {
			port = Integer.parseInt(getParameters().getNamed().get("port"));
		}

		// Defaults to localhost and default port if not present.
		KNTAppFactory.init(host, port);

		final StartScreenPresenter start = KNTAppFactory
				.getStartScreenPresenter();

		stage.setTitle("Kings & Things");

		stage.initStyle(StageStyle.DECORATED);
		stage.setResizable(false);
		stage.setFullScreen(false);
		stage.getIcons().add(new Image("view/com/assets/pics/icon.png"));

		scene = new Scene(start.getView());
		scene.getStylesheets().add("view/com/assets/docs/kingsnthings.css");
		scene.getStylesheets().add("view/com/assets/docs/gameScreen.css");
		primaryStage = stage;

		
		
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	public static boolean isPlayerTurn(Player gameCurrent) {
		return gameCurrent.equals(player);
	}

	public static final Player getPlayer() {
		return player;
	}

	public static final void setPlayer(final Player player) {
		NetworkedMain.player = player;
	}

	public static final String getRoomName() {
		return roomName;
	}
	
	@Override
	public void stop() throws Exception {
		// Stop all thread, leave room
		KNTAppFactory.cleanup();
		System.exit(0);
	}

	public static final void setRoomName(String room) {
		roomName = room;
	}

	// TODO Implement view interface
	public static void setView(Parent n) {
		scene.setRoot(n);
		// size content
		primaryStage.sizeToScene();
	}

	public static void setView(Parent n, double w, double h) {
		scene.setRoot(n);
		primaryStage.setMinWidth(w);
		primaryStage.setMinHeight(h);
		primaryStage.setWidth(w);
		primaryStage.setHeight(h);
	}

	public static void centerOnScreen() {
		primaryStage.centerOnScreen();
	}

	public static void setTitle(String title) {
		primaryStage.setTitle(title);
	}

	public static File showChooser(FileChooser chooser) {
		return chooser.showOpenDialog(primaryStage);
	}
}