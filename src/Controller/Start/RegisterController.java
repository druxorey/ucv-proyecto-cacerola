package Controller.Start;

import javax.swing.*;

import Model.Services.UserService;
import Model.Services.EmailService;
import View.Start.RegisterView;
import Controller.Common.ViewController;

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
		int userCount = new UserService().getUserCount();

		if (!UserService.validateRegistrationFields(registerView, firstName, lastName, email, userId)) {
			System.err.println("[RegisterController] Registration request failed due to validation errors.");
			return;
		}

		String message = String.format(
			"Se ha recibido una nueva solicitud de registro en el sistema MiComedorUCV.\n\n"
				+ "Datos del solicitante:\n"
				+ "Nombre: %s\n"
				+ "Apellido: %s\n"
				+ "Correo electrónico: %s\n"
				+ "Cédula de Identidad: %s\n",
			firstName, lastName, email, userId
		);

		String subject = String.format("Solicitud de Registro #%d", userCount);

		try {
			EmailService.sendEmail(subject, message);

			System.out.println("[RegisterController] Registration request sent successfully for UserID: '" + userId + "'. Email: '" + email + "'.\n" + message);
			JOptionPane.showMessageDialog(registerView, "¡Solicitud enviada correctamente!", "Registro", JOptionPane.INFORMATION_MESSAGE);
			registerView.dispose();
		} catch (Exception e1) {
			System.err.println("[RegisterController] Error sending registration request for UserID: '" + userId + "'. Email: '" + email + "'. Error: " + e1.getMessage());
			JOptionPane.showMessageDialog(registerView, "Error al enviar la solicitud: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	}
}