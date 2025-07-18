package Test.Controller.Common;

import org.junit.Test;
import static org.junit.Assert.*;
import Controller.Common.RegisterController;

public class RegisterControllerTest {
    @Test
    public void testCheckFieldsWithEmptyFieldsReturnsFalse() {
        RegisterController controller = new RegisterController(null);
        boolean result = controller.checkFields("", "", "", "");
        assertFalse(result);
    }
}
