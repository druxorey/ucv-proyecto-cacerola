package Controller.Start;

import javax.swing.*;

import Model.Services.UserService;
import View.Start.RegisterView;
import Controller.Common.ViewController;
import Model.Services.RegisterService;

public class RegisterController {
	private RegisterView registerView;
	private ViewController viewController;


	public RegisterController(RegisterView registerView) {
		this.registerView = registerView;
		this.viewController = new ViewController();

		if (registerView != null) {
			this.registerView.submitRegistrationButton.addActionListener(_ -> submitRegistrationRequest());
			this.registerView.returnButton.addActionListener(_ -> viewController.goToLoginView(registerView));
		}
	}


	private void submitRegistrationRequest() {
		String firstName = registerView.firstNameField.getText();
		String lastName = registerView.lastNameField.getText();
		String email = registerView.emailField.getText();
		String userId = registerView.userIdField.getText();

		if (!UserService.validateRegistrationFields(registerView, firstName, lastName, email, userId)) {
			System.err.println("[RegisterController] Registration request failed due to validation errors.");
			return;
		}

		try {
			RegisterService.saveRegistrationRequest(firstName, lastName, email, userId);
			System.out.println("[RegisterController] Registration request saved successfully for UserID: '" + userId + "'. Email: '" + email + "'.");
			JOptionPane.showMessageDialog(registerView, "¡Solicitud guardada correctamente!", "Registro", JOptionPane.INFORMATION_MESSAGE);
			viewController.goToLoginView(registerView);
		} catch (Exception e1) {
			System.err.println("[RegisterController] Error saving registration request for UserID: '" + userId + "'. Email: '" + email + "'. Error: " + e1.getMessage());
			JOptionPane.showMessageDialog(registerView, "Error al guardar la solicitud: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	}
}