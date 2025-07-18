package Test.Model.Entities;
 
import org.junit.Test;
import static org.junit.Assert.*;
import Model.Entities.User;

public class UserTest {
    @Test
    public void testUserConstructorAndGetters() {
        User user = new User("12345678", "pass12345678", 1, "mail@example.com", "John", "Doe");
        assertEquals("12345678", user.getUserId());
        assertEquals("pass12345678", user.getUserPassword());
        assertEquals(1, user.getUserType());
        assertEquals("mail@example.com", user.getUserEmail());
        assertEquals("John", user.getUserFirstName());
        assertEquals("Doe", user.getUserLastName());
    }
}
