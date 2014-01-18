/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game.phase.init;

import model.com.Hex;
import model.com.game.HexInput;
import model.com.game.phase.Phase;

/**
 *
 * @author kurtis
 */
public class StartKingdomPhase extends Phase implements HexInput {

    public StartKingdomPhase() {
	super("Starting Kingdoms");
    }
    
    /**
     * Receives a hex tile as input to execute the phase.
     * 
     * @param hex 
     */
    @Override
    public void execute(Hex hex) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
