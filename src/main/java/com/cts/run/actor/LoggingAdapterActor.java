package com.cts.run.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class LoggingAdapterActor extends AbstractActor {
	
	 private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	  public static Props props() {
	    return Props.create(LoggingAdapterActor.class);
	  }

	  @Override
	  public void preStart() {
	    log.info("LoggingAdapterActor Application started");
	  }

	  @Override
	  public void postStop() {
	    log.info("LoggingAdapterActor Application stopped");
	  }

	  // No need to handle any messages
	  @Override
	  public Receive createReceive() {
	    return receiveBuilder()
	            .build();
	  }

}
