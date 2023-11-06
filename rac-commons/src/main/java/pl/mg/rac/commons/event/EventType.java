package pl.mg.rac.commons.event;

import lombok.Getter;

@Getter
public enum EventType {

    //USER EVENTS
    RAC_USER_CREATED("RAC_USER_CREATED"),
    RAC_USER_DELETED("RAC_USER_DELETED"),
    RAC_USER_CHARGED("RAC_USER_CHARGED"),

    //CAR EVENTS
    RAC_CAR_CREATED("RAC_CAR_CREATED"),
    RAC_CAR_DELETED("RAC_CAR_DELETED"),
    RAC_CAR_RENTED("RAC_CAR_RENTED"),
    RAC_CAR_RETURNED("RAC_CAR_RETURNED"),

    //RENTAL EVENTS

    //LOCATION CHANGE EVENTS

    ;

    private final String id;

    EventType(String id) {
        this.id = id;
    }

}
