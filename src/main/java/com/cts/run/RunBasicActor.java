package com.cts.run;

import com.cts.run.actor.MasterActor;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class RunBasicActor {
 
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("basic-actor-system");// created actor system
		
		ActorRef master = system.actorOf(Props.create(MasterActor.class)); // reference of the actor
		
		master.tell("Hello Akka", Actor.noSender()); // assign the action to actor
		
		system.terminate(); 
	}
}
