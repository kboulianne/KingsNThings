package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Battle {
	
	private Player currentPlayer; // if null Creature and Things
	
	private int roundNumber;
	private boolean unExploredHex;
	private Hex associatedHex;
	
	private Player defender;//  can be Player or is null if hex is unexplored/has no oner
//	private List<Creature> defenderCreatures;
	private Fort defenderFort;
//	private List<GamePiece> defenderItems; 
	
	private Player offender;
//	private List<Creature> offenderCreatures;
	 
	// Splits creatues according to phases
	private Map<BattlePhase, List<Creature>> attackingForces;
	private Map<BattlePhase, List<Creature>> defendingForces;
	
	
	
	private BattlePhase battlePhase;
	public enum BattlePhase { MAGIC("Magic"), RANGED("Ranged"), 
		MELEE("Melee"), RETREAT("Retreat"), POSTCOMBAT("Post Combat") ;
		public final String phaseAsString;
		BattlePhase(String n) { phaseAsString = n; }
		
	}
	
	/** For PvP.
	 * 
	 * @param offender
	 * @param defender
	 * @param hex 
	 */
	public Battle(Player offender, Player defender, Hex hex) {
		currentPlayer = offender;
		this.defender = defender;		
		associatedHex = hex;
		roundNumber = 1;
		battlePhase = BattlePhase.MAGIC;
		this.offender = offender;
//		offenderCreatures = hex.getArmies(offender);
//		// Defender creatures
//		defenderCreatures = hex.getArmies(defender);

//		defenderItems = hex.getMiscItems();
		// Only one per tile
		defenderFort = hex.getFort();
		
		attackingForces = splitCreatures(hex.getArmies(offender));
		defendingForces = splitCreatures(hex.getArmies(defender));
		
		setUnExploredHex(false);

//		defenderItems = new ArrayList<>();
//		for (Creature c: hex.getArmies(hex.getHexOwner()))
//			defenderItems.add(c);
		
	}
	
	private Map<BattlePhase, List<Creature>> splitCreatures(List<Creature> creatures) {
		Map<BattlePhase, List<Creature>> forces = new HashMap<>();
		// Init the map
		forces.put(BattlePhase.MAGIC, new ArrayList<Creature>());
		forces.put(BattlePhase.MELEE, new ArrayList<Creature>());
		forces.put(BattlePhase.RANGED, new ArrayList<Creature>());
		
		BattlePhase phase;
		List<Creature> list = null;
		for (Creature c: creatures) {
			if (c.isMagic()) {
				phase = BattlePhase.MAGIC;
				list = forces.get(phase);
			} 
			else if (c.isRanged() && !c.isMagic()) {
				phase = BattlePhase.RANGED;
				list = forces.get(phase);
			}
			else if (!c.isRanged() && !c.isMagic()) { // MELEE
				phase = BattlePhase.MELEE;
				list = forces.get(phase);
			}
			
			list.add(c);
		}
		
		return forces;
	}
	
	/** For exploration. */
//	public Battle(Player offender,  Hex hex){
//		currentPlayer = offender;
//		
//		associatedHex = hex;
//		roundNumber = 1;
//		battlePhase = BattlePhase.MAGIC;
//		this.offender = offender;
//		offenderCreatures = hex.getArmies(offender);
//		
//		if(hex.getHexOwner()==null){
//			setUnExploredHex(true);
//			defender = null;
//			defenderItems = hex.getMiscItems();
//		}else{
//			setUnExploredHex(false);
//			defender = hex.getHexOwner();
//			defenderItems = new ArrayList<GamePiece>();
//			for (Creature c: hex.getArmies(hex.getHexOwner()))
//				defenderItems.add(c);
//		}
//	}
	

	
	//setters and getters
	
	public int getRoundNumber() {
		return roundNumber;
	}

	public String getDefenderName() {
		if (defender == null)
			return "Creatures and Things";
		return defender.getName();
	}

//	public List<GamePiece> getDefenderItems() {
//		return defenderItems;
//	}
//
//	public ArrayList<Creature> getDefenderItemsThatAreCreatures() {
//		// FOR DEMO. 
//		ArrayList<Creature> creatures = new ArrayList<>();
//		for(GamePiece gp: defenderItems){
//			if (gp instanceof Creature)
//				creatures.add((Creature) gp);
//		}
//		return creatures;
//	}
//	
	public Player getOffender() {
		return offender;
	}
//
//	public List<Creature> getOffenderCreatures() {
//		return offenderCreatures;
//	}
//
//	public List<Creature> getDefenderCreaturesForPhase() {
//		return defenderCreatures;
//	}
	
	public BattlePhase getBattleRound() {
		return battlePhase;
	}

	public Hex getAssociatedHex() {
		return associatedHex;
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
	
	public List<Creature> getAttackerCreatures() {
		return associatedHex.getArmies(offender);
	}
	
	public List<Creature> getDefenderCreatures() {
		return associatedHex.getArmies(defender);
	}
	
	/**
	 * Gets the attacker's creatures for the specified phase.
	 * @return The list of creatures.
	 */
	public List<Creature> getAttackerCreaturesForPhase() {
		return attackingForces.get(battlePhase);
	}
	
	/**
	 * Gets the defender's creatures for the specified phase.
	 * @return 
	 */
	public List<Creature> getDefenderCreaturesForPhase() {
		return defendingForces.get(battlePhase);
	}
	
	public void killAttackerCreature(Creature c) {
		// remove from hex, and from map
		associatedHex.getArmies(offender).remove(c);
		attackingForces.get(battlePhase).remove(c);
	}
	
	public void killDefenderCreature(Creature c) {
		// remove from hex, and from map
		associatedHex.getArmies(defender).remove(c);
		defendingForces.get(battlePhase).remove(c);
	}
}
