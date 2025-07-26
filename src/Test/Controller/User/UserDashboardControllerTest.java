package Test.Controller.User;

import Controller.User.UserDashboardController;
import View.User.UserDashboardView;
import org.junit.*;
import static org.junit.Assert.*;

public class UserDashboardControllerTest {

	@Test
	public void testConstructorSetsListener() {
		UserDashboardView view = new UserDashboardView("testuser");
		UserDashboardController controller = new UserDashboardController(view, "Test");
		assertNotNull(controller);
	}
}