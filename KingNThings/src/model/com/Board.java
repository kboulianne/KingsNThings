package model.com;

// Need board method factory that creates two and three player verson, or four player version
public class Board {
//	static Board instance;
	
	// In Game class
//	List<Player> listOfPlayers;

	Cup cup;
	Die[] dice;
	int numOfHexes;
	Hex[] hexArray;	
	// Four player starting positions, in hexArray
	int[] startPositions = new int[] {19, 23, 28, 32};
	
	public enum NumberOfHexes { THIRTY_SEVEN(37), NINETEEN(19); 
		private final int numberOfHexes;
		NumberOfHexes(int i) {
			numberOfHexes = i;
		}
	}
	
	
	// singleton
//	public Board getInstance(){
//		if(instance==null){
//			instance = new Board();
//		}
//		return instance;
//	}
	
	public Board(){
//		listOfPlayers = new ArrayList<Player>();
		cup = new Cup();
		dice = new Die[2];
		dice[0] = new Die();
		dice[1] = new Die();
	}
	
	public void setupTiles(NumberOfHexes numOfH){
		numOfHexes = numOfH.numberOfHexes;
		hexArray = new Hex[numOfHexes];
		
		//factory = new HexFactory()
		// factory.createHex(DESERT_HEX)
		// or
		// 
	}
	
	

}
