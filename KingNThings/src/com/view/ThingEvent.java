/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.model.Thing;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 *
 * @author kurtis
 */
public class ThingEvent extends Event {

	/**
	 *
	 */
	private static final long serialVersionUID = -5197694746437106725L;
	private Thing thing;
	public static final EventType<ThingEvent> THING_CLICKED = new EventType<>(ANY, "THING_CLICKED");

	public ThingEvent(Thing thing) {
		this(THING_CLICKED);
		this.thing = thing;
	}

	public ThingEvent(EventType<? extends Event> arg) {
		super(arg);
	}

	public ThingEvent(Object obj, EventTarget tar, EventType<? extends Event> type) {
		super(obj, tar, type);
	}

	public Thing getThing() {
		return thing;
	}
}
