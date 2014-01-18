/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game.phase;

/**
 *
 * @author kurtis
 */
public abstract class Phase {
    private String name;
    
    protected Phase(final String name) {
	this.name = name;
    }
}
