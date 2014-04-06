package com.main;

import java.util.List;

import com.model.Board;
import com.model.Fort;
import com.model.Hex;
import com.model.Player;

public class Mini {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player p1 = new Player("Player 1"); //yellow
		Player p2 = new Player("Player 2"); //grey
		Player p3 = new Player("Player 3"); //green
		Player p4 = new Player("Player 4"); ///red
		
		
		Board board = new Board(Board.NumberOfHexes.THIRTY_SEVEN);
		
		List<Hex> hexes = board.getHexes();
		
		Hex hex0 = new Hex(0, Hex.HexType.SWAMP);
		hex0.setOwner(p4);
		hex0.setFaceDown(false);
		hex0.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(0, hex0);

		Hex hex1 = new Hex(1, Hex.HexType.SEA);
		hex1.setFaceDown(false);
		hexes.set(1, hex1);
		
		Hex hex2 = new Hex(2, Hex.HexType.PLAINS);
		hex2.setOwner(p4);
		hex2.setFaceDown(false);
		//add stack 1
		hex2.setFort(new Fort(Fort.FortType.TOWER));
		hexes.set(0, hex2);
		
		Hex hex3 = new Hex(3, Hex.HexType.FROZEN_WASTE_HEX);
		hex3.setOwner(p1);
		hex3.setFaceDown(false);
		//add stack 2
		hex3.setFort(new Fort(Fort.FortType.TOWER));
		hexes.set(3, hex3);
		
		Hex hex4 = new Hex(4, Hex.HexType.DESERT);
		hex4.setOwner(p1);
		hex4.setFaceDown(false);
		hex4.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(4, hex4);
		
		Hex hex5 = new Hex(5, Hex.HexType.FOREST);
		hex5.setOwner(p1);
		hex5.setFaceDown(false);
		hex5.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(5, hex5);
		
		Hex hex6 = new Hex(6, Hex.HexType.MOUNTAIN);
		hex6.setOwner(p1);
		hex6.setFaceDown(false);
		hex6.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(6, hex6);
		
		Hex hex7 = new Hex(7, Hex.HexType.DESERT);
		hex7.setOwner(p1);
		hex7.setFaceDown(false);
		hex7.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(7, hex7);
		
		Hex hex8 = new Hex(8, Hex.HexType.SWAMP);
		hex8.setOwner(p1);
		hex8.setFaceDown(false);
		hex8.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(8, hex8);
		
		Hex hex9 = new Hex(9, Hex.HexType.MOUNTAIN);
		hex9.setOwner(p1);
		hex9.setFaceDown(false);
		hex9.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(9, hex9);
		
		Hex hex10 = new Hex(10, Hex.HexType.FOREST);
		hex10.setOwner(p1);
		hex10.setFaceDown(false);
		hex10.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(10, hex10);
		
		Hex hex11 = new Hex(11, Hex.HexType.DESERT);
		hex11.setOwner(p1);
		hex11.setFaceDown(false);
		hex11.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(11, hex11);
		
		Hex hex12 = new Hex(12, Hex.HexType.FOREST);
		hex12.setOwner(p1);
		hex12.setFaceDown(false);
		hex12.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(12, hex12);
		
		Hex hex13 = new Hex(13, Hex.HexType.JUNGLE_HEX);
		hex13.setOwner(p1);
		hex13.setFaceDown(false);
		hex13.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(13, hex13);
		
		Hex hex14 = new Hex(14, Hex.HexType.MOUNTAIN);
		hex14.setOwner(p1);
		hex14.setFaceDown(false);
		hex14.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(14, hex14);
		
		Hex hex15 = new Hex(15, Hex.HexType.PLAINS);
		hex15.setOwner(p1);
		hex15.setFaceDown(false);
		hex15.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(15, hex15);
		
		Hex hex16 = new Hex(16, Hex.HexType.SEA);
		hex16.setOwner(p1);
		hex16.setFaceDown(false);
		hex16.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(16, hex16);
		
		Hex hex17 = new Hex(17, Hex.HexType.MOUNTAIN);
		hex17.setOwner(p1);
		hex17.setFaceDown(false);
		hex17.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(17, hex17);
		
