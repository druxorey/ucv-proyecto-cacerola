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
	public void testProcessUserRegistrationWithEmptyFirstName() {
		UserRegisterView view = new UserRegisterView("", "B", "1", "a@b.com");
		new UserRegisterController(view);
		try {
			view.addUserButton.doClick();
		} catch (Exception e) {
			fail("[testProcessUserRegistrationWithEmptyFirstName] Exception should not be thrown for empty first name");
		}
	}

	@Test
	public void testProcessUserRegistrationWithEmptyLastName() {
		UserRegisterView view = new UserRegisterView("A", "", "1", "a@b.com");
		new UserRegisterController(view);
		try {
			view.addUserButton.doClick();
		} catch (Exception e) {
			fail("[testProcessUserRegistrationWithEmptyLastName] Exception should not be thrown for empty last name");
		}
	}

	@Test
	public void testProcessUserRegistrationWithEmptyUserId() {
		UserRegisterView view = new UserRegisterView("A", "B", "", "a@b.com");
		new UserRegisterController(view);
		try {
			view.addUserButton.doClick();
		} catch (Exception e) {
			fail("[testProcessUserRegistrationWithEmptyUserId] Exception should not be thrown for empty userId");
		}
	}

	@Test
	public void testProcessUserRegistrationWithEmptyEmail() {
		UserRegisterView view = new UserRegisterView("A", "B", "1", "");
		new UserRegisterController(view);
		try {
			view.addUserButton.doClick();
		} catch (Exception e) {
			fail("[testProcessUserRegistrationWithEmptyEmail] Exception should not be thrown for empty email");
		}
	}

	@Test
	public void testProcessUserRegistrationWithAllFieldsEmpty() {
		UserRegisterView view = new UserRegisterView("", "", "", "");
		new UserRegisterController(view);
		try {
			view.addUserButton.doClick();
		} catch (Exception e) {
			fail("[testProcessUserRegistrationWithAllFieldsEmpty] Exception should not be thrown for all fields empty");
		}
	}
}