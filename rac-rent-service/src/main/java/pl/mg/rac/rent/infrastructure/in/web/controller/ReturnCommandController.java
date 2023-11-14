package pl.mg.rac.rent.infrastructure.in.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mg.rac.rent.application.dto.command.RequestReturnCommand;
import pl.mg.rac.rent.application.dto.response.RequestReturnResponse;
import pl.mg.rac.rent.application.facade.RentFacade;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/return")
@Slf4j
public class ReturnCommandController {

    private final RentFacade rentFacade;

    public ReturnCommandController(RentFacade rentFacade) {
        this.rentFacade = rentFacade;
    }

    @PutMapping(value = "/{rentId}")
    public ResponseEntity<RequestReturnResponse> returnRequest(@PathVariable String rentId) throws URISyntaxException {
        log.debug("returnRequest() called with: rentId = [" + rentId + "]");
        RequestReturnResponse returnResponse = rentFacade.requestReturn(new RequestReturnCommand(rentId));
        return ResponseEntity.created(new URI("/rent/" + returnResponse.rentId())).body(returnResponse);
    }

}