		Hex hex18 = new Hex(18, Hex.HexType.FROZEN_WASTE_HEX);
		hex18.setOwner(p1);
		hex18.setFaceDown(false);
		hex18.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(18, hex0);
		
		Hex hex19 = new Hex(19, Hex.HexType.SWAMP);
		hex19.setOwner(p1);
		hex19.setFaceDown(false);
		hex19.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(19, hex19);		
		
		Hex hex20 = new Hex(20, Hex.HexType.PLAINS);
		hex20.setOwner(p1);
		hex20.setFaceDown(false);
		hex20.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(20, hex0);		
		
		Hex hex21 = new Hex(21, Hex.HexType.SEA);
		hex21.setFaceDown(false);
		hexes.set(21, hex21);
		
		Hex hex22 = new Hex(22, Hex.HexType.JUNGLE_HEX);
		hex22.setOwner(p4);
		hex22.setFaceDown(false);
		//add stack 1
		hex22.setFort(new Fort(Fort.FortType.TOWER));
		hexes.set(22, hex22);
		
		Hex hex23 = new Hex(23, Hex.HexType.FROZEN_WASTE_HEX);
		hex23.setOwner(p1);
		hex23.setFaceDown(false);
		//add stack 2
		hex23.setFort(new Fort(Fort.FortType.TOWER));
		hexes.set(23, hex23);
		
		Hex hex24 = new Hex(24, Hex.HexType.PLAINS);
		hex24.setOwner(p1);
		hex24.setFaceDown(false);
		hex24.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(24, hex24);
		
		Hex hex25 = new Hex(25, Hex.HexType.FROZEN_WASTE_HEX);
		hex25.setOwner(p1);
		hex25.setFaceDown(false);
		hex25.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(25, hex25);
		
		Hex hex26 = new Hex(26, Hex.HexType.SWAMP);
		hex26.setOwner(p1);
		hex26.setFaceDown(false);
		hex26.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(26, hex26);
		
		Hex hex27 = new Hex(27, Hex.HexType.DESERT);
		hex27.setOwner(p1);
		hex27.setFaceDown(false);
		hex27.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(27, hex27);
		
		Hex hex28 = new Hex(28, Hex.HexType.DESERT);
		hex28.setOwner(p1);
		hex28.setFaceDown(false);
		hex28.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(28, hex28);
		
		Hex hex29 = new Hex(9, Hex.HexType.FROZEN_WASTE_HEX);
		hex29.setOwner(p1);
		hex29.setFaceDown(false);
		hex29.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(29, hex29);
		
		Hex hex30 = new Hex(10, Hex.HexType.MOUNTAIN);
		hex30.setOwner(p1);
		hex30.setFaceDown(false);
		hex30.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(30, hex30);
		
		Hex hex31 = new Hex(31, Hex.HexType.FOREST);
		hex31.setOwner(p1);
		hex31.setFaceDown(false);
		hex31.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(31, hex31);
		
		Hex hex32 = new Hex(32, Hex.HexType.SWAMP);
		hex32.setOwner(p1);
		hex32.setFaceDown(false);
		hex32.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(32, hex32);
		
		Hex hex33 = new Hex(33, Hex.HexType.SEA);
		hex33.setOwner(p1);
		hex33.setFaceDown(false);
		hex33.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(33, hex33);
		
		Hex hex34 = new Hex(34, Hex.HexType.SWAMP);
		hex34.setOwner(p1);
		hex34.setFaceDown(false);
		hex34.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(34, hex34);
		
		Hex hex35 = new Hex(35, Hex.HexType.FOREST);
		hex35.setOwner(p1);
		hex35.setFaceDown(false);
		hex35.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(35, hex35);
		
		Hex hex36 = new Hex(36, Hex.HexType.PLAINS);
		hex36.setOwner(p1);
		hex36.setFaceDown(false);
		hex36.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(36, hex36);
		
		Hex hex37 = new Hex(37, Hex.HexType.SWAMP);
		hex37.setOwner(p1);
		hex37.setFaceDown(false);
		hex37.setFort(new Fort(Fort.FortType.KEEP));
		hexes.set(37, hex37);
	}

}
