
package Controller.Admin;
import Controller.Common.RegisterController;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import View.Admin.AdminView;
import Model.Entities.User;
import Model.Services.UserService;

public class AdminController {
	private AdminView view;
	private UserService userService;

	public AdminController(AdminView view) {
		this.view = view;
		this.userService = new UserService();
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
			JOptionPane.showMessageDialog(view, "Todos los campos son obligatorios.");
			return;
		}

		boolean success = addUser(userId, password, type, email, firstName, lastName);
		if (success) {
			JOptionPane.showMessageDialog(view, "Usuario agregado correctamente.");
			firstNameField.setText("");
			lastNameField.setText("");
			userIdField.setText("");
			passwordField.setText("");
			emailField.setText("");
			userType.setSelectedIndex(0);
		} else {
			JOptionPane.showMessageDialog(view, "Error al agregar usuario.");
		}
	}
}
