import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.cts.run.actor.TemapratureDeviceRecord;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
 

class TemapratureDeviceRecordTest {

	ActorSystem system = ActorSystem.create("basic-actor-system");// created actor system
	
	
	@Test
	void test() {
		 TestKit probe = new TestKit(system);
		  ActorRef deviceActor = system.actorOf(TemapratureDeviceRecord.props("group", "device"));

		  deviceActor.tell(new TemapratureDeviceRecord.RecordTemperature(1, 24.0), probe.getRef());
		  assertEquals(1L, probe.expectMsgClass(TemapratureDeviceRecord.TemperatureRecorded.class).requestId);

		  deviceActor.tell(new TemapratureDeviceRecord.ReadTemperature(2), probe.getRef());
		  TemapratureDeviceRecord.RespondTemperature response1 = probe.expectMsgClass(TemapratureDeviceRecord.RespondTemperature.class);
		  assertEquals(2L, response1.requestId);
		  assertEquals(Optional.of(24.0), response1.value);

		  deviceActor.tell(new TemapratureDeviceRecord.RecordTemperature(3, 55.0), probe.getRef());
		  assertEquals(3L, probe.expectMsgClass(TemapratureDeviceRecord.TemperatureRecorded.class).requestId);

		  deviceActor.tell(new TemapratureDeviceRecord.ReadTemperature(4), probe.getRef());
		  TemapratureDeviceRecord.RespondTemperature response2 = probe.expectMsgClass(TemapratureDeviceRecord.RespondTemperature.class);
		  assertEquals(4L, response2.requestId);
		  assertEquals(Optional.of(55.0), response2.value);
	}

}
