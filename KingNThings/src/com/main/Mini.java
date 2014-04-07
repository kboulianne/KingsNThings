package com.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.model.Board;
import com.model.DesertCreature;
import com.model.ForestCreature;
import com.model.Fort;
import com.model.Hex;
import com.model.JungleCreature;
import com.model.MountainCreature;
import com.model.PlainsCreature;
import com.model.Player;
import com.model.Player.PlayerId;
import com.model.SwampCreature;
import com.presenter.HexFactory;
import com.util.Util;

public class Mini {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Player p1 = new Player("Player 1"); //yellow
		p1.setPlayerID(PlayerId.ONE);
		Player p2 = new Player("Player 2"); //grey
		p2.setPlayerID(PlayerId.TWO);
		Player p3 = new Player("Player 3"); //green
		p3.setPlayerID(PlayerId.THREE);
		Player p4 = new Player("Player 4"); ///red
		p4.setPlayerID(PlayerId.FOUR);
		
		HexFactory factory = new HexFactory();
		
		Board board = new Board(Board.NumberOfHexes.THIRTY_SEVEN);
		
		
		List<Hex> hexes = board.getHexes();
		
		Hex hex0 = factory.createHex(0, Hex.HexType.SWAMP);
		hex0.setOwner(p4);
		hex0.setFaceDown(false);
		hex0.setFort(new Fort(Fort.FortType.KEEP));
		hexes.add( hex0);

		Hex hex1 = factory.createHex(1, Hex.HexType.SEA);
		hex1.setFaceDown(false);
		hexes.add( hex1);
		
		Hex hex2 = factory.createHex(2, Hex.HexType.PLAINS);
		hex2.setOwner(p4);
		hex2.setFaceDown(false);
		//add stack 1
		hex2.addCreatToArmy(new JungleCreature("crocodilesjungle"), p4);
		hex2.addCreatToArmy(new MountainCreature("mountainmen") , p4);
		hex2.addCreatToArmy(new SwampCreature("giantlizard"), p4);
		hex2.addCreatToArmy(new SwampCreature("slimebeast") , p4);
		hex2.addCreatToArmy(new ForestCreature("killerracoon") , p4);
		hex2.addCreatToArmy(new PlainsCreature("farmers") , p4);
		hex2.addCreatToArmy(new ForestCreature("wildcat") , p4);
		hex2.setFort(new Fort(Fort.FortType.TOWER));
		hexes.add( hex2);
		
		Hex hex3 = factory.createHex(3, Hex.HexType.FROZEN_WASTE_HEX);
		hex3.setOwner(p1);
		hex3.setFaceDown(false);
		//add stack 2
		hex3.addCreatToArmy(new SwampCreature("thing"), p1);
		hex3.addCreatToArmy(new SwampCreature("giantlizard"), p1);
		hex3.addCreatToArmy(new SwampCreature("swamprat"), p1);
		hex3.addCreatToArmy(new ForestCreature("unicorn"), p1);
		hex3.addCreatToArmy(new ForestCreature("bears"), p1);
		hex3.addCreatToArmy(new DesertCreature("giantspider"), p1);
		hex3.addCreatToArmy(new DesertCreature("camelcorps"), p1);
		hex3.addCreatToArmy(new DesertCreature("sandworm"), p1);
		hex3.setFort(new Fort(Fort.FortType.TOWER));
		hexes.add( hex3);
		
		Hex hex4 = factory.createHex(4, Hex.HexType.DESERT);
		hex4.setOwner(p1);
		hex4.setFaceDown(false);
		hexes.add( hex4);
		
		Hex hex5 = factory.createHex(5, Hex.HexType.FOREST);
		hex5.setOwner(p3);
		hex5.setFaceDown(false);
		hexes.add( hex5);
		
		Hex hex6 = factory.createHex(6, Hex.HexType.MOUNTAIN);
		hex6.setFaceDown(false);
		hexes.add( hex6);
		
		Hex hex7 = factory.createHex(7, Hex.HexType.DESERT);
		hex7.setOwner(p2);
		hex7.setFaceDown(false);
		hexes.add( hex7);
		
		Hex hex8 = factory.createHex(8, Hex.HexType.SWAMP);
		hex8.setOwner(p4);
		hex8.setFaceDown(false);
		hexes.add( hex8);
		
		Hex hex9 = factory.createHex(9, Hex.HexType.MOUNTAIN);
		hex9.setOwner(p4);
		hex9.setFaceDown(false);
		hex9.setFort(new Fort(Fort.FortType.CASTLE));
		hexes.add( hex9);
		
		Hex hex10 = factory.createHex(10, Hex.HexType.FOREST);
		hex10.setOwner(p4);
		hex10.setFaceDown(false);
		hex10.setFort(new Fort(Fort.FortType.TOWER));
		hexes.add( hex10);
		
		Hex hex11 = factory.createHex(11, Hex.HexType.DESERT);
		hex11.setOwner(p1);
		hex11.setFaceDown(false);
		hex11.setFort(new Fort(Fort.FortType.KEEP));
		hexes.add( hex11);
		
		Hex hex12 = factory.createHex(12, Hex.HexType.FOREST);
		hex12.setOwner(p1);
		hex12.setFaceDown(false);
		hex12.setFort(new Fort(Fort.FortType.KEEP));
		hexes.add( hex12);
		
