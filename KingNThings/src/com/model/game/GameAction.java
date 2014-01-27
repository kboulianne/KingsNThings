/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.model.game;

/**
 *
 * @author kurtis
 */
public enum GameAction {
	ROLL ("Roll");
	
	
	private final String actionString;
	
	private GameAction(String action) {
		actionString = action;
	}
	
	public final String getActionString() {
		return actionString;
	}
}
