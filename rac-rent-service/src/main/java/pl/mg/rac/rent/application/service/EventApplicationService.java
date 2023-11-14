package pl.mg.rac.rent.application.service;

import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.rent.application.service.event.EventAdapter;

import java.util.Map;

public class EventApplicationService {

    private final Map<String, EventAdapter> eventAdapters;

    public EventApplicationService(Map<String, EventAdapter> eventAdapters) {
        this.eventAdapters = eventAdapters;
    }

    public void handleIncomingEvent(RacEvent<?> event) {
        eventAdapters.get(event.getEventType()).handle(event);
    }
}