		Hex hex13 = factory.createHex(13, Hex.HexType.JUNGLE_HEX);
		hex13.setOwner(p1);
		hex13.setFaceDown(false);
		hex13.setFort(new Fort(Fort.FortType.CASTLE));
		hexes.add( hex13);
		
		Hex hex14 = factory.createHex(14, Hex.HexType.MOUNTAIN);
		hex14.setOwner(p3);
		hex14.setFaceDown(false);
		hexes.add( hex14);
		
		Hex hex15 = factory.createHex(15, Hex.HexType.PLAINS);
		hex15.setOwner(p3);
		hex15.setFaceDown(false);
		hexes.add( hex15);
		
		Hex hex16 = factory.createHex(16, Hex.HexType.SEA);
		hex16.setFaceDown(false);
		hexes.add( hex16);
		
		Hex hex17 = factory.createHex(17, Hex.HexType.MOUNTAIN);
		hex17.setFaceDown(false);
		hexes.add( hex17);
		
		Hex hex18 = factory.createHex(18, Hex.HexType.FROZEN_WASTE_HEX);
		hex18.setOwner(p2);
		hex18.setFaceDown(false);
		hex18.setFort(new Fort(Fort.FortType.TOWER));
		hexes.add( hex18);
		
		Hex hex19 = factory.createHex(19, Hex.HexType.SWAMP);
		hex19.setOwner(p2);
		hex19.setFaceDown(false);
		hex19.setFort(new Fort(Fort.FortType.CASTLE));
		hexes.add( hex19);		
		
		Hex hex20 = factory.createHex(20, Hex.HexType.PLAINS);
		hex20.setOwner(p2);
		hex20.setFaceDown(false);
		hexes.add( hex20);		
		
		Hex hex21 = factory.createHex(21, Hex.HexType.SEA);
		hex21.setFaceDown(false);
		hexes.add( hex21);
		
		Hex hex22 = factory.createHex(22, Hex.HexType.JUNGLE_HEX);
		hex22.setOwner(p4);
		hex22.setFaceDown(false);
		hexes.add( hex22);
		
		Hex hex23 = factory.createHex(23, Hex.HexType.FROZEN_WASTE_HEX);
		hex23.setOwner(p4);
		hex23.setFort(new Fort(Fort.FortType.KEEP));
		hexes.add( hex23);
		
		Hex hex24 = factory.createHex(24, Hex.HexType.PLAINS);
		hex24.setOwner(p4);
		hex24.setFaceDown(false);
		hexes.add( hex24);
		
		Hex hex25 = factory.createHex(25, Hex.HexType.FROZEN_WASTE_HEX);
		hex25.setOwner(p1);
		hex25.setFaceDown(false);
		hex25.setFort(new Fort(Fort.FortType.CASTLE));
		hexes.add( hex25);
		
		Hex hex26 = factory.createHex(26, Hex.HexType.SWAMP);
		hex26.setOwner(p1);
		hex26.setFaceDown(false);
		hex26.setFort(new Fort(Fort.FortType.KEEP));
		hexes.add( hex26);
		
		Hex hex27 = factory.createHex(27, Hex.HexType.DESERT);
		hex27.setOwner(p1);
		hex27.setFaceDown(false);
		hex27.setFort(new Fort(Fort.FortType.TOWER));
		hexes.add( hex27);
		
		Hex hex28 = factory.createHex(28, Hex.HexType.DESERT);
		hex28.setOwner(p1);
		hex28.setFaceDown(false);
		hexes.add( hex28);
		
		Hex hex29 = factory.createHex(29, Hex.HexType.FROZEN_WASTE_HEX);
		hex29.setOwner(p1);
		hex29.setFaceDown(false);
		hexes.add( hex29);
		
		Hex hex30 = factory.createHex(30, Hex.HexType.MOUNTAIN);
		hex30.setOwner(p3);
		hex30.setFaceDown(false);
		hexes.add( hex30);
		
		Hex hex31 = factory.createHex(31, Hex.HexType.FOREST);
		hex31.setOwner(p3);
		hex31.setFaceDown(false);
		hexes.add( hex31);
		
		Hex hex32 = factory.createHex(32, Hex.HexType.SWAMP);
		hex32.setOwner(p3);
		hex32.setFaceDown(false);
		hex32.setFort(new Fort(Fort.FortType.KEEP));
		hexes.add( hex32);
		
		Hex hex33 = factory.createHex(33, Hex.HexType.SEA);
		hex33.setFaceDown(false);
		hex33.setFort(new Fort(Fort.FortType.TOWER));
		hexes.add( hex33);
		
		Hex hex34 = factory.createHex(34, Hex.HexType.SWAMP);
		hex34.setOwner(p2);
		hex34.setFaceDown(false);
		hexes.add( hex34);
		
		Hex hex35 = factory.createHex(35, Hex.HexType.FOREST);
		hex35.setOwner(p2);
		hex35.setFaceDown(false);
		hexes.add( hex35);
		
		Hex hex36 = factory.createHex(36, Hex.HexType.PLAINS);
		hex36.setOwner(p2);
		hex36.setFaceDown(false);
		hex36.setFort(new Fort(Fort.FortType.KEEP));
		hexes.add( hex36);
		
		try{
			FileWriter writer = new FileWriter(args[0]);
			Gson gson = Util.GSON_BUILDER.create();
			writer.write(gson.toJson(board, Board.class));
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
