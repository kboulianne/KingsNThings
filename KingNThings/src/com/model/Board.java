package com.model;

// Need board method factory that creates two and three player verson, or four player version

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
	
	public Board(NumberOfHexes nOH){
	    faceDown = true;
	    numOfHexes = nOH.numberOfHexes;
	    hexes = new ArrayList<>();	    
	}
	
	public void addHex(final Hex hex) {
	    // Adding at start position, set flag in hex.
	    int index = hexes.size();   
	    if (startPositions.contains(index))
	    	hex.setStartPosition(true);
	    hexes.add(hex);
	}
	
	public int getHexNum() {
	    return numOfHexes;
	}
	public List<Hex> getHexes() {
	    return hexes;
	}
	public final void setFaceDown(final boolean b) {
	    faceDown = b;
	}
	public final boolean isFaceDown() {
	    return faceDown;
	}
	
}
