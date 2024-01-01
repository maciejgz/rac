package pl.mg.rac.user.application.facade;

import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.user.application.dto.command.*;
import pl.mg.rac.user.application.dto.exception.*;
import pl.mg.rac.user.application.dto.query.GetUserQuery;
import pl.mg.rac.user.application.dto.response.*;
import pl.mg.rac.user.application.port.in.*;
import pl.mg.rac.user.application.service.EventApplicationService;

/**
 * Facade for user service - it is a place where we can put all application services to be used in the controller.
 * In such a simple project it is just a boilerplate, but in a real project it can be useful.
 */
public class UserFacade {

    private final CreateUserPort createUserAdapter;
    private final DeleteUserPort deleteUserAdapter;
    private final ChargeUserPort chargeUserAdapter;
    private final GetUserPort getUserAdapter;
    private final BlockUserPort blockUserPort;
    private final UnblockUserPort unblockUserPort;
    private final GetRandomUserPort getRandomUserAdapter;
    private final EventApplicationService eventApplicationService;

    public UserFacade(CreateUserPort createUserAdapter, DeleteUserPort deleteUserAdapter, ChargeUserPort chargeUserAdapter, GetUserPort getUserAdapter, BlockUserPort blockUserPort, UnblockUserPort unblockUserPort,
                      GetRandomUserPort getRandomUserAdapter, EventApplicationService eventApplicationService) {
        this.createUserAdapter = createUserAdapter;
        this.deleteUserAdapter = deleteUserAdapter;
        this.chargeUserAdapter = chargeUserAdapter;
        this.getUserAdapter = getUserAdapter;
        this.blockUserPort = blockUserPort;
        this.unblockUserPort = unblockUserPort;
        this.getRandomUserAdapter = getRandomUserAdapter;
        this.eventApplicationService = eventApplicationService;
    }

    public CreateUserResponse createUser(CreateUserCommand command) throws UserRegistrationException {
        return createUserAdapter.createUser(command);
    }

    public void deleteUser(DeleteUserCommand command) throws UserDeletionException {
        deleteUserAdapter.deleteUser(command);
    }

    public ChargeUserResponse chargeUser(ChargeUserCommand command) throws UserChargeException {
        return chargeUserAdapter.chargeUser(command);
    }

    public UserResponse getUser(GetUserQuery query) throws UserNotFoundException {
        return getUserAdapter.getUser(query);
    }

    public UserResponse getRandomUser() throws UserNotFoundException {
        return getRandomUserAdapter.getRandomUser();
    }

    public BlockUserResponse blockUser(BlockUserCommand command) throws UserNotFoundException {
        return blockUserPort.block(command);
    }

    public UnblockUserResponse unblockUser(UnblockUserCommand command) throws UserNotFoundException {
        return unblockUserPort.unblock(command);
    }

    public void handleIncomingEvent(RacEvent<?> event) {
        eventApplicationService.handleIncomingEvent(event);
    }

}
