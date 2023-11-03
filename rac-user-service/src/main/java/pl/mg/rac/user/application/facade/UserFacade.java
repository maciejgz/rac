package pl.mg.rac.user.application.facade;

import org.springframework.stereotype.Service;
import pl.mg.rac.user.application.service.UserApplicationService;

/**
 * Facade for user service - it is a place where we can put all application services to be used in the controller.
 * In such a simple project it is just a boilerplate, but in a real project it can be useful.
 */
@Service
public class UserFacade {

    private final UserApplicationService userApplicationService;

    public UserFacade(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }
}
