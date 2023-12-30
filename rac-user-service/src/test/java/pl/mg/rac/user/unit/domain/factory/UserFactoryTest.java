package pl.mg.rac.user.unit.domain.factory;

import org.junit.jupiter.api.Test;
import pl.mg.rac.commons.value.Location;
import pl.mg.rac.user.domain.factory.UserFactory;
import pl.mg.rac.user.domain.model.User;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserFactoryTest {

    @Test
    void createNewUserTest() {
        //given
        //when
        User user = UserFactory.createNewUser("name", new Location(BigDecimal.ONE, BigDecimal.TWO));
        //then
        assertNull(user.getId());
        assertEquals("name", user.getName());
        assertEquals(BigDecimal.ZERO, user.getBalance());
        assertEquals(new Location(BigDecimal.ONE, BigDecimal.TWO), user.getLocation());
    }
}