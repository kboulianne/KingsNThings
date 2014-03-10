package com.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.model.Board.NumberOfHexes;
import com.model.Board;
import com.model.Hex;
import com.model.HexDesert;
import com.model.HexForest;
import com.model.HexFrozenWaste;
import com.model.HexJungle;
import com.model.HexMountain;
import com.model.HexPlains;
import com.model.HexSea;
import com.model.HexSwamp;

public class HexFactory {

	public Hex createHex(int id, Hex.HexType type) {

		Hex hex = null;
		switch (type) {
			case DESERT:
				hex = new HexDesert(id);
				break;
			case FOREST:
				hex = new HexForest(id);
				break;
			case FROZEN_WASTE_HEX:
				hex = new HexFrozenWaste(id);
				break;
			case JUNGLE_HEX:
				hex = new HexJungle(id);
				break;
			case MOUNTAIN:
				hex = new HexMountain(id);
				break;
			case PLAINS:
				hex = new HexPlains(id);
				break;
			case SEA:
				hex = new HexSea(id);
				break;
			case SWAMP:
				hex = new HexSwamp(id);
				break;
			default:
				hex = null;
				break;
		}
		return hex;
	}

	/*
	 /**
	 * Initialization logic for the game board. id all 0 / no position
	 */
	public List<Hex> createHexPool(NumberOfHexes numOfHexes) {
		/* Create all hex instances. The board determines if they are face down or up.  Put all instances in a pool so
		 * they can be picked and assigned at random.
		 * 
		 */
    	//if(numOfHexes.THIRTY_SEVEN){

		List<Hex> hexPool = new ArrayList<>();
		// 48 tiles. Need to "set aside" 4 sea hexes, distribute rest randomly
		// Sea hexes
		for (int i = 0; i < 4; i++) {
			hexPool.add(createHex(0, Hex.HexType.SEA));
		}

		// Others
		// TODO: Should be 44 tiles instead of 42?
		for (int i = 0; i < 6; i++) {
			hexPool.add(createHex(0, Hex.HexType.DESERT));
			hexPool.add(createHex(0, Hex.HexType.FOREST));
			hexPool.add(createHex(0, Hex.HexType.FROZEN_WASTE_HEX));
			hexPool.add(createHex(0, Hex.HexType.JUNGLE_HEX));
			hexPool.add(createHex(0, Hex.HexType.MOUNTAIN));
			hexPool.add(createHex(0, Hex.HexType.PLAINS));
			hexPool.add(createHex(0, Hex.HexType.SWAMP));
		}

		return hexPool;
	}
}
