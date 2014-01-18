/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game.phase;

/**
 *  The Context class in the Strategy Pattern.
 * @author kurtis
 */
//TODO make final
public class Phase {
    private String name;
    private IPhaseStrategy behaviour;
    
    protected Phase(final String name) {
	this.name = name;
    }

    public Phase() {
	
    }
   
    public final void setBehaviour(IPhaseStrategy behave) {
	behaviour = behave;
    }
    
    public final IPhaseStrategy getBehaviour() {
	return behaviour;
    }
//    public abstract void doRoll();
}
