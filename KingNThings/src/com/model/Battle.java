package com.model;

import java.util.ArrayList;

import com.main.KNTAppFactory;
import com.presenter.BattlePresenter;
import com.view.BattleView;

public class Battle {
	
	private Player currentPlayer; // if null Creature and Things
	
	private int roundNumber;
	private boolean unExploredHex;
	private Hex associatedHex;
	private boolean playerRetreated;
	private boolean playerHasWon;
	
	private Player defender;//  can be Player or is null if hex is unexplored/has no oner
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
	
	
	public Battle(Player offender, Hex hex){
		currentPlayer = offender;
		
		associatedHex = hex;
		roundNumber = 1;
		battlePhase = BattlePhase.MAGIC;
		this.offender = offender;
		offenderCreatures = hex.getArmies(offender);
		playerHasWon = false;
		playerRetreated = false;
		
		defenderDie1 = new Die();
		defenderDie2 = new Die();
		
		offenderDie1 = new Die();
		offenderDie2 = new Die();
		if(hex.getOwner()==null){
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

	public Player getOffender() {
		return offender;
	}

	public ArrayList<Creature> getOffenderCreatures() {
		return offenderCreatures;
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

	public boolean isUnExploredHex() {
		return unExploredHex;
	}

	public void setUnExploredHex(boolean unExploredHex) {
		this.unExploredHex = unExploredHex;
	}



	public boolean isPlayerRetreated() {
		return playerRetreated;
	}



	public void setPlayerRetreated(boolean playerRetreated) {
		this.playerRetreated = playerRetreated;
	}



	public boolean isPlayerHasWon() {
		return playerHasWon;
	}



	public void setPlayerHasWon(boolean playerHasWon) {
		this.playerHasWon = playerHasWon;
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
	

}
