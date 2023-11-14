package pl.mg.rac.rent.infrastructure.in.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.rac.rent.application.dto.command.RequestRentCommand;
import pl.mg.rac.rent.application.dto.response.RequestRentResponse;
import pl.mg.rac.rent.application.facade.RentFacade;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/rent")
@Slf4j
public class RentCommandController {

    private final RentFacade rentFacade;

    public RentCommandController(RentFacade rentFacade) {
        this.rentFacade = rentFacade;
    }

    @PostMapping(value = "")
    public ResponseEntity<RequestRentResponse> rentRequest(@RequestBody RequestRentCommand command) throws URISyntaxException {
        log.debug("rentRequest() called with: command = [" + command + "]");
        RequestRentResponse rent = rentFacade.requestRent(command);
        return ResponseEntity.created(new URI("/rent/" + rent.rentId())).body(rent);
    }

}
