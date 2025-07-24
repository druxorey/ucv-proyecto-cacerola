
package Controller.Admin;
import Controller.Common.RegisterController;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import View.Admin.AdminView;
import Model.Entities.User;
import Model.Services.UserService;

public class AdminController {
	private AdminView adminView;
	private UserService userService;

	public AdminController(AdminView adminView) {
		this.adminView = adminView;
		this.userService = new UserService();

		if (adminView != null) {
			this.adminView.logOutButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("[UserController] Returning to login view.");
					adminView.dispose();
					View.Start.LoginView view = new View.Start.LoginView();
					Model.Services.UserService service = new Model.Services.UserService();
					new Controller.Common.LoginController(view, service);
					view.setVisible(true);
				}
			});
		}
	}

	public boolean addUser(String userId, String password, int type, String email, String firstName, String lastName) {
		RegisterController registerController = new RegisterController(null);
		boolean valid = registerController.checkFields(email, userId, password, password);
		if (!valid) {
			return false;
		}
		User user = new User(userId, password, type, email, firstName, lastName);
		return userService.addUserToDatabase(user);
	}

	public void handleAddUser(JTextField firstNameField, JTextField lastNameField, JTextField userIdField, JPasswordField passwordField, JTextField emailField, JComboBox<String> userType) {
		String firstName = firstNameField.getText().trim();
		String lastName = lastNameField.getText().trim();
		String userId = userIdField.getText().trim();
		String password = new String(passwordField.getPassword());
		String email = emailField.getText().trim();
		int type = userType.getSelectedIndex() + 1;

		if (firstName.isEmpty() || lastName.isEmpty() || userId.isEmpty() || password.isEmpty() || email.isEmpty()) {
			JOptionPane.showMessageDialog(adminView, "Todos los campos son obligatorios.");
			return;
		}

		boolean success = addUser(userId, password, type, email, firstName, lastName);
		if (success) {
			JOptionPane.showMessageDialog(adminView, "Usuario agregado correctamente.");
			firstNameField.setText("");
			lastNameField.setText("");
			userIdField.setText("");
			passwordField.setText("");
			emailField.setText("");
			userType.setSelectedIndex(0);
		} else {
			JOptionPane.showMessageDialog(adminView, "Error al agregar usuario.");
		}
	}
}
