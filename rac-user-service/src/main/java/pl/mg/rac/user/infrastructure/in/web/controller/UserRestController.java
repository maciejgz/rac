package pl.mg.rac.user.infrastructure.in.web.controller;

import org.springframework.web.bind.annotation.RestController;
import pl.mg.rac.user.application.facade.UserFacade;

@RestController
public class UserRestController {

    private final UserFacade userFacade;

    public UserRestController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
}
