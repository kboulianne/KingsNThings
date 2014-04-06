package com.model;

// Need board method factory that creates two and three player verson, or four player version

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Board {
	
	private int numOfHexes;
	private List<Hex> hexes;
	private boolean faceDown; //Tiles are drawn face down until players select start positions.
	private static final Set<Integer> startPositions; // Four player starting positions

	static {
	    // Initialize start positions
		//TODO 19hex game start points
	    startPositions = new HashSet<>(Arrays.asList(new Integer[] { 19, 23, 28, 32 }));
	}

	public enum NumberOfHexes { THIRTY_SEVEN(37), NINETEEN(19); 
		private final int numberOfHexes;
		NumberOfHexes(int i) {
			numberOfHexes = i;
		}
	}
	
	/*
	 * For gson.
	 */
	@SuppressWarnings("unused")
	private Board() {
		this(NumberOfHexes.THIRTY_SEVEN);
	}
	
	public Board(NumberOfHexes nOH){
	    faceDown = true;
	    numOfHexes = nOH.numberOfHexes;
	    hexes = new ArrayList<>(NumberOfHexes.THIRTY_SEVEN.numberOfHexes);	    
	}
	
	public void addHex(final Hex hex) {
	    // Adding at start position, set flag in hex.
	    int index = hexes.size();   
	    if (startPositions.contains(index))
	    	hex.setStartPosition(true);
	    hexes.add(hex);
	}
	
	public void replaceHex(Hex h)	{
		hexes.set(h.getId(), h);
	}
	
	public int getHexNum() {
	    return numOfHexes;
	}
	
	public List<Hex> getHexes() {
	    return hexes;
	}
	
	public final void setFaceDown(final boolean b) {
		
	    faceDown = b;
	    for (Hex h:hexes)
	    		h.setFaceDown(b);
	    
	}
	public final boolean isFaceDown() {
	    return faceDown;
	}

	public Set<Integer> getStartPositions() {
		return startPositions;
	}
	
	public void reset(){
		for (Hex h:hexes){
			h.setHighlighted(false);
			h.setSelected(false);
		}
	}
	
	/**
	 * Finds hexes where an opponent has armies on a hex controlled by
	 * Player p. The player must have armies on the hex as well.
	 * @param p
	 * @return 
	 */
	public List<Hex> findConflictsFor(Player p) {
		List<Hex> conflicts = new ArrayList<>();
		
		Map<Player, List<Creature>> armies = null;
		
		for (Hex hex : hexes) {
			// Get the armies and check size of key set to know if an
			// opponent is in the tile. size of 1 means no other armies
			armies = hex.getArmies();
			
			
				// We have at least two armies, make sure Player p has
				// an army in the hex
				if (armies.containsKey(p)) {
					
					if (armies.size() >= 2 || !hex.getKedabCreatures().isEmpty()) {
					// Player has an army, mark as conflict
					conflicts.add(hex);
				}
			}
		}
		
		
		return conflicts;
	}	
}
