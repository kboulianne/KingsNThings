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
    
    // THESE SHOULD ONLY BE EXPOSED TO ABSTRACT CLASS AND SUBCLASSES. CHANGE IPhaseStrategy to abstract class?
    void preExecutePhase(T input);
    
    /**
     * Executes this phase's behaviour.
     * @param input Expects input of type T
     */
    void executePhase(T input);
    
    void postExecutePhase(T input);
    
    void phaseEnd();
}
