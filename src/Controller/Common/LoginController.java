package Controller.Common;

import Model.Services.UserService;
import View.Start.LoginView;

import javax.swing.*;
import java.awt.event.*;

public class LoginController {
	private LoginView view;
	private UserService service;

	public LoginController(LoginView view, UserService service) {
		this.view = view;
		this.service = service;
		initController();
	}

	private void initController() {
		view.loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = view.userIdField.getText();
				String password = new String(view.passwordField.getPassword());

				if (userId.isEmpty() || password.isEmpty()) {
					System.err.println("[LoginController] Login attempt failed: One or more fields are empty.");
					JOptionPane.showMessageDialog(view, "Por favor, complete todos los campos");
					return;
				}

				int authenticated = service.authenticate(userId, password);

				if (authenticated == 1) {
					System.out.println("[LoginController] User login successful. UserID: '" + userId + "' (Role: User)");
					JOptionPane.showMessageDialog(view, "Login exitoso");
				} else if (authenticated == 2) {
					System.out.println("[LoginController] Admin login successful. UserID: '" + userId + "' (Role: Admin)");
					JOptionPane.showMessageDialog(view, "Login exitoso como administrador");
				} else {
					System.err.println("[LoginController] Login failed: Incorrect ID or password.");
					JOptionPane.showMessageDialog(view, "Cédula o contraseña incorrectos");
				}
			}
		});

		view.registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("[LoginController] Opening registration view for new user.");
				View.Start.RegisterView registerView = new View.Start.RegisterView();
				RegisterController registerController = new RegisterController(registerView);
				registerView.setVisible(true);
			}
		});
	}
}
