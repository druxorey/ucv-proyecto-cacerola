package Controller.User;

import java.awt.event.*;

import View.User.UserView;

public class UserController {
    private UserView userView;
    private String userId;

    public UserController(UserView userView, String userId) {
		this.userView = userView;
		if (userView != null) {
			this.userView.logOutButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("[UserController] Returning to login view.");
					userView.dispose();
					View.Start.LoginView view = new View.Start.LoginView();
					Model.Services.UserService service = new Model.Services.UserService();
					new Controller.Common.LoginController(view, service);
					view.setVisible(true);
				}
			});
		}
	}
}
