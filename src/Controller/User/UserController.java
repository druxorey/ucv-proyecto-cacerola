package Controller.User;

import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Model.Services.EmailSender;
import Model.Services.UserService;
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
