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
public abstract class AbstractPhaseStrategy<T> implements IPhaseStrategy<T> {

    protected final GamePlay context;
    
    // TODO Avoid calling this in subclases. Don't remember if it is possible.
    protected AbstractPhaseStrategy(final GamePlay context) {
    	this.context = context;
    }
    
//    @Override
//    public void preExecutePhase(T input) {
//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void executePhase(T input) {
//	preExecutePhase(input);
//    }
//
//    @Override
//    public void postExecutePhase(T input) {
//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    public final void execute(T input) {
		preExecutePhase(input);
		executePhase(input);
		postExecutePhase(input);
    }
    
}
