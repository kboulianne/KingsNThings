package model.com;

// Need board method factory that creates two and three player verson, or four player version
public class Board {
	Cup cup;
//	Die[] dice;
	private int numOfHexes;
	private Hex[] hexArray;
	/** Tiles are drawn face down until players select start
	 *  positions.
	 */
	private boolean faceDown;
	// Four player starting positions, in hexArray
	int[] startPositions = new int[] {19, 23, 28, 32};

	public enum NumberOfHexes { THIRTY_SEVEN(37), NINETEEN(19); 
		private final int numberOfHexes;
		NumberOfHexes(int i) {
			numberOfHexes = i;
		}
	}
	
	public Board(NumberOfHexes nOH){
	    faceDown = true;
	    cup = new Cup();
	    numOfHexes = nOH.numberOfHexes;
//	    dice = new Die[2];
//	    dice[0] = new Die();
//	    dice[1] = new Die();
	    hexArray = new Hex[numOfHexes];
	}
	
	public int getHexNum() {
	    return numOfHexes;
	}
	

	
	public void setupTiles(NumberOfHexes numOfH){
		numOfHexes = numOfH.numberOfHexes;
		hexArray = new Hex[numOfHexes];
		
		//factory = new HexFactory()
		// factory.createHex(DESERT_HEX)
		// or
		// 
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
