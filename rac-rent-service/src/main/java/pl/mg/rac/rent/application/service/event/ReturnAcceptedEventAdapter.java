package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.rentreturn.ReturnConfirmationEvent;
import pl.mg.rac.rent.application.port.in.AcceptReturn;

@Slf4j
public class ReturnAcceptedEventAdapter implements EventAdapter<ReturnConfirmationEvent>, AcceptReturn {

    @Override
    public void handle(ReturnConfirmationEvent event) {

    }
}
