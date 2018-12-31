package com.cts.run.actor;

import akka.actor.AbstractActor;

public class SupervisedActor  extends AbstractActor {

	@Override
	  public void preStart() {
	    System.out.println("SupervisedActor started");
	  }

	  @Override
	  public void postStop() {
	    System.out.println("SupervisedActor stopped");
	  }

	@Override
	public Receive createReceive() {
		 return receiveBuilder()
			        .matchEquals("fail", f -> {
			          System.out.println("supervised actor fails now");
			          throw new Exception("child failed!");
			        })
			        .build();
	}
	  
}
