package com.cts.run.actor;

import java.util.Optional;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class TemapratureDeviceRecord extends AbstractActor {
  private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

  final String groupId;

  final String deviceId;
  
  Optional<Double> lastTemperatureReading = Optional.empty();
  
  @Override
  public void preStart() {
    log.info("TemapratureDeviceRecord actor started", groupId, deviceId);
  }

  @Override
  public void postStop() {
    log.info("TemapratureDeviceRecord actor stopped", groupId, deviceId);
  }
  
  public TemapratureDeviceRecord(String groupId, String deviceId) {
    this.groupId = groupId;
    this.deviceId = deviceId;
  }

  public static Props props(String groupId, String deviceId) {
    return Props.create(TemapratureDeviceRecord.class, groupId, deviceId);
  }

  public static final class RecordTemperature {
    final int requestId;
    final double value;

    public RecordTemperature(int requestId, double value) {
      this.requestId = requestId;
      this.value = value;
    }
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
            .match(RecordTemperature.class, r -> {
              log.info("Recorded temperature reading with: ", r.value, r.requestId);
              lastTemperatureReading = Optional.of(r.value);
              getSender().tell(new TemperatureRecorded(r.requestId), getSelf());
            })
            .match(ReadTemperature.class, r -> {
              getSender().tell(new RespondTemperature(r.requestId, lastTemperatureReading), getSelf());
            })
            .build();
  }
  

  public static final class TemperatureRecorded {
    public final int requestId;

    public TemperatureRecorded(int requestId) {
      this.requestId = requestId;
    }
  }

  public static final class ReadTemperature {
    final long requestId;

    public ReadTemperature(long requestId) {
      this.requestId = requestId;
    }
  }

  public static final class RespondTemperature {
    public final long requestId;
    public final Optional<Double> value;

    public RespondTemperature(long requestId, Optional<Double> value) {
      this.requestId = requestId;
      this.value = value;
    }
  }

}