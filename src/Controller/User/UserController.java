package Controller.User;

import Controller.Common.ViewController;
import View.User.UserView;

public class UserController {
    private UserView userView;
	private ViewController viewController;

	public UserController(UserView userView, String firstName) {
		this.userView = userView;
		this.viewController = new ViewController();
		
		if (userView != null) {
			this.userView.logOutButton.addActionListener(_ -> viewController.goToLoginView(userView));
		}
	}
}
