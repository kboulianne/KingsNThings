package model.com;

import java.util.ArrayList;
import java.util.List;

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
	
	
	// singleton
//	public Board getInstance(){
//		if(instance==null){
//			instance = new Board();
//		}
//		return instance;
//	}
	
	protected Board(){
//		listOfPlayers = new ArrayList<Player>();
		cup = new Cup();
		dice = new Die[2];
		dice[0] = new Die();
		dice[1] = new Die();
	}
	
	public void setupTiles(int numOfH){
		numOfHexes = numOfH;
		hexArray = new Hex[numOfH];
		
		//factory = new HexFactory()
		// factory.createHex(DESERT_HEX)
		// or
		// 
	}
	
	

}
