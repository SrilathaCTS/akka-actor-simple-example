package com.cts.run.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class MasterActor extends AbstractActor{

	 @Override
	  public void preStart() {
	    System.out.println("first started");
	  }

	  @Override
	  public void postStop() {
	    System.out.println("first stopped");
	  }

	  
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, msg -> {
			System.out.println(msg);
			 getContext().actorOf(Props.create(ChildActor.class), "second");
		}).build();
		
	} 

} 
