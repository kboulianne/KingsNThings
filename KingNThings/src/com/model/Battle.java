package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Battle {
	
	private Player currentPlayer; 
	
	private int roundNumber;
	private boolean kedabFight;
	private Hex associatedHex;
	
	private Player defender;
	private Fort defenderFort;
	private int defHits;
	
	private Player offender;
	private int offHits;
	/** Contains information about the current state of the turn. */
	private String info; 
	private String instructions;
	
	// Splits creatures according to phases
	private Map<BattlePhase, List<Creature>> offendingForces;
	private Map<BattlePhase, List<Creature>> defendingForces;
	
	private BattlePhase battlePhase;
	public enum BattlePhase { MAGIC("Magic"), APPLYMAJHITS("Apply Magic Hits"), 
		RANGED("Ranged"), APPLYRANHITS("Apply Ranged Hits"), 
		MELEE("Melee"), APPLYMELHITS("Apply Melee Hits"),
		RETREAT("Retreat"), POSTCOMBAT("Post Combat") ;
		public final String phaseAsString;
		BattlePhase(String n) { phaseAsString = n; }
	}
	
	// Needed by Gson
	public Battle() {}
	
	/** For PvP.
	 * 
	 * @param offender
	 * @param defender
	 * @param hex 
	 */
	public Battle(Player offender, Player defender, Hex hex, boolean kedabFight) {
		currentPlayer = offender;
		this.defender = defender;		
		associatedHex = hex;
		roundNumber = 1;
		battlePhase = BattlePhase.MAGIC;
		this.offender = offender;
		defHits= 0;
		offHits=0;
		defenderFort = hex.getFort();
		
		offendingForces = splitCreatures(hex.getArmies(offender));
		this.kedabFight = kedabFight;
		
		if(kedabFight){
			defendingForces = splitCreatures(hex.getKedabCreatures());
		}else{
			defendingForces = splitCreatures(hex.getArmies(defender));
		}
	}
	
	private Map<BattlePhase, List<Creature>> splitCreatures(List<Creature> creatures) {
		Map<BattlePhase, List<Creature>> forces = new HashMap<BattlePhase, List<Creature>>();
		// Init the map
		forces.put(BattlePhase.MAGIC, new ArrayList<Creature>());
		forces.put(BattlePhase.MELEE, new ArrayList<Creature>());
		forces.put(BattlePhase.RANGED, new ArrayList<Creature>());
		
		BattlePhase phase = null;
		List<Creature> list = null;
		for (Creature c: creatures) {
			c.setSelected(true);
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

	public void killOffenderCreature(Creature c) {
		// remove from hex, and from map
		associatedHex.getArmies(offender).remove(c);
		
		BattlePhase phase = null;
		if (c.isMagic()) {
			phase = BattlePhase.MAGIC;
		} 
		else if (c.isRanged() && !c.isMagic()) {
			phase = BattlePhase.RANGED;
		}
		else if (!c.isRanged() && !c.isMagic()) { // MELEE
			phase = BattlePhase.MELEE;
		}
		offendingForces.get(phase).remove(c);
	}
	
	public void killDefenderCreature(Creature c) {
		// remove from hex, and from map
		if(isKedabFight()){
			associatedHex.getKedabCreatures().remove(c);
		}else{
			associatedHex.getArmies(defender).remove(c);
		}
		BattlePhase phase = null;
		if (c.isMagic()) {
			phase = BattlePhase.MAGIC;
		} 
		else if (c.isRanged() && !c.isMagic()) {
			phase = BattlePhase.RANGED;
		}
		else if (!c.isRanged() && !c.isMagic()) { // MELEE
			phase = BattlePhase.MELEE;
		}
		defendingForces.get(phase).remove(c);
	}
	
	public boolean isBattleResolved() {
		// Resovled when one or both sides can no longer roll.
		//boolean att, def;
		
		// Forced to retreat
		if (offendingForces.get(BattlePhase.MAGIC).isEmpty()
				&& offendingForces.get(BattlePhase.RANGED).isEmpty()
				&& offendingForces.get(BattlePhase.MELEE).isEmpty())
			return true;
		
		return false;
	}
	
	public boolean isOffenderEliminated() { // Eliminated if no troops left in the hex.
		return associatedHex.getArmies(offender).isEmpty(); 
	}
	
	public boolean isDefenderEliminated() {
		if(isKedabFight()){
			return associatedHex.getKedabCreatures().isEmpty();
		}else{
			return associatedHex.getArmies(defender).isEmpty();
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
	
	public Player getOffender() {
		return offender;
	}
	
	public BattlePhase getBattleRound() {
		return battlePhase;
	}
	
	public Hex getAssociatedHex() {
		return associatedHex;
	}
	
	public Fort getDefenderFort() {
		return defenderFort;
	}
	public boolean isKedabFight() {
		return kedabFight;
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
	
	public List<Creature> getOffenderCreatures() {
		return associatedHex.getArmies(offender);
	}
	
	public List<Creature> getDefenderCreatures() {
		
		if(isKedabFight()){
			return associatedHex.getKedabCreatures();
		}else{
			return associatedHex.getArmies(defender);
		}
	}
	
	/**
	 * Gets the attacker's creatures for the specified phase.
	 * @return The list of creatures.
	 */
	public List<Creature> getOffenderCreaturesForPhase() {
		return offendingForces.get(battlePhase);
	}	
	/**
	 * Gets the defender's creatures for the specified phase.
	 * @return 
	 */
	public List<Creature> getDefenderCreaturesForPhase() {
		return defendingForces.get(battlePhase);
	}
	
	
	public String toString(){
		return "Battle object: phase: "+ battlePhase.phaseAsString+" hex:"+ associatedHex.getId()+
				"\nOffender: "+ offender.getName()+"\nDefender: "+defender.getName();
	}

	public int getDefHits() {
		return defHits;
	}

	public void setDefHits(int defHits) {
		this.defHits = defHits;
	}

	public int getOffHits() {
		return offHits;
	}

	public void setOffHits(int offHits) {
		this.offHits = offHits;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	public String getInstructions() {
		return instructions;
	}
}
