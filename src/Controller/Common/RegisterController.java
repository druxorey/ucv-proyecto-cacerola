package Controller.Common;

import javax.swing.*;
import java.util.List;

import Model.Services.UserService;
import Model.Services.EmailSender;
import View.Start.RegisterView;


public class RegisterController {
	private RegisterView registerView;
	private static final String[] ALLOWED_DOMAINS = {"@gmail.com", "@ciens.ucv.ve", "@ucv.ve"};
	private static final String EMAIL_RECIPIENT = "guillermogalavisg@gmail.com";

	
	public boolean checkFields(String firstName, String lastName, String email, String userId) {
		if (email.isEmpty() || userId.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
			System.err.println("[RegisterController] Registration failed: One or more fields are empty. Email: '" + email + "', UserID: '" + userId + "'");
			JOptionPane.showMessageDialog(registerView, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
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
					String firstName = new String(registerView.firstNameField.getText());
					String lastName = new String(registerView.lastNameField.getText());
					String email = registerView.emailField.getText();
					String userId = registerView.userIdField.getText();
					int userCount = new UserService().getUserCount();

					if (!checkFields(firstName, lastName, email, userId)) {
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

			this.registerView.returnButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("[RegisterController] Returning to login view.");
					registerView.dispose();
					View.Start.LoginView view = new View.Start.LoginView();
					Model.Services.UserService service = new Model.Services.UserService();
					new Controller.Common.LoginController(view, service);
					view.setVisible(true);
				}
			});
		}
	}
}
