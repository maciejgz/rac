package pl.mg.rac.commons.event;

import lombok.Getter;

@Getter
public enum EventType {

    //USER EVENTS
    RAC_USER_CREATED("RAC_USER_CREATED"),
    RAC_USER_DELETED("RAC_USER_DELETED"),
    RAC_USER_CHARGED("RAC_USER_CHARGED")

    //CAR EVENTS

    //RENTAL EVENTS

    //LOCATION CHANGE EVENTS

    ;

    private final String id;

    EventType(String id) {
        this.id = id;
    }

}
