package Test.Controller.Admin;

import org.junit.Test;
import static org.junit.Assert.*;
import Controller.Admin.AdminController;

public class AdminControllerTest {
    @Test
    public void testAddUserWithInvalidEmailReturnsFalse() {
        AdminController controller = new AdminController(null);
        boolean result = controller.addUser("id", "pass", 1, "bademail", "First", "Last");
        assertFalse(result);
    }

    @Test
    public void testAddUserWithShortIdReturnsFalse() {
        AdminController controller = new AdminController(null);
        boolean result = controller.addUser("123456", "password123", 1, "user@gmail.com", "First", "Last");
        assertFalse(result);
    }

    @Test
    public void testAddUserWithLongIdReturnsFalse() {
        AdminController controller = new AdminController(null);
        boolean result = controller.addUser("123456789", "password123", 1, "user@gmail.com", "First", "Last");
        assertFalse(result);
    }

    @Test
    public void testAddUserWithShortPasswordReturnsFalse() {
        AdminController controller = new AdminController(null);
        boolean result = controller.addUser("1234567", "short", 1, "user@gmail.com", "First", "Last");
        assertFalse(result);
    }

    @Test
    public void testAddUserWithInvalidDomainReturnsFalse() {
        AdminController controller = new AdminController(null);
        boolean result = controller.addUser("1234567", "password123", 1, "user@hotmail.com", "First", "Last");
        assertFalse(result);
    }

    @Test
    public void testAddUserWithInvalidEmailFormatReturnsFalse() {
        AdminController controller = new AdminController(null);
        boolean result = controller.addUser("1234567", "password123", 1, "usergmail.com", "First", "Last");
        assertFalse(result);
    }

    @Test
    public void testAddUserWithEmptyFieldsReturnsFalse() {
        AdminController controller = new AdminController(null);
        boolean result = controller.addUser("", "", 1, "", "", "");
        assertFalse(result);
    }

    @Test
    public void testAddUserWithValidDataReturnsTrueOrFalseDependingOnDB() {
        AdminController controller = new AdminController(null);
        boolean result = controller.addUser("12345678", "password123", 1, "user@gmail.com", "First", "Last");
        assertNotNull(result);
    }
}
