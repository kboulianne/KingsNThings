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
public class GameEventProducer implements Runnable {

	// Object for now
	private final BlockingQueue<String> queue;
	private String msg = null;
	// Wait until an event is available.
	
	
	public GameEventProducer(BlockingQueue<String> shared) {
		this.queue = shared;
	}
	
	@Override
	public void run() {
		try {
			while (true) produce();
		} catch (InterruptedException ex) {
			Logger.getLogger(GameEventProducer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void produce() throws InterruptedException {
		
		// Wait for message is null
		while (msg == null) {
			synchronized (this) {
				System.out.println("PRODUCER: Waiting for message to be set.");
				wait();
			}
		}
		
		System.out.println("PRODUCER: Message Received: Time to Produce.");
		
		// TODO CHange to boolean instead of queue?
		// Wait for queue (limit to 1 for now since we can't wait for multiple events)
		while (queue.size() == 1) {
			synchronized (queue) {
				System.out.println("PRODUCER: Waiting for queue.");
				queue.wait();
			}
		}
		
		synchronized (queue) {
			System.out.println("PRODUCER: Added message to queue. Waiting to be consumed.");
			queue.add(msg);
			// Use setter so msg gets locked through this
			setMessage(null);
			queue.notifyAll();
		}
//		queue.add(msg);
		
	}
	
	public void setMessage(String msg) {
		synchronized(this) {
			this.msg = msg;
			// Wake the producer
			notifyAll();
		}
	}
	// Produce on demand.
//	public void produce(/* eventtype */) throws InterruptedException {
//
//	}
	
}
