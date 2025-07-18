package Test.Controller.User;

import org.junit.Test;
import static org.junit.Assert.*;
import Controller.User.UserController;

public class UserControllerTest {
    @Test
    public void testUserControllerConstructorDoesNotThrow() {
        UserController controller = new UserController(null, "userId");
        assertNotNull(controller);
    }
}
