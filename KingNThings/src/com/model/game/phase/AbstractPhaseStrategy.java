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
}
