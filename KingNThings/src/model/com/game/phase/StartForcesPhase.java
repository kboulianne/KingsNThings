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
public class StartForcesPhase extends Phase implements HexInput {

    public StartForcesPhase() {
	super("Starting Forces");
    }
    
    @Override
    public void execute(Hex hex) {
	
    }
    
}
