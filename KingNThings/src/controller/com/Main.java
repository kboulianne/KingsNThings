package controller.com;


import view.com.GameScreen;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
		stage.initStyle(StageStyle.DECORATED);
		stage.setResizable(false);
		stage.setFullScreen(false);
		
		//doesnt work
		//Font.loadFont(Main.class.getResource("VIKING.TTF").toExternalForm(), 12);
		
		//stage.setIconified(true);
		//stage.getIcons().
		stage.getIcons().add(new Image("view/com/assets/pics/icon.png"));
		//stage.setMaxHeight(HEIGHT);
		//stage.setMaxWidth(WIDTH);
		//stage.setFullScreen(true);
		//new StartScreen().show();

//		view.show(); //temporary
		
		/*// rough init 
		//create players as they join the game
		Player player1 = new Player(Player.PlayerId.ONE, "Paul McCartney");
		Player player2 = new Player(Player.PlayerId.TWO, "Bill Gates");
		Player player3 = new Player(Player.PlayerId.THREE, "Mick Jagger");
		Player player4 = new Player(Player.PlayerId.FOUR, "Sean Connery");
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		playerList.add(player4);
		
		// one for each application?
		Board board = new Board();
		int numOfPlayers = playerList.size();
        if(numOfPlayers == 4){
        	board.setupTiles(Board.NumberOfHexes.THIRTY_SEVEN);
		}else if(numOfPlayers == 2||numOfPlayers == 3){
			board.setupTiles(Board.NumberOfHexes.NINETEEN);
		}else{
			Util.log("Board Setup Failure: wrong number of players");
		}
		
        //Game game = new Game(currentPlayer, board, playerList);
        	
		// done init */
		
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
