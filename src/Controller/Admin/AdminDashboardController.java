package Controller.Admin;

import java.time.LocalDate;

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

		LocalDate today = LocalDate.now();
		LocalDate sunday = today.with(java.time.DayOfWeek.SUNDAY);
		LocalDate friday = sunday.plusDays(5);
		String currentWeekKey = sunday + "->" + friday;
		var weeks = operationalCostsService.getAllWeeks();
		if (!weeks.contains(currentWeekKey)) {
			operationalCostsService.saveOperationalCostsForWeek(
				sunday, friday, 0, 0, 0, 0, 0, 0, 0
			);
		}

		if (adminView != null) {
			this.adminView.logOutButton.addActionListener(_ -> viewController.goToLoginView(adminView));
			this.adminView.saveButton.addActionListener(_ -> saveOperationalCosts());
		}
	}

	
	private void saveOperationalCosts() {
		try {
			if (adminView.totalFixedCostsField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(adminView, "El campo 'Costos Fijos' está vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (adminView.variableCostsField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(adminView, "El campo 'Costos Variables' está vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (adminView.numberOfTraysField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(adminView, "El campo 'Número de Bandejas' está vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (adminView.wastePercentageField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(adminView, "El campo 'Porcentaje de Desperdicio' está vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (adminView.studentPctField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(adminView, "El campo 'Porcentaje Estudiante' está vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (adminView.professorPctField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(adminView, "El campo 'Porcentaje Profesor' está vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (adminView.employeePctField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(adminView, "El campo 'Porcentaje Empleado' está vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				return;
			}

			LocalDate weekStart = adminView.selectedWeekStart;
			LocalDate weekEnd = adminView.selectedWeekEnd;
			double totalFixed = Double.parseDouble(adminView.totalFixedCostsField.getText());
			double variable = Double.parseDouble(adminView.variableCostsField.getText());
			int trays = Integer.parseInt(adminView.numberOfTraysField.getText());
			double waste = Double.parseDouble(adminView.wastePercentageField.getText());
			double studentPct = Double.parseDouble(adminView.studentPctField.getText());
			double professorPct = Double.parseDouble(adminView.professorPctField.getText());
			double employeePct = Double.parseDouble(adminView.employeePctField.getText());

			if (studentPct < 20.0 || studentPct > 30.0) {
				JOptionPane.showMessageDialog(adminView, "El porcentaje de estudiantes debe estar entre 20.0 y 30.0.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (professorPct < 70.0 || professorPct > 90.0) {
				JOptionPane.showMessageDialog(adminView, "El porcentaje de profesores debe estar entre 70.0 y 90.0.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (employeePct < 90.0 || employeePct > 110.0) {
				JOptionPane.showMessageDialog(adminView, "El porcentaje de empleados debe estar entre 90.0 y 100.0.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			boolean ok = operationalCostsService.saveOperationalCostsForWeek(
				weekStart, weekEnd, totalFixed, variable, trays, waste, studentPct, professorPct, employeePct
			);
			if (ok) JOptionPane.showMessageDialog(adminView, "Costos guardados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			else JOptionPane.showMessageDialog(adminView, "Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(adminView, "Datos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	public void openUserRegisterView(String firstName, String lastName, String userId, String email) {
		UserRegisterView registerView = new UserRegisterView(firstName, lastName, userId, email);
		new UserRegisterController(registerView);
		registerView.setVisible(true);
	}
}