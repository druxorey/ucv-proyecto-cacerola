package Controller.Common;

import javax.swing.JFrame;

import Controller.Admin.AdminDashboardController;
import Controller.Start.AppointmentRequestController;
import Controller.User.UserDashboardController;
import View.Admin.AdminDashboardView;
import View.User.UserDashboardView;

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
		View.Start.AppointmentRequestView registerView = new View.Start.AppointmentRequestView();
		new AppointmentRequestController(registerView);
		registerView.setVisible(true);
	}


    public void goToUserView(JFrame currentView, String userId) {
        System.out.println("[ViewController] Opening user view for: " + userId);
        currentView.dispose();
        UserDashboardView userView = new UserDashboardView(userId);
        new UserDashboardController(userView, userId);
        userView.setVisible(true);
    }


    public void goToAdminView(JFrame currentView) {
        System.out.println("[ViewController] Opening admin view.");
        currentView.dispose();
        AdminDashboardView adminView = new AdminDashboardView();
        new AdminDashboardController(adminView);
        adminView.setVisible(true);
    }
}