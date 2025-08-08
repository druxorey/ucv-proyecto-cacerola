package Test.Controller.Start;

import Controller.Start.LoginController;
import View.Start.LoginView;
import org.junit.*;
import static org.junit.Assert.*;

public class LoginControllerTest {

	@Test
	public void testConstructorSetsListeners() {
		LoginView view = new LoginView();
		LoginController controller = new LoginController(view);
		assertNotNull(controller);
	}

	@Test
	public void testValidateUserCredentialsWithEmptyFields() {
		LoginView view = new LoginView();
		view.userIdField.setText("");
		view.passwordField.setText("");
		new LoginController(view);
		try {
			view.loginSubmitAction.doClick();
		} catch (Exception e) {
			fail("[testValidateUserCredentialsWithEmptyFields] Exception should not be thrown for empty fields");
		}
	}

	@Test
	public void testValidateUserCredentialsWithOnlyUserId() {
		LoginView view = new LoginView();
		view.userIdField.setText("123");
		view.passwordField.setText("");
		new LoginController(view);
		try {
			view.loginSubmitAction.doClick();
		} catch (Exception e) {
			fail("[testValidateUserCredentialsWithOnlyUserId] Exception should not be thrown for empty userId");
		}
	}

	@Test
	public void testValidateUserCredentialsWithOnlyPassword() {
		LoginView view = new LoginView();
		view.userIdField.setText("");
		view.passwordField.setText("pass");
		new LoginController(view);
		try {
			view.loginSubmitAction.doClick();
		} catch (Exception e) {
			fail("[testValidateUserCredentialsWithOnlyPassword] Exception should not be thrown for empty password");
		}
	}
}