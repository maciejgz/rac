package pl.mg.rac.location.application.service;

import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.location.application.service.event.EventAdapter;

import java.util.HashMap;
import java.util.Map;

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
            throw new IllegalArgumentException("No adapter registered for event type: " + eventType);
        }
        adapter.handle(event);
    }
}
