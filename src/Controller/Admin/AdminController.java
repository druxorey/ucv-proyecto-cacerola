package Controller.Admin;

import javax.swing.*;

import Model.Entities.User;
import Model.Services.UserService;
import View.Admin.AdminView;
import Controller.Common.ViewController;

public class AdminController {
	private AdminView adminView;
	private UserService userService;
	private ViewController viewController;
	private Model.Services.OperationalCostsService operationalCostsService;


	public AdminController(AdminView adminView) {
		this.adminView = adminView;
		this.userService = new UserService();
		this.viewController = new ViewController();
		this.operationalCostsService = new Model.Services.OperationalCostsService();

		if (adminView != null) {
			this.adminView.logOutButton.addActionListener(_ -> viewController.goToLoginView(adminView));
			// this.adminView.addUserButton.addActionListener(_ -> processUserRegistration());
			this.adminView.saveButton.addActionListener(_ -> saveOperationalCosts());
		}
	}


	private void saveOperationalCosts() {
		try {
			double totalFixedCosts = Double.parseDouble(adminView.totalFixedCostsField.getText());
			double variableCosts = Double.parseDouble(adminView.variableCostsField.getText());
			int numberOfTrays = Integer.parseInt(adminView.numberOfTraysField.getText());
			double wastePercentage = Double.parseDouble(adminView.wastePercentageField.getText());

			boolean ok = operationalCostsService.saveOperationalCosts(totalFixedCosts, variableCosts, numberOfTrays, wastePercentage);
			if (ok) {
				JOptionPane.showMessageDialog(adminView, "Costos guardados correctamente.");
			} else {
				JOptionPane.showMessageDialog(adminView, "Error al guardar los costos.");
			}
		} catch (Exception ex) {
			System.err.println("[AdminController] Error parsing operational costs: " + ex.getMessage());
			ex.printStackTrace();
			JOptionPane.showMessageDialog(adminView, "Verifica los valores ingresados.");
		}
	}


	public void processUserRegistration() {
		String firstName = adminView.firstNameField.getText().trim();
		String lastName = adminView.lastNameField.getText().trim();
		String userId = adminView.userIdField.getText().trim();
		String password = new String(adminView.passwordField.getPassword());
		String email = adminView.emailField.getText().trim();
		int type = adminView.userTypeComboBox.getSelectedIndex() + 1;

		if (firstName.isEmpty() || lastName.isEmpty() || userId.isEmpty() || password.isEmpty() || email.isEmpty()) {
			JOptionPane.showMessageDialog(adminView, "Todos los campos son obligatorios.");
			return;
		}

		boolean valid = UserService.validateRegistrationFields(adminView, firstName, lastName, email, userId);
		if (!valid) {
			return;
		}

		User user = new User(userId, password, type, email, firstName, lastName);
		boolean success = userService.addUserToDatabase(user);

		if (success) {
			JOptionPane.showMessageDialog(adminView, "Usuario agregado correctamente.");
			adminView.firstNameField.setText("");
			adminView.lastNameField.setText("");
			adminView.userIdField.setText("");
			adminView.passwordField.setText("");
			adminView.emailField.setText("");
			adminView.userTypeComboBox.setSelectedIndex(0);
		} else {
			JOptionPane.showMessageDialog(adminView, "Error al agregar usuario.");
		}
	}
}