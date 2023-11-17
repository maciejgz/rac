package pl.mg.rac.car.application.service;

import pl.mg.rac.car.application.service.event.EventAdapter;
import pl.mg.rac.commons.event.RacEvent;

import java.util.Map;

public class EventApplicationService {

    private final Map<String, EventAdapter<RacEvent<?>>> eventAdapters;

    public EventApplicationService(Map<String, EventAdapter<RacEvent<?>>> eventAdapters) {
        this.eventAdapters = eventAdapters;
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