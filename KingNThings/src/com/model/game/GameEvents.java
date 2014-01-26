/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kurtis
 */
public class GameEvents {

	static final BlockingQueue<String> sharedQueue = new ArrayBlockingQueue<>(1);
	static GameEventProducer producer = new GameEventProducer(sharedQueue);
	static GameEventConsumer consumer = new GameEventConsumer(sharedQueue);
//	Thread producer = new Thread(new GameEventProducer(sharedQueue));
//	Thread consumer = new Thread
	
	
	public static GameEventProducer getProducer() {
		return producer;
	}
	
	public static GameEventConsumer getConsumer() {
		return consumer;
	}
}
