package pl.mg.rac.user.unit.domain.model;

import org.junit.jupiter.api.Test;
import pl.mg.rac.commons.value.Location;
import pl.mg.rac.user.domain.model.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void mapUserTest() {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        //when
        User user = new User("id", "name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), "rentId", registrationDate, false);
        //then
        assertEquals("id", user.getId());
        assertEquals("name", user.getName());
        assertEquals(BigDecimal.ONE, user.getBalance());
        assertEquals(new Location(BigDecimal.ONE, BigDecimal.TWO), user.getLocation());
        assertEquals("rentId", user.getCurrentRentId());
        assertEquals(registrationDate, user.getRegistrationDate());
        assertFalse(user.isBlocked());
    }

    @Test
    public void createNewUserTest() {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        //when
        User user = new User("name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);
        //then
        assertNull(user.getId());
        assertEquals("name", user.getName());
        assertEquals(BigDecimal.ONE, user.getBalance());
        assertEquals(new Location(BigDecimal.ONE, BigDecimal.TWO), user.getLocation());
        assertEquals(registrationDate, user.getRegistrationDate());
        assertFalse(user.isBlocked());
    }

    @Test
    public void blockUserTest() {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        User user = new User("name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);
        //when
        user.block();
        //then
        assertTrue(user.isBlocked());
    }

    @Test
    public void unblockUserTest() {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        User user = new User("name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);
        user.block();
        //when
        user.unblock();
        //then
        assertFalse(user.isBlocked());
    }

    @Test
    public void startRentTest() {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        User user = new User("name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);
        //when
        user.startRent("rentId");
        //then
        assertEquals("rentId", user.getCurrentRentId());
    }

    @Test
    public void returnSuccessTest() {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        User user = new User("name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);
        user.startRent("rentId");
        //when
        user.returnSuccess();
        //then
        assertNull(user.getCurrentRentId());
    }

    @Test
    public void chargeTest() {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        User user = new User("name", BigDecimal.ONE, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);
        //when
        BigDecimal balance = user.charge(BigDecimal.TEN);
        //then
        assertEquals(BigDecimal.valueOf(11), balance);
        assertEquals(1, user.getEvents().size());
    }

    @Test
    public void finishRentAndChargeOldUserTest() throws Exception {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        //1 January 2020 00:00:00
        Instant rentStartDate = Instant.ofEpochSecond(1577836800);
        //2 January 2020 00:00:00
        Instant rentEndDate = Instant.ofEpochSecond(1577923200);
        User user = new User("name", BigDecimal.TEN, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);
        //when
        BigDecimal balance = user.finishRentAndCharge(rentStartDate, rentEndDate, BigDecimal.ONE);
        //then
        assertEquals(BigDecimal.valueOf(288.1), balance);
    }

    @Test
    public void finishRentAndChargeFreshUserTest() throws Exception {
        //given
        LocalDate registrationDate = LocalDate.now().minusDays(2);
        //00:00 two days ago
        LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
        LocalDateTime midnight = twoDaysAgo.atStartOfDay();
        Instant rentStartDate = midnight.atZone(ZoneId.systemDefault()).toInstant();

        LocalDate oneDayAgo = LocalDate.now().minusDays(1);
        LocalDateTime midnightOneDayAgo = oneDayAgo.atStartOfDay();
        Instant rentEndDate = midnightOneDayAgo.atZone(ZoneId.systemDefault()).toInstant();

        //2 January 2020 00:00:00
        User user = new User("name", BigDecimal.TEN, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);
        //when
        BigDecimal balance = user.finishRentAndCharge(rentStartDate, rentEndDate, BigDecimal.ONE);
        //then
        assertEquals(BigDecimal.valueOf(432.2), balance);
    }

    @Test
    public void finishRentAndChargeUserBlockedExceptionTest() {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        User user = new User("name", BigDecimal.TEN, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);
        //when
        user.block();
        //then
        assertThrows(Exception.class, () -> user.finishRentAndCharge(null, null, BigDecimal.ONE));
    }

    @Test
    public void rollbackRentReturnTest() {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        User user = new User("name", BigDecimal.TEN, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);
        //when
        user.rollbackRentReturn("rentId", BigDecimal.ONE);
        //then
        assertEquals("rentId", user.getCurrentRentId());
        assertEquals(BigDecimal.valueOf(9), user.getBalance());
    }

    @Test
    public void requestRentTest() throws Exception {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        User user = new User("name", BigDecimal.TEN, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);
        //when
        user.requestRent();
        //then
        //no exception
    }

    @Test
    public void requestRentUserBlockedExceptionTest() {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        User user = new User("name", BigDecimal.TEN, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);

        //when
        user.block();
        //then
        assertThrows(Exception.class, user::requestRent);
    }

    @Test
    public void addEventTest() {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        User user = new User("name", BigDecimal.TEN, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);
        //when
        user.addEvent(null);
        //then
        //no exception
    }

    @Test
    public void cancelRentTest() {
        //given
        LocalDate registrationDate = LocalDate.of(2020, 1, 1);
        User user = new User("name", BigDecimal.TEN, new Location(BigDecimal.ONE, BigDecimal.TWO), registrationDate);
        //when
        user.cancelRent();
        //then
        assertNull(user.getCurrentRentId());
    }

}