package Test.Model.Services;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.Services.UserService;

public class UserServiceTest {
    @Test
    public void testGetUserCountDoesNotThrow() {
        UserService service = new UserService();
        int count = service.getUserCount();
        assertTrue(count >= 0);
    }
}
