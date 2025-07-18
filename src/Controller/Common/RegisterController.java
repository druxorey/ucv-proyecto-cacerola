package Controller.Common;

import javax.swing.*;

import Model.Services.EmailSender;
import Model.Services.UserService;
import View.Start.RegisterView;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController {
	private RegisterView registerView;
	private static final String[] ALLOWED_DOMAINS = {"@gmail.com", "@ciens.ucv.ve", "@ucv.ve"};
	private static final String EMAIL_RECIPIENT = "guillermogalavisg@gmail.com";

	public boolean checkFields(String email, String userId, String password, String confirmPassword) {
		if (email.isEmpty() || userId.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
			System.err.println("[RegisterController] Registration failed: One or more fields are empty. Email: '" + email + "', UserID: '" + userId + "'");
			JOptionPane.showMessageDialog(registerView, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!password.equals(confirmPassword)) {
			System.err.println("[RegisterController] Registration failed: Passwords do not match. UserID: '" + userId + "'");
			JOptionPane.showMessageDialog(registerView, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			System.err.println("[RegisterController] Registration failed: Invalid email format. Email: '" + email + "'");
			JOptionPane.showMessageDialog(registerView, "Correo electrónico inválido.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		boolean validDomain = false;
		for (String domain : ALLOWED_DOMAINS) {
			if (email.endsWith(domain)) {
				validDomain = true;
				break;
			}
		}

		if (!validDomain) {
			System.err.println("[RegisterController] Registration failed: Email domain not allowed. Email: '" + email + "'");
			JOptionPane.showMessageDialog(registerView, "Solo se permiten correos de los dominios: @gmail.com, @ciens.ucv.ve, @ucv.ve", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!userId.matches("\\d{7,8}")) {
			System.err.println("[RegisterController] Registration failed: Invalid ID format. UserID: '" + userId + "'");
			JOptionPane.showMessageDialog(registerView, "Cédula de identidad inválida. Debe tener 7 u 8 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (password.length() < 8) {
			System.err.println("[RegisterController] Registration failed: Password too short. UserID: '" + userId + "'");
			JOptionPane.showMessageDialog(registerView, "La contraseña debe tener al menos 8 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		List<String> existingUserIds = new UserService().getUserIds();
		if (existingUserIds.contains(userId)) {
			System.err.println("[RegisterController] Registration failed: UserID already exists. UserID: '" + userId + "'");
			JOptionPane.showMessageDialog(registerView, "Ya existe un usuario con esa Cédula.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	public RegisterController(RegisterView registerView) {
		this.registerView = registerView;
		if (registerView != null) {
			this.registerView.submitRegistrationButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String email = registerView.emailField.getText();
					String userId = registerView.userIdField.getText();
					String password = new String(registerView.passwordField.getPassword());
					String confirmPassword = new String(registerView.confirmPasswordField.getPassword());
					int userCount = new UserService().getUserCount();

					if (!checkFields(email, userId, password, confirmPassword)) {
						return;
					}

					String message = String.format(
						"Correo: %s\nCédula: %s\nContraseña: %s", userCount,
						email, userId, password
					);

					String subject = String.format("Solicitud de Registro #%d", userCount);

					try {
						EmailSender.sendEmail(
							 EMAIL_RECIPIENT,
							 subject,
							 message
						);
						System.out.println("[RegisterController] Registration request sent successfully for UserID: '" + userId + "'. Email: '" + email + "'.\n" + message);
						JOptionPane.showMessageDialog(registerView, "¡Solicitud enviada correctamente!", "Registro", JOptionPane.INFORMATION_MESSAGE);
						registerView.dispose();
					} catch (Exception e1) {
						System.err.println("[RegisterController] Error sending registration request for UserID: '" + userId + "'. Email: '" + email + "'. Error: " + e1.getMessage());
						JOptionPane.showMessageDialog(registerView, "Error al enviar la solicitud: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			});
		}
	}
}
