package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model.Board.NumberOfHexes;
import com.model.Player.PlayerId;

import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

//TODO Use Factory Pattern?
public class Hex extends GamePiece {
	
    private int id; // location on grid
	private Player owner;
	private Color color;
	private boolean startPosition; // Is the owner's start position
	private boolean selected;
	private boolean selectable;
	private boolean highlighted;
	//boolean facedUp; // is right side up?
	private HexType type;
	private String typeAsString; 
	private int movementWeight; // value of -1 if not able to move to
	private int[] joiningHexes; // Integer array of hex id's, size 6
	
	//      __0__        
	//   5 /     \ 1
    //    /       \      value=-1 if no Hex
	//    \       /
	//   4 \_____/ 2
	//        3	
	static final int joiningHexes37Mapping[][] = {
		{1,2,3,4,5,6}, // 0, radius 0
		{8,9,2,0,6,7},{9,10,11,3,0,1},{2,11,12,13,4,0},{0,3,13,14,15,5},
		{6,0,4,15,16,17},{7,1,0,5,17,18}, // 1-6, radius 1,
		{20,8,1,6,18,19},{21,22,9,1,7,20},{22,23,10,2,1,8},{23,24,25,11,2,9}, // 7-10
		{10,25,26,12,3,2},{11,26,27,28,13,3},{3,12,28,29,14,4},{4,13,29,30,31,15}, // 11-14
		{5,4,14,31,32,16},{17,5,15,32,33,34},{18,6,5,16,34,35},{19,7,6,17,35,36},// 15-18, radius 2
		{-1,20,7,18,36,-1},{-1,21,8,7,19,-1},{-1,-1,22,8,20,-1},{-1,-1,23,9,8,21}, // 19-22
		{-1,-1,24,10,9,22},{-1,-1,-1,25,10,23},{24,-1,-1,26,11,10},{25,-1,-1,27,12,11}, // 23-26
		{26,-1,-1,-1,28,12},{12,27,-1,-1,29,13},{13,28,-1,-1,30,14},{14,29,-1,-1,-1,31},// 27-30
		{15,14,30,-1,-1,32},{16,15,31,-1,-1,33},{34,16,32,-1,-1,-1},{35,17,16,33,-1,-1},// 31-34
		{36,18,17,34,-1,-1},{-1,19,18,35,-1,-1}};
	//TODO init
	static final int joiningHexes19Mapping[][] = {};
	
	static final Image START_IMAGE = new Image("view/com/assets/pics/tiles/start.png");
	// list of armies for all players
	// list of misc Things
	private HashMap<Player, ArrayList<Creature>> armies;
	private ArrayList<GamePiece> miscItems;

	
	public enum HexType {
		JUNGLE_HEX("Jungle"),
		FROZEN_WASTE_HEX("Frozen Waste"),
		FOREST("Forest"),
		PLAINS("Plains"),
		SWAMP("Swamp"),
		MOUNTAIN("Mountain"),
		DESERT("Desert"),
		SEA("Sea"); 
		private final String typeName;
		HexType(String n) {
			typeName = n;
		}
	}
	
	public Hex(int id, HexType type){
	    this.type = type;
	    this.id= id;
	    setTypeAsString(type.typeName);
	    color = Color.DARKGRAY;
	    startPosition =  false;
	    selected = false;
	    selectable = true; // may have to change for startup
	    //setJoiningHexes();
	    armies = new HashMap<Player, ArrayList<Creature>>();
	    miscItems = new ArrayList<GamePiece>();

	    setMovementWeight(-1);
	}

	
    // setters and getters
    public final Player getOwner() {
    	return owner;
    }
    public final void setOwner(final Player player) {
    	this.owner = player;
    }
    public Color getColor() {
	    return color;
    }
    public void setColor(Color color) {
	    this.color = color;
    }
    public boolean isSelected() {
	    return selected;
    }
    public void setSelected(boolean selected) {
	    this.selected = selected;
    }
    public final HexType getType() {
    	return type;
    }
    public final boolean isStartPosition() {
    	return startPosition;
    }
    public void setStartPosition(boolean b) {
    	this.startPosition = b;
    }
    public int getId() {
	    return id;
    }
    public void setId(int id, NumberOfHexes numOfHexes) {
	    this.id = id;
	    setJoiningHexes(numOfHexes);
    }
    public int[] getJoiningHexes() {
	    return joiningHexes;
    }
    protected void setJoiningHexes(NumberOfHexes numOfHexes) {
	    joiningHexes = new int[6];
	    if(numOfHexes.equals(NumberOfHexes.THIRTY_SEVEN)){
		    for(int i=0; i<6; i++){
			    joiningHexes[i] = joiningHexes37Mapping[id][i];
		    }
	    }else if(numOfHexes.equals(NumberOfHexes.NINETEEN)){
	    	for(int i=0; i<6; i++){
			    joiningHexes[i] = joiningHexes19Mapping[id][i];
		    }
	    }
    }
    public boolean isSelectable() {
	    return selectable;
    }
    public void setSelectable(boolean selectable) {
	    this.selectable = selectable;
    }
    public boolean isHighlighted() {
	    return highlighted;
    }
    public void setHighlighted(boolean highlighted) {
	    this.highlighted = highlighted;
    }   
    public Map<Player, ArrayList<Creature>> getArmies() {
    	return armies;
    }
    public ArrayList<Creature> getArmies(Player p)	{
    	return armies.get(p);
    }  
    public ArrayList<GamePiece> getMiscItems() {
		return miscItems;
	}
	public void setMiscItems(ArrayList<GamePiece> miscItems) {
		this.miscItems = miscItems;
	}
	public String getTypeAsString() {
		return typeAsString;
	}
	public void setTypeAsString(String typeAsString) {
		this.typeAsString = typeAsString;
	}
	public int getMovementWeight() {
		return movementWeight;
	}
	public void setMovementWeight(int movementWeight) {
		this.movementWeight = movementWeight;
	}
    
    public void addCreatToArmy(Creature creature, Player p)	{
    	if(armies.get(p).size() < 10)	{
    		armies.get(p).add(creature);
    	}
    }

    public void addItemToHex(GamePiece item)	{
    	miscItems.add(item);
    }
}