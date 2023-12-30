package pl.mg.rac.user.unit.application.facade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.user.UserCreatedEvent;
import pl.mg.rac.commons.event.user.payload.UserCreatedPayload;
import pl.mg.rac.commons.value.Location;
import pl.mg.rac.user.application.dto.command.*;
import pl.mg.rac.user.application.dto.exception.UserChargeException;
import pl.mg.rac.user.application.dto.exception.UserDeletionException;
import pl.mg.rac.user.application.dto.exception.UserRegistrationException;
import pl.mg.rac.user.application.dto.query.GetUserQuery;
import pl.mg.rac.user.application.dto.response.*;
import pl.mg.rac.user.application.facade.UserFacade;
import pl.mg.rac.user.application.port.in.*;
import pl.mg.rac.user.application.service.EventApplicationService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {

    @Mock
    private CreateUserPort createUserPort;

    @Mock
    private DeleteUserPort deleteUserPort;

    @Mock
    private GetUserPort getUserPort;

    @Mock
    private BlockUserPort blockUserPort;

    @Mock
    private UnblockUserPort unblockUserPort;

    @Mock
    private GetRandomUserPort getRandomUserPort;

    @Mock
    private ChargeUserPort chargeUserPort;

    @Mock
    private EventApplicationService eventApplicationService;

    @InjectMocks
    private UserFacade userFacade;


    @Test
    void createUserTest() throws Exception {
        // given
        CreateUserCommand command = new CreateUserCommand("name", new Location(BigDecimal.ONE, BigDecimal.TWO));
        CreateUserResponse expectedResponse = new CreateUserResponse("name", BigDecimal.ZERO);
        when(createUserPort.createUser(command)).thenReturn(expectedResponse);

        // when
        CreateUserResponse actualResponse = userFacade.createUser(command);

        // then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createUserExceptionTest() throws Exception {
        // given
        CreateUserCommand command = new CreateUserCommand("name", new Location(BigDecimal.ONE, BigDecimal.TWO));
        when(createUserPort.createUser(command)).thenThrow(new UserRegistrationException("test"));

        // when
        // then
        assertThrows(UserRegistrationException.class, () -> userFacade.createUser(command));
    }

    @Test
    void deleteUserTest() throws Exception {
        // given
        DeleteUserCommand command = new DeleteUserCommand("name");
        Mockito.doNothing().when(deleteUserPort).deleteUser(command);

        // when

        // then
        assertDoesNotThrow(() -> userFacade.deleteUser(command));
    }

    @Test
    void deleteUserExceptionTest() throws Exception {
        // given
        DeleteUserCommand command = new DeleteUserCommand("name");
        Mockito.doThrow(new UserDeletionException("test")).when(deleteUserPort).deleteUser(command);

        // when

        // then
        assertThrows(UserDeletionException.class, () -> userFacade.deleteUser(command));
    }

    @Test
    void chargeUserTest() throws Exception {
        // given
        ChargeUserCommand command = new ChargeUserCommand("name", BigDecimal.ONE);
        ChargeUserResponse expectedResponse = new ChargeUserResponse("name", BigDecimal.ONE);
        when(chargeUserPort.chargeUser(command)).thenReturn(expectedResponse);

        // when
        ChargeUserResponse actualResponse = userFacade.chargeUser(command);

        // then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void chargeUserExceptionTest() throws Exception {
        // given
        ChargeUserCommand command = new ChargeUserCommand("name", BigDecimal.ONE);
        when(chargeUserPort.chargeUser(command)).thenThrow(new UserChargeException("test"));

        // when
        // then
        assertThrows(UserChargeException.class, () -> userFacade.chargeUser(command));
    }

    @Test
    void getUserTest() throws Exception {
        // given
        GetUserQuery query = new GetUserQuery("name");
        UserResponse expectedResponse = new UserResponse("name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO));
        when(getUserPort.getUser(query)).thenReturn(expectedResponse);

        // when
        UserResponse actualResponse = userFacade.getUser(query);

        // then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getRandomUserTest() throws Exception {
        // given
        UserResponse expectedResponse = new UserResponse("name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO));
        when(getRandomUserPort.getRandomUser()).thenReturn(expectedResponse);

        // when
        UserResponse actualResponse = userFacade.getRandomUser();

        // then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void blockUserTest() throws Exception {
        // given
        BlockUserCommand command = new BlockUserCommand("name");
        BlockUserResponse expectedResponse = new BlockUserResponse("name", true);
        when(blockUserPort.block(command)).thenReturn(expectedResponse);

        // when
        BlockUserResponse actualResponse = userFacade.blockUser(command);

        // then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void unblockUserTest() throws Exception {
        // given
        UnblockUserCommand command = new UnblockUserCommand("name");
        UnblockUserResponse expectedResponse = new UnblockUserResponse("name", false);
        when(unblockUserPort.unblock(command)).thenReturn(expectedResponse);

        // when
        UnblockUserResponse actualResponse = userFacade.unblockUser(command);

        // then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void handleIncomingEventTest() {
        // given
        RacEvent<?> event = new UserCreatedEvent("test", new UserCreatedPayload("name", new Location(BigDecimal.ONE, BigDecimal.TWO)));
        Mockito.doNothing().when(eventApplicationService).handleIncomingEvent(event);

        // when

        // then
        assertDoesNotThrow(() -> userFacade.handleIncomingEvent(event));
    }

}