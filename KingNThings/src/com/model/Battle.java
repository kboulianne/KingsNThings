package com.model;

import java.util.ArrayList;

public class Battle {
	
	private int roundNumber;
	private boolean unExploredHex;
	private Hex associatedHex;
	
	private Player defender;//  can be Player or is null if hex is unexplored/has no oner
	private ArrayList<GamePiece> defenderItems; 
	private Die defenderDie1;
	private Die defenderDie2;
	
	private Player offender;
	private ArrayList<Creature> offenderCreatures;
	private Die offenderDie1;
	private Die offenderDie2;
	 
	private BattleRound battleRound;
	public enum BattleRound { MAGIC("Magic"), RANGED("Ranged"), 
		MELEE("Melee"), RETREAT("Retreat"), POSTCOMBAT("Post Combat") ;
		public final String battleRoundName;
		BattleRound(String n) { battleRoundName = n; }
		
	}
	
	
	public Battle(Player offender, Hex hex){
		associatedHex = hex;
		roundNumber = 1;
		battleRound = BattleRound.MAGIC;
		this.offender = offender;
		offenderCreatures = hex.getArmies(offender);
		
		defenderDie1 = new Die();
		defenderDie2 = new Die();
		
		offenderDie1 = new Die();
		offenderDie2 = new Die();
		if(hex.getOwner()==null){
			unExploredHex = true;
			defender = null;
			defenderItems = hex.getMiscItems();
		}else{
			unExploredHex = false;
			defender = hex.getOwner();
			defenderItems = new ArrayList<GamePiece>();
			for (Creature c: hex.getArmies(hex.getOwner()))
				defenderItems.add(c);
		}
	}
	
	protected void nextRound(){
		//TODO
		roundNumber++;
		battleRound = BattleRound.MAGIC;
	}
	
	public void nextBattleRound(){
		//TODO
		if(battleRound.ordinal()+1 == BattleRound.values().length )
			nextRound();
		else
			battleRound = BattleRound.values()[battleRound.ordinal()+1];
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

	public BattleRound getBattleRound() {
		return battleRound;
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
	

}
