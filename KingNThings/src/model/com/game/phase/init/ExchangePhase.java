/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game.phase.init;

import model.com.Thing;
import model.com.game.ThingInput;
import model.com.game.phase.Phase;

/**
 *
 * @author kurtis
 */
public class ExchangePhase extends Phase implements ThingInput {
    
    public ExchangePhase() {
	super("Exchange Things");
    }

    @Override
    public void execute(Thing thing) {
	
    }
}
