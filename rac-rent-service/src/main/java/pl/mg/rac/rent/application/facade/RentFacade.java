package pl.mg.rac.rent.application.facade;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.rent.application.dto.command.RequestRentCommand;
import pl.mg.rac.rent.application.dto.command.RequestReturnCommand;
import pl.mg.rac.rent.application.dto.exception.InvalidRentStateException;
import pl.mg.rac.rent.application.dto.exception.RentNotFoundException;
import pl.mg.rac.rent.application.dto.query.GetRentByIdQuery;
import pl.mg.rac.rent.application.dto.response.RentResponse;
import pl.mg.rac.rent.application.dto.response.RequestRentResponse;
import pl.mg.rac.rent.application.dto.response.RequestReturnResponse;
import pl.mg.rac.rent.application.port.in.GetRent;
import pl.mg.rac.rent.application.port.in.RequestRent;
import pl.mg.rac.rent.application.port.in.RequestReturn;

@Slf4j
public class RentFacade {

    private final RequestRent requestRentAdapter;
    private final GetRent getRentAdapter;
    private final RequestReturn requestReturnAdapter;

    public RentFacade(RequestRent requestRentAdapter, GetRent getRentAdapter, RequestReturn requestReturnAdapter) {
        this.requestRentAdapter = requestRentAdapter;
        this.getRentAdapter = getRentAdapter;
        this.requestReturnAdapter = requestReturnAdapter;
    }

    public RequestRentResponse requestRent(RequestRentCommand command) {
        return requestRentAdapter.requestRent(command);
    }

    public RentResponse getRent(GetRentByIdQuery query) throws RentNotFoundException {
        return getRentAdapter.getRent(query);
    }

    public RequestReturnResponse requestReturn(RequestReturnCommand command) throws InvalidRentStateException, RentNotFoundException {
        return requestReturnAdapter.requestReturn(command);
    }

    public void handleIncomingEvent(RacEvent<?> event) {
        /**
         *  String eventType = record.value().getEventType();
         *   if ("ID1".equals(key)) {
         *             // handle events with ID1
         *         } else if ("ID2".equals(key)) {
         *             // handle events with ID2
         *         } else {
         *             // handle other events
         *         }
         *
         *
         *         public Adapter selectAdapter(MyEvent event) {
         *             switch (EventType.valueOf(event.getEventType())) {
         *                 case EVENT_TYPE_1:
         *                     return new AdapterType1();
         *                 case EVENT_TYPE_2:
         *                     return new AdapterType2();
         *                 default:
         *                     throw new IllegalArgumentException("Invalid event type: " + event.getEventType());
         *             }
         *         }
         */
        //TODO
    }


}
