/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game.phase;

import model.com.Hex;
import model.com.game.HexInput;

/**
 *
 * @author kurtis
 */
public class StartPosPhase extends Phase implements HexInput {

    public StartPosPhase() {
	super("Starting Positions");
    }
    
    @Override
    public void execute(final Hex hex) {
	
    }
    
}
