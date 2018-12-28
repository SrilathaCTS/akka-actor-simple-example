package com.cts.run.actor;

import akka.actor.AbstractActor;

public class ChildActor extends AbstractActor{

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, msg -> {
			System.out.println(msg);
		}).build();
	}

}  
