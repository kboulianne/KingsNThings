/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game;

import model.com.Thing;

/**
 *  Implementors execute logic by taking a Thing as input.
 * 
 * @author kurtis
 */
public interface ThingInput {
    void execute(final Thing thing);
}
