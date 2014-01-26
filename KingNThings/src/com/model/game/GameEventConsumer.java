/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.model.game;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kurtis
 */
public class GameEventConsumer implements Runnable {

	private final BlockingQueue<String> queue;
	
	public GameEventConsumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				consume();
			} catch (InterruptedException ex) {
				Logger.getLogger(GameEventConsumer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	
	private void /* eventtype */ consume() throws InterruptedException {
		// Wait until queue has an element
		while (queue.isEmpty()) {
			synchronized (queue) {
				System.out.println("CONSUMER: Waiting for messages to be put in queue.");
				queue.wait();
			}
		}
		
		// Consume the element
		synchronized (queue) {
			System.out.println("CONSUMER: Message Consumed: " + queue.remove());
			queue.notifyAll();
		}
	}
}
