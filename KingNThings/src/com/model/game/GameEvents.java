/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kurtis
 */
public class GameEvents {

	private boolean available = false;

	public synchronized void produce(/* eventtype */) {
		while (available == true) {
			try {
				wait();
			} catch (InterruptedException ex) {
				Logger.getLogger(GameEvents.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		available = true;
		notifyAll();
		System.out.println("Produced");
	}

	public synchronized void/* eventtype */ consume() {
		while (available == false) {
			try {
				wait();
			} catch (InterruptedException ex) {
				Logger.getLogger(GameEvents.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		System.out.println("Consumed");
		available = false;
		notifyAll();
	}
}
