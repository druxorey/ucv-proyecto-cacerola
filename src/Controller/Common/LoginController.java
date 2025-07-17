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
					JOptionPane.showMessageDialog(view, "Por favor, complete todos los campos");
					return;
				}

				int authenticated = service.authenticate(userId, password);

				if (authenticated == 1) {
					JOptionPane.showMessageDialog(view, "Login exitoso");
				} else if (authenticated == 2) {
					view.dispose();
					View.Admin.AdminView adminView = new View.Admin.AdminView();
					Controller.Admin.AdminController adminController = new Controller.Admin.AdminController(adminView);
					adminView.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(view, "Cédula o contraseña incorrectos");
				}
			}
		});

		view.registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View.Start.RegisterView registerView = new View.Start.RegisterView();
				RegisterController registerController = new RegisterController(registerView);
				registerView.setVisible(true);
			}
		});
	}
}
