package com.cts.run.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class SupervisingActor extends AbstractActor {

	ActorRef child = getContext().actorOf(Props.create(SupervisedActor.class), "Child-Actor");
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().matchEquals("childfail", fail ->{
			child.tell("fail", getSelf());
		}).build();
	}

}
