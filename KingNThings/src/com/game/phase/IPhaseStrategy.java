/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.game.phase;

import com.model.Game;

/**
 *
 * @author kurtis
 */
public interface IPhaseStrategy {
	
    void phaseStart(Game game);
    void turnStart(Game game);
    void turnEnd(Game game); 
    void phaseEnd();
}
