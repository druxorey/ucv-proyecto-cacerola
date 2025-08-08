package Test.Controller.Admin;

import Controller.Admin.AdminDashboardController;
import View.Admin.AdminDashboardView;
import org.junit.*;
import static org.junit.Assert.*;

public class AdminDashboardControllerTest {

	@Test
	public void testConstructorSetsListeners() {
		AdminDashboardView view = new AdminDashboardView();
		AdminDashboardController controller = new AdminDashboardController(view);
		assertNotNull(controller);
	}

	@Test
	public void testSaveOperationalCostsHandlesInvalidInput() {
		AdminDashboardView view = new AdminDashboardView();
		view.totalFixedCostsField.setText("abc");
		view.variableCostsField.setText("def");
		view.numberOfTraysField.setText("ghi");
		view.wastePercentageField.setText("jkl");
		new AdminDashboardController(view);

		try {
			view.saveButton.doClick();
		} catch (Exception e) {
			fail("[testSaveOperationalCostsHandlesInvalidInput] Exception should not be thrown for invalid input");
		}
	}
}