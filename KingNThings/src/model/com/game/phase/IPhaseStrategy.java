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
public interface IPhaseStrategy<T> {
    /**
     * Executes this phase's behaviour.
     * @param input Expects input of type T
     */
    void execute(T input);
}
