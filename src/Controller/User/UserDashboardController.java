package Controller.User;

import Controller.Common.ViewController;
import View.User.UserDashboardView;

public class UserDashboardController {
	private UserDashboardView userView;
	private ViewController viewController;

	public UserDashboardController(UserDashboardView userView, String firstName) {
		this.userView = userView;
		this.viewController = new ViewController();
		
		if (userView != null) {
			this.userView.logOutButton.addActionListener(_ -> viewController.goToLoginView(userView));
		}
	}
}
