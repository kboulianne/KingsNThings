/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game;

import model.com.Hex;

/**
 *  Used by objects that need to access a hex tile as input and
 *  execute logic.
 * @author kurtis
 */
public interface HexInput {
    void execute(final Hex hex);
}
