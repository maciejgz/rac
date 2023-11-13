package pl.mg.rac.rent.infrastructure.in.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mg.rac.rent.application.facade.RentFacade;

@RestController
@RequestMapping("/rent")
@Slf4j
public class RentCommandController {

    private final RentFacade rentFacade;

    public RentCommandController(RentFacade rentFacade) {
        this.rentFacade = rentFacade;
    }
}
