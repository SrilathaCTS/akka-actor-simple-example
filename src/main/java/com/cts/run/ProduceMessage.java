package com.cts.run;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class ProduceMessage {

	 public static void main(String[] args) throws InterruptedException {
	        ActorSystem system = ActorSystem.create("generate-numbers-one-to-ten");
	        ActorRef printNumbersConsumer = system.actorOf(Props.create(ConsumerMessage.class));
	        
	        for (int i = 1; i <= 10; i++) {
	            System.out.println(">>> Producing & sending a number " +  i);
	            printNumbersConsumer.tell(i, ActorRef.noSender());
	            TimeUnit.SECONDS.sleep(1);  //// sleep for 1 second before sending the next number
	        }
	        
	        system.terminate();
	        System.out.println("===== Finished producing & sending numbers 1 to 10");
	        
	    }
}

class ConsumerMessage extends UntypedActor {
	 
    @Override
    public void onReceive(Object msg) throws Exception {
        if(msg instanceof Integer) {
            System.out.println("<<< Receiving & printing " + msg);
        }
    }
}