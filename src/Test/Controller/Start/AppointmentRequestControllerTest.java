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
	public void testSubmitRegistrationRequestWithInvalidFields() {
		AppointmentRequestView view = new AppointmentRequestView();
		view.firstNameField.setText("");
		view.lastNameField.setText("");
		view.emailField.setText("");
		view.userIdField.setText("");
		AppointmentRequestController controller = new AppointmentRequestController(view);
		try {
			view.submitRegistrationButton.doClick();
		} catch (Exception e) {
			fail("No debe lanzar excepción");
		}
	}
}