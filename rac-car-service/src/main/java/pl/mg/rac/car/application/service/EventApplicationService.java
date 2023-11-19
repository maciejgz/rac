package pl.mg.rac.car.application.service;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.car.application.service.event.EventAdapter;
import pl.mg.rac.commons.event.RacEvent;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EventApplicationService {

    private final Map<String, EventAdapter<RacEvent<?>>> eventAdapters;

    public EventApplicationService(Map<String, EventAdapter<RacEvent<?>>> adapters) {
        eventAdapters = new HashMap<>();
        for (EventAdapter<RacEvent<?>> adapter : adapters.values()) {
            eventAdapters.put(adapter.getEventType(), adapter);
        }
    }

    public void handleIncomingEvent(RacEvent<?> event) {
        String eventType = event.getEventType();
        EventAdapter<RacEvent<?>> adapter = eventAdapters.get(eventType);
        if (adapter == null) {
            log.warn("No adapter registered for event type: " + eventType);
        } else {
            adapter.handle(event);
        }
    }
}
