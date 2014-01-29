/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.model.game.phase;

/**
 *
 * @author kurtis
 */
public interface IPhaseStrategy<T> {
    
    void phaseStart();
    void turnStart();
    void turnEnd();
    void addHandlers();
    void removeHandlers();    
    void phaseEnd();
}
