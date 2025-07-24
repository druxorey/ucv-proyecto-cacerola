package Controller.Common;

import javax.swing.JFrame;

import Controller.Admin.AdminController;
import Controller.Start.RegisterController;
import Controller.User.UserController;
import View.Admin.AdminView;
import View.User.UserView;

public class ViewController {

    public void goToLoginView(JFrame currentView) {
        System.out.println("[ViewController] Returning to login view.");
        currentView.dispose();
        View.Start.LoginView view = new View.Start.LoginView();
        new Controller.Start.LoginController(view);
        view.setVisible(true);
    }


	public void goToRegistrationView(JFrame currentView) {
		System.out.println("[ViewController] Opening registration view.");
		currentView.dispose();
		View.Start.RegisterView registerView = new View.Start.RegisterView();
		new RegisterController(registerView);
		registerView.setVisible(true);
	}


    public void goToUserView(JFrame currentView, String userId) {
        System.out.println("[ViewController] Opening user view for: " + userId);
        currentView.dispose();
        UserView userView = new UserView(userId);
        new UserController(userView, userId);
        userView.setVisible(true);
    }


    public void goToAdminView(JFrame currentView) {
        System.out.println("[ViewController] Opening admin view.");
        currentView.dispose();
        AdminView adminView = new AdminView();
        new AdminController(adminView);
        adminView.setVisible(true);
    }
}