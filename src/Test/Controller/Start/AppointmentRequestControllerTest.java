package Test.Controller.Start;

import Controller.Start.AppointmentRequestController;
import View.Start.AppointmentRequestView;
import org.junit.*;
import static org.junit.Assert.*;

public class AppointmentRequestControllerTest {

	@Test
	public void testConstructorSetsListeners() {
		AppointmentRequestView view = new AppointmentRequestView();
		AppointmentRequestController controller = new AppointmentRequestController(view);
		assertNotNull(controller);
	}

	@Test
	public void testSubmitRegistrationRequestWithEmptyFirstName() {
		AppointmentRequestView view = new AppointmentRequestView();
		view.firstNameField.setText("");
		view.lastNameField.setText("Doe");
		view.emailField.setText("john@doe.com");
		view.userIdField.setText("jdoe");
		new AppointmentRequestController(view);

		try {
			view.submitRegistrationButton.doClick();
		} catch (Exception e) {
			fail("[testSubmitRegistrationRequestWithEmptyFirstName] Exception should not be thrown for empty first name");
		}
	}

	@Test
	public void testSubmitRegistrationRequestWithEmptyLastName() {
		AppointmentRequestView view = new AppointmentRequestView();
		view.firstNameField.setText("John");
		view.lastNameField.setText("");
		view.emailField.setText("john@doe.com");
		view.userIdField.setText("jdoe");
		new AppointmentRequestController(view);

		try {
			view.submitRegistrationButton.doClick();
		} catch (Exception e) {
			fail("[testSubmitRegistrationRequestWithEmptyLastName] Exception should not be thrown for empty last name");
		}
	}

	@Test
	public void testSubmitRegistrationRequestWithEmptyEmail() {
		AppointmentRequestView view = new AppointmentRequestView();
		view.firstNameField.setText("John");
		view.lastNameField.setText("Doe");
		view.emailField.setText("");
		view.userIdField.setText("jdoe");
		new AppointmentRequestController(view);

		try {
			view.submitRegistrationButton.doClick();
		} catch (Exception e) {
			fail("[testSubmitRegistrationRequestWithEmptyEmail] Exception should not be thrown for empty email");
		}
	}

	@Test
	public void testSubmitRegistrationRequestWithEmptyUserId() {
		AppointmentRequestView view = new AppointmentRequestView();
		view.firstNameField.setText("John");
		view.lastNameField.setText("Doe");
		view.emailField.setText("john@doe.com");
		view.userIdField.setText("");
		new AppointmentRequestController(view);

		try {
			view.submitRegistrationButton.doClick();
		} catch (Exception e) {
			fail("[testSubmitRegistrationRequestWithEmptyUserId] Exception should not be thrown for empty userId");
		}
	}

	@Test
	public void testSubmitRegistrationRequestWithAllFieldsEmpty() {
		AppointmentRequestView view = new AppointmentRequestView();
		view.firstNameField.setText("");
		view.lastNameField.setText("");
		view.emailField.setText("");
		view.userIdField.setText("");
		new AppointmentRequestController(view);

		try {
			view.submitRegistrationButton.doClick();
		} catch (Exception e) {
			fail("[testSubmitRegistrationRequestWithAllFieldsEmpty] Exception should not be thrown for all fields empty");
		}
	}
}