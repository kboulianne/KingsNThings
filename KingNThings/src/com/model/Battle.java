package com.model;

import java.util.ArrayList;
import java.util.List;

public class Battle {
	
	private Player currentPlayer; // if null Creature and Things
	
	private int roundNumber;
	private boolean unExploredHex;
	private Hex associatedHex;
	
	private Player defender;//  can be Player or is null if hex is unexplored/has no oner
	private List<Creature> defenderCreatures;
	private Fort defenderFort;
	private ArrayList<GamePiece> defenderItems; 
	private Die defenderDie1;
	private Die defenderDie2;
	
	private Player offender;
	private ArrayList<Creature> offenderCreatures;
	private Die offenderDie1;
	private Die offenderDie2;
	 
	private BattlePhase battlePhase;
	public enum BattlePhase { MAGIC("Magic"), RANGED("Ranged"), 
		MELEE("Melee"), RETREAT("Retreat"), POSTCOMBAT("Post Combat") ;
		public final String phaseAsString;
		BattlePhase(String n) { phaseAsString = n; }
		
	}
	
	/** For PvP. */
	public Battle(Player offender, Player defender, Hex hex) {
		currentPlayer = offender;
		this.defender = defender;
		
		associatedHex = hex;
		roundNumber = 1;
		battlePhase = BattlePhase.MAGIC;
		this.offender = offender;
		offenderCreatures = hex.getArmies(offender);
		// Defender creatures
		defenderCreatures = hex.getArmies(defender);
		
		defenderDie1 = new Die();
		defenderDie2 = new Die();
		
		offenderDie1 = new Die();
		offenderDie2 = new Die();

//		defenderItems = hex.getMiscItems();
		// Only one per tile
		defenderFort = hex.getFort();
		

		setUnExploredHex(false);

//		defenderItems = new ArrayList<>();
//		for (Creature c: hex.getArmies(hex.getHexOwner()))
//			defenderItems.add(c);
		
	}
	
	/** For exploration. */
	public Battle(Player offender,  Hex hex){
		currentPlayer = offender;
		
		associatedHex = hex;
		roundNumber = 1;
		battlePhase = BattlePhase.MAGIC;
		this.offender = offender;
		offenderCreatures = hex.getArmies(offender);
		
		defenderDie1 = new Die();
		defenderDie2 = new Die();
		
		offenderDie1 = new Die();
		offenderDie2 = new Die();
		if(hex.getHexOwner()==null){
			setUnExploredHex(true);
			defender = null;
			defenderItems = hex.getMiscItems();
		}else{
			setUnExploredHex(false);
			defender = hex.getHexOwner();
			defenderItems = new ArrayList<GamePiece>();
			for (Creature c: hex.getArmies(hex.getHexOwner()))
				defenderItems.add(c);
		}
	}
	

	
	//setters and getters
	
	public int getRoundNumber() {
		return roundNumber;
	}

	public String getDefenderName() {
		if (defender == null)
			return "Creatures and Things";
		return defender.getName();
	}

	public ArrayList<GamePiece> getDefenderItems() {
		return defenderItems;
	}

	public ArrayList<Creature> getDefenderItemsThatAreCreatures() {
		// FOR DEMO. 
		ArrayList<Creature> creatures = new ArrayList<>();
		for(GamePiece gp: defenderItems){
			if (gp instanceof Creature)
				creatures.add((Creature) gp);
		}
		return creatures;
	}
	
	public Player getOffender() {
		return offender;
	}

	public ArrayList<Creature> getOffenderCreatures() {
		return offenderCreatures;
	}

	public List<Creature> getDefenderCreatures() {
		return defenderCreatures;
	}
	
	public BattlePhase getBattleRound() {
		return battlePhase;
	}

	public Hex getAssociatedHex() {
		return associatedHex;
	}

	public Die getDefenderDie1() {
		return defenderDie1;
	}

	public Die getDefenderDie2() {
		return defenderDie2;
	}

	public Die getOffenderDie1() {
		return offenderDie1;
	}

	public Die getOffenderDie2() {
		return offenderDie2;
	}

	public Fort getDefenderFort() {
		return defenderFort;
	}
	
	public boolean isUnExploredHex() {
		return unExploredHex;
	}

	public void setUnExploredHex(boolean unExploredHex) {
		this.unExploredHex = unExploredHex;
	}
	public BattlePhase getBattlePhase() {
		return battlePhase;
	}



	public void setBattlePhase(BattlePhase battlePhase) {
		this.battlePhase = battlePhase;
	}



	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}



	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}



	public Player getDefender() {
		return defender;
	}
	

}
