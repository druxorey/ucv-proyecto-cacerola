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

				if (service.authenticate(userId, password)) {
					JOptionPane.showMessageDialog(view, "Login exitoso");
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
