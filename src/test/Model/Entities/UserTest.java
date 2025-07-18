package test.Model.Entities;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.Entities.User;

public class UserTest {
    @Test
    public void testUserConstructorAndGetters() {
        User user = new User("u1", "pass123", 1, "mail@example.com", "John", "Doe");
        assertEquals("u1", user.getUserId());
        assertEquals("pass123", user.getUserPassword());
        assertEquals(1, user.getUserType());
        assertEquals("mail@example.com", user.getUserEmail());
        assertEquals("John", user.getUserFirstName());
        assertEquals("Doe", user.getUserLastName());
    }
}
