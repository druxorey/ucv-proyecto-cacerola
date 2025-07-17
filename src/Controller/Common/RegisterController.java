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
	private static final String[] ALLOWED_DOMAINS = {"@gmail.com", "@ciens.ucv.ve"};
	private static final String EMAIL_RECIPIENT = "guillermogalavisg@gmail.com";

	private boolean checkFields(String email, String userId, String password, String confirmPassword) {
		if (email.isEmpty() || userId.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
			JOptionPane.showMessageDialog(registerView, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!password.equals(confirmPassword)) {
			JOptionPane.showMessageDialog(registerView, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
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
			JOptionPane.showMessageDialog(registerView, "Solo se permiten correos de los dominios: @gmail.com y @ciens.ucv.ve", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!userId.matches("\\d{7,8}")) {
			JOptionPane.showMessageDialog(registerView, "Cédula de identidad inválida. Debe tener 7 u 8 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (password.length() < 8) {
			JOptionPane.showMessageDialog(registerView, "La contraseña debe tener al menos 8 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		List<String> existingUserIds = new UserService().getUserIds();
		if (existingUserIds.contains(userId)) {
			JOptionPane.showMessageDialog(registerView, "Ya existe un usuario con esa Cédula.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	public RegisterController(RegisterView registerView) {
		this.registerView = registerView;
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
					"Solicitud de Registro #%d\n\n" +
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
					System.out.println("Solicitud enviada correctamente:\n" + message);
					JOptionPane.showMessageDialog(registerView, "¡Solicitud enviada correctamente!", "Registro", JOptionPane.INFORMATION_MESSAGE);
					registerView.dispose();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(registerView, "Error al enviar la solicitud: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
	}
}
