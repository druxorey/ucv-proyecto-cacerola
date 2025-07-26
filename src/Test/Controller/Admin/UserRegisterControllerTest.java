package Test.Controller.Admin;

import Controller.Admin.UserRegisterController;
import View.Admin.UserRegisterView;
import org.junit.*;
import static org.junit.Assert.*;

public class UserRegisterControllerTest {

	@Test
	public void testConstructorSetsListener() {
		UserRegisterView view = new UserRegisterView("A", "B", "1", "a@b.com");
		UserRegisterController controller = new UserRegisterController(view);
		assertNotNull(controller);
	}

	@Test
	public void testProcessUserRegistrationWithInvalidFields() {
		UserRegisterView view = new UserRegisterView("", "", "", "");
		UserRegisterController controller = new UserRegisterController(view);
		// Simula click con campos vacíos
		try {
			view.addUserButton.doClick();
		} catch (Exception e) {
			fail("No debe lanzar excepción");
		}
	}
}