package Controller.Admin;

import javax.swing.*;

import Controller.Common.ViewController;
import View.Admin.AdminDashboardView;
import View.Admin.UserRegisterView;

public class AdminDashboardController {
	private AdminDashboardView adminView;
	private ViewController viewController;
	private Model.Services.OperationalCostsService operationalCostsService;

	public AdminDashboardController(AdminDashboardView adminView) {
		this.adminView = adminView;
		this.viewController = new ViewController();
		this.operationalCostsService = new Model.Services.OperationalCostsService();

		if (adminView != null) {
			this.adminView.logOutButton.addActionListener(_ -> viewController.goToLoginView(adminView));
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
			JOptionPane.showMessageDialog(adminView, "Verifica los valores ingresados.");
		}
	}

	public void openUserRegisterView(String firstName, String lastName, String userId, String email) {
		UserRegisterView registerView = new UserRegisterView(firstName, lastName, userId, email);
		new UserRegisterController(registerView);
		registerView.setVisible(true);
	}
}