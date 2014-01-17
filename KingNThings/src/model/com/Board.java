package model.com;

import java.util.ArrayList;
import java.util.List;

public class Board {
	static Board instance;
	
	List<Player> listOfPlayers;
	Cup cup;
	Die[] dice;
	int numOfHexes;
	Hex[] hexArray;	
	
	// singleton
	public Board getInstance(){
		if(instance==null){
			instance = new Board();
		}
		return instance;
	}
	private Board(){
		listOfPlayers = new ArrayList<Player>();
		cup = new Cup();
		dice = new Die[2];
		dice[0] = new Die();
		dice[1] = new Die();
	}
	
	public void setupTiles(int numOfH){
		numOfHexes = numOfH;
		hexArray = new Hex[numOfH];
	}
	
	

}
