package model.com;

// Need board method factory that creates two and three player verson, or four player version
public class Board {
	Cup cup;
//	Die[] dice;
	private int numOfHexes = 37;
	private Hex[] hexArray;
	/** Tiles are drawn face down until players select start
	 *  positions.
	 */
	private boolean faceDown;
	// Four player starting positions, in hexArray
	int[] startPositions = new int[] {19, 23, 28, 32};
	
	public Board(){
	    faceDown = true;
	    cup = new Cup();
//	    dice = new Die[2];
//	    dice[0] = new Die();
//	    dice[1] = new Die();
	    hexArray = new Hex[numOfHexes];
	}
	
	public int getHexNum() {
	    return numOfHexes;
	}
	
	public void setHex(Hex hex, int index) {
	    hexArray[index] = hex;
	}
	
	public final void setFaceDown(final boolean b) {
	    faceDown = b;
	}
	
	public final boolean isFaceDown() {
	    return faceDown;
	}
	
//	public void setupTiles(int numOfH){
//		numOfHexes = numOfH;
//		hexArray = new Hex[numOfH];
//		
//		//factory = new HexFactory()
//		// factory.createHex(DESERT_HEX)
//		// or
//		// 
//	}
	
	

}
