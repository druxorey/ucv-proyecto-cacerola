package Controller.Start;

import javax.swing.*;

import Model.Services.UserService;
import View.Start.LoginView;
import Controller.Common.ViewController;

public class LoginController {
	private LoginView loginView;
	private UserService userService;


	public LoginController(LoginView loginView) {
		this.loginView = loginView;
		this.userService = new UserService();

		if (loginView != null) {
			loginView.loginSubmitAction.addActionListener(_ -> validateUserCredentials());
			loginView.registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					ViewController viewController = new ViewController();
					viewController.goToRegistrationView(loginView);
				}
			});
		}
	}


	private void validateUserCredentials() {
		String userId = loginView.userIdField.getText();
		String password = new String(loginView.passwordField.getPassword());

		if (userId.isEmpty() && password.isEmpty()) {
			System.err.println("[LoginController] Login attempt failed: One or more fields are empty.");
			JOptionPane.showMessageDialog(loginView, "Por favor, complete todos los campos");
			return;
		}

		if (password.isEmpty()) {
			System.err.println("[LoginController] Login attempt failed: Password field is empty.");
			JOptionPane.showMessageDialog(loginView, "Por favor, coloque su contraseña");
			return;
		}

		if (userId.isEmpty()) {
			System.err.println("[LoginController] Login attempt failed: UserID field is empty.");
			JOptionPane.showMessageDialog(loginView, "Por favor, coloque su Cédula de Identidad");
			return;
		}

		short authenticated = userService.validateUserCredentials(userId, password);
		ViewController viewController = new ViewController();

		if (authenticated == 0) {
			System.out.println("[LoginController] Admin login successful. UserID: '" + userId + "' (Role: Admin)");
			viewController.goToAdminView(loginView);
			
		} else if (authenticated == 1 || authenticated == 2 || authenticated == 3) {
			System.out.println("[LoginController] User login successful. UserID: '" + userId + "' (Role: User)");
			userService.generateUserCache(userId);
			viewController.goToUserView(loginView, userId);

		} else {
			System.err.println("[LoginController] Login failed: Incorrect ID or password.");
			JOptionPane.showMessageDialog(loginView, "Cédula o contraseña incorrectos");
		}
	}
}