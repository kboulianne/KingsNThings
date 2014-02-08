package com.model;

import java.util.ArrayList;

public class Battle {
	
	int roundNumber;
	boolean unExploredHex;
	
	Object defender;//  can be Player or if unexplored creatures
	ArrayList<Creature> defenderCreatures;
	
	public enum Rounds { MAGIC, RANGED, MELEE, RETREAT, POSTCOMBAT }
	
	
	public Battle(){
		
	}

}
