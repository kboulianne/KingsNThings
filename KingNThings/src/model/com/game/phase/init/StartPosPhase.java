/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game.phase.init;

import com.services.game.GameService;
import model.com.Hex;
import model.com.game.HexInput;
import model.com.game.phase.IPhaseStrategy;
import model.com.game.phase.Phase;

/**
 *
 * @author kurtis
 */
public class StartPosPhase implements IPhaseStrategy<Hex> /*implements HexInput*/ {

    @Override
    public void execute(final Hex input) {
	GameService service = GameService.getInstance();
	
    }

    
    
//    @Override
//    public void execute(final Hex hex) {
//	
//    }
    
}
