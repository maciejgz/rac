package pl.mg.rac.rent.application.facade;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.rent.application.dto.command.RequestRentCommand;
import pl.mg.rac.rent.application.dto.command.RequestReturnCommand;
import pl.mg.rac.rent.application.dto.exception.CarAlreadyHasActiveRentException;
import pl.mg.rac.rent.application.dto.exception.InvalidRentStateException;
import pl.mg.rac.rent.application.dto.exception.RentNotFoundException;
import pl.mg.rac.rent.application.dto.exception.UserAlreadyHasActiveRentException;
import pl.mg.rac.rent.application.dto.query.GetRentByIdQuery;
import pl.mg.rac.rent.application.dto.response.RentResponse;
import pl.mg.rac.rent.application.dto.response.RequestRentResponse;
import pl.mg.rac.rent.application.dto.response.RequestReturnResponse;
import pl.mg.rac.rent.application.port.in.GetRent;
import pl.mg.rac.rent.application.port.in.RequestRent;
import pl.mg.rac.rent.application.port.in.RequestReturn;
import pl.mg.rac.rent.application.service.EventApplicationService;

@Slf4j
public class RentFacade {

    private final RequestRent requestRentAdapter;
    private final GetRent getRentAdapter;
    private final RequestReturn requestReturnAdapter;
    private final EventApplicationService eventApplicationService;

    public RentFacade(RequestRent requestRentAdapter, GetRent getRentAdapter, RequestReturn requestReturnAdapter, EventApplicationService eventApplicationService) {
        this.requestRentAdapter = requestRentAdapter;
        this.getRentAdapter = getRentAdapter;
        this.requestReturnAdapter = requestReturnAdapter;
        this.eventApplicationService = eventApplicationService;
    }

    public RequestRentResponse requestRent(RequestRentCommand command) throws UserAlreadyHasActiveRentException, CarAlreadyHasActiveRentException {
        return requestRentAdapter.requestRent(command);
    }

    public RentResponse getRent(GetRentByIdQuery query) throws RentNotFoundException {
        return getRentAdapter.getRent(query);
    }

    public RequestReturnResponse requestReturn(RequestReturnCommand command) throws InvalidRentStateException, RentNotFoundException {
        return requestReturnAdapter.requestReturn(command);
    }

    public void handleIncomingEvent(RacEvent<?> event) {
        eventApplicationService.handleIncomingEvent(event);
    }

}
