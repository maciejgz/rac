package pl.mg.rac.user.unit.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mg.rac.commons.value.Location;
import pl.mg.rac.user.application.dto.command.*;
import pl.mg.rac.user.application.dto.exception.UserChargeException;
import pl.mg.rac.user.application.dto.exception.UserDeletionException;
import pl.mg.rac.user.application.dto.exception.UserNotFoundException;
import pl.mg.rac.user.application.dto.exception.UserRegistrationException;
import pl.mg.rac.user.application.dto.query.GetUserQuery;
import pl.mg.rac.user.application.dto.response.CreateUserResponse;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.application.port.out.UserEventPublisher;
import pl.mg.rac.user.application.service.UserApplicationService;
import pl.mg.rac.user.domain.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserApplicationServiceTest {

    @Mock
    UserDatabase userDatabase;

    @Mock
    UserEventPublisher userEventPublisher;

    @InjectMocks
    UserApplicationService userApplicationService;

    @Test
    void createUserSuccessTest() throws Exception {
        // given
        CreateUserCommand command = new CreateUserCommand("test", new Location(BigDecimal.ONE, BigDecimal.TWO));
        when(userDatabase.exists(command.name())).thenReturn(false);
        User mockedUser = new User(UUID.randomUUID().toString(), "name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), null, LocalDate.now(), false);
        when(userDatabase.save(Mockito.any())).thenReturn(mockedUser);
        Mockito.doNothing().when(userEventPublisher).publishUserEvent(Mockito.any());

        // when
        CreateUserResponse createdUser = userApplicationService.createUser(command);

        // then
        assertEquals(createdUser.name(), mockedUser.getName());
        assertEquals(createdUser.balance(), mockedUser.getBalance());
    }

    @Test
    void createUserAlreadyExistsTest() {
        // given
        CreateUserCommand command = new CreateUserCommand("test", new Location(BigDecimal.ONE, BigDecimal.TWO));
        when(userDatabase.exists(command.name())).thenReturn(true);
        // when
        // then
        assertThrows(UserRegistrationException.class, () -> userApplicationService.createUser(command));
    }

    @Test
    void chargeUserSuccessTest() throws UserChargeException {
        // given
        User mockedUser = new User(UUID.randomUUID().toString(), "name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), null, LocalDate.now(), false);
        Mockito.doNothing().when(userEventPublisher).publishUserEvent(Mockito.any());
        when(userDatabase.findByName(mockedUser.getName())).thenReturn(java.util.Optional.of(mockedUser));
        ChargeUserCommand command = new ChargeUserCommand(mockedUser.getName(), BigDecimal.TEN);
        when(userDatabase.save(Mockito.any())).thenReturn(mockedUser);

        // when
        userApplicationService.chargeUser(command);
        // then
        assertEquals(mockedUser.getBalance(), BigDecimal.valueOf(11));
    }

    @Test
    void chargeUserNotExistsTest() {
        // given
        ChargeUserCommand command = new ChargeUserCommand("test", BigDecimal.TEN);
        when(userDatabase.findByName(command.name())).thenReturn(java.util.Optional.empty());
        // when
        // then
        assertThrows(UserChargeException.class, () -> userApplicationService.chargeUser(command));
    }

    @Test
    void deleteUserSuccessTest() throws Exception {
        // given
        User mockedUser = new User(UUID.randomUUID().toString(), "name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), null, LocalDate.now(), false);
        DeleteUserCommand command = new DeleteUserCommand(mockedUser.getName());
        Mockito.doNothing().when(userEventPublisher).publishUserEvent(Mockito.any());
        when(userDatabase.findByName(mockedUser.getName())).thenReturn(Optional.of(mockedUser));
        Mockito.doNothing().when(userDatabase).delete(Mockito.any());
        // when
        userApplicationService.deleteUser(command);
        // then
        Mockito.verify(userDatabase, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    void deleteUserNotExistsTest() {
        // given
        DeleteUserCommand command = new DeleteUserCommand("test");
        when(userDatabase.findByName(command.name())).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(UserDeletionException.class, () -> userApplicationService.deleteUser(command));
    }

    @Test
    void deleteUserAlreadyHasActiveRentTest() {
        // given
        User mockedUser = new User(UUID.randomUUID().toString(), "name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), "sample", LocalDate.now(), false);
        DeleteUserCommand command = new DeleteUserCommand(mockedUser.getName());
        when(userDatabase.findByName(mockedUser.getName())).thenReturn(Optional.of(mockedUser));
        // when

        // then
        assertThrows(UserDeletionException.class, () -> userApplicationService.deleteUser(command));
    }

    @Test
    void getUserSuccessTest() throws Exception {
        // given
        User mockedUser = new User(UUID.randomUUID().toString(), "name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), null, LocalDate.now(), false);
        when(userDatabase.findByName(mockedUser.getName())).thenReturn(Optional.of(mockedUser));
        GetUserQuery query = new GetUserQuery(mockedUser.getName());
        // when
        var user = userApplicationService.getUser(query);
        // then
        assertEquals(user.name(), mockedUser.getName());
        assertEquals(user.balance(), mockedUser.getBalance());
        assertEquals(user.location(), mockedUser.getLocation());
    }

    @Test
    void getUserNotExistsTest() {
        // given
        GetUserQuery query = new GetUserQuery("test");
        when(userDatabase.findByName(query.name())).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(UserNotFoundException.class, () -> userApplicationService.getUser(query));
    }

    @Test
    void getRandomUserSuccessTest() throws Exception {
        // given
        User mockedUser = new User(UUID.randomUUID().toString(), "name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), null, LocalDate.now(), false);
        when(userDatabase.getRandomUser()).thenReturn(Optional.of(mockedUser));
        // when
        var user = userApplicationService.getRandomUser();
        // then
        assertEquals(user.name(), mockedUser.getName());
        assertEquals(user.balance(), mockedUser.getBalance());
        assertEquals(user.location(), mockedUser.getLocation());
    }

    @Test
    void getRandomUserNotExistsTest() {
        // given
        when(userDatabase.getRandomUser()).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(UserNotFoundException.class, () -> userApplicationService.getRandomUser());
    }

    @Test
    void blockUserSuccessTest() throws Exception {
        // given
        User mockedUser = new User(UUID.randomUUID().toString(), "name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), null, LocalDate.now(), false);
        when(userDatabase.findByName(mockedUser.getName())).thenReturn(Optional.of(mockedUser));
        // when
        var user = userApplicationService.block(new BlockUserCommand(mockedUser.getName()));
        // then
        assertEquals(user.name(), mockedUser.getName());
        assertEquals(user.blocked(), mockedUser.isBlocked());
    }

    @Test
    void blockUserNotExistsTest() {
        // given
        when(userDatabase.findByName("test")).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(UserNotFoundException.class, () -> userApplicationService.block(new BlockUserCommand("test")));
    }

    @Test
    void blockUserAlreadyBlockedTest() throws Exception {
        // given
        User mockedUser = new User(UUID.randomUUID().toString(), "name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), null, LocalDate.now(), true);
        when(userDatabase.findByName(mockedUser.getName())).thenReturn(Optional.of(mockedUser));
        // when
        var user = userApplicationService.block(new BlockUserCommand(mockedUser.getName()));
        // then
        assertEquals(user.name(), mockedUser.getName());
        assertEquals(user.blocked(), mockedUser.isBlocked());
    }

    @Test
    void unblockUserAlreadyBlockedTest() throws Exception {
        // given
        User mockedUser = new User(UUID.randomUUID().toString(), "name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), null, LocalDate.now(), true);
        when(userDatabase.findByName(mockedUser.getName())).thenReturn(Optional.of(mockedUser));
        // when
        var user = userApplicationService.unblock(new UnblockUserCommand(mockedUser.getName()));
        // then
        assertEquals(user.name(), mockedUser.getName());
        assertEquals(user.blocked(), mockedUser.isBlocked());
    }

    @Test
    void unblockUserNotExistsTest() {
        // given
        when(userDatabase.findByName("test")).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(UserNotFoundException.class, () -> userApplicationService.unblock(new UnblockUserCommand("test")));
    }

    @Test
    void unblockUserSuccessTest() throws Exception {
        // given
        User mockedUser = new User(UUID.randomUUID().toString(), "name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), null, LocalDate.now(), true);
        when(userDatabase.findByName(mockedUser.getName())).thenReturn(Optional.of(mockedUser));
        // when
        var user = userApplicationService.unblock(new UnblockUserCommand(mockedUser.getName()));
        // then
        assertEquals(user.name(), mockedUser.getName());
        assertEquals(user.blocked(), mockedUser.isBlocked());
    }

}