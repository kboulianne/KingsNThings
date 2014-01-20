package controller.com;

import model.com.Hex;
import model.com.HexDesert;

public class HexFactory {
	
	public Hex createHex(int id, Hex.HexType type){
		
		Hex hex = null;
		switch (type) {
	        case DESERT:  hex = new HexDesert(id);
	                 break;
	                /*
	        case FOREST:  hex = new HexForest();
	                 break;
	        case FROZEN_WASTE_HEX:  hex = new HexFrozenWaste();
	                 break;
	        case JUNGLE_HEX:  hex = new HexJungle();
	                 break;
	        case MOUNTAIN:  hex = new MountainHex();
	                 break;
	        case PLAINS:  hex = new PlainsHex();
	                 break;
	        case SEA:  hex = new SeaHex();
	                 break;
	        case SWAMP:  hex = new SwampHex();
	                 break;
	                 */
	        default: hex = null;
	                 break;
		}
		
		return hex;
	}
	
}
