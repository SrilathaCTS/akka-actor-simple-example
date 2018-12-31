package com.cts.run;

import java.io.IOException;

import com.cts.run.actor.LoggingAdapterActor;
import com.cts.run.actor.MasterActor;
import com.cts.run.actor.SupervisingActor;
import com.cts.run.actor.TemapratureDeviceRecord;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class RunBasicActor {
 
	public static void main(String[] args) {
		// simple actor 
		ActorSystem system = ActorSystem.create("basic-actor-system");// created actor system
		
		ActorRef master = system.actorOf(Props.create(MasterActor.class)); // reference of the actor
		
		master.tell("Hello Akka", Actor.noSender()); // assign the action to actor
		
		//system.terminate();
		
		// supervising actor 
		//The default supervisor strategy is to stop and restart the child. If you don’t change the default strategy all failures result in a restart.
		ActorRef supervisingActor = system.actorOf(Props.create(SupervisingActor.class), "supervising-actor");
		supervisingActor.tell("childfail", ActorRef.noSender());
		
		// logging 
		 ActorRef supervisor = system.actorOf(LoggingAdapterActor.props(), "logging-supervisor");
		 try {
			System.in.read();
		} catch (IOException e) {
			system.terminate();
		}
		
		 // temparature records
		 ActorRef tempRecord =  system.actorOf(TemapratureDeviceRecord.props("group", "device"), "temparature-record");
		 tempRecord.tell(new TemapratureDeviceRecord.RecordTemperature(1, 24.0), ActorRef.noSender() ); 
		
		
	}
}
 