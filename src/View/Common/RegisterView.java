package View.Common;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {
	public JTextField emailField;
	public JTextField userIdField;
	public JPasswordField passwordField;
	public JPasswordField confirmPasswordField;
	public JButton submitRegistrationButton;

	public RegisterView() {
		setTitle("Registro de Usuario");
		setSize(500, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
		formPanel.setBackground(UIColors.BG_PRIMARY_COLOR);

		JLabel registerTitle = new JLabel("Solicitud de Registro");
		registerTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
		registerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		formPanel.add(registerTitle);
		formPanel.add(Box.createVerticalStrut(10));

		JLabel infoLabel = new JLabel("<html>Esta es una solicitud para crear una cuenta en el sistema. Una vez enviada, será revisada por el departamento de registro. Si es aprobada, recibirás un correo con la hora de tu cita para completar el registro físico.</html>");
		infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoLabel.setForeground(Color.DARK_GRAY);
		formPanel.add(infoLabel);
		formPanel.add(Box.createVerticalStrut(20));

		emailField = new JTextField(20);
		userIdField = new JTextField(20);
		passwordField = new JPasswordField(20);
		confirmPasswordField = new JPasswordField(20);

		addField(formPanel, "Correo Electrónico", emailField,
			"<html>Debe ser un correo @gmail.com, institucional (@ucv.ve) o de facultad (@ciens.ucv.ve, etc.)</html>");
		addField(formPanel, "Cédula de Identidad", userIdField,
			"<html>Ingrese su número de cédula sin puntos ni guiones. Ejemplo: 12345678</html>");
		addField(formPanel, "Contraseña", passwordField,
			"<html>Debe tener al menos 8 caracteres, incluir letras y números.</html>");
		addField(formPanel, "Confirmar Contraseña", confirmPasswordField,
			"<html>Repita la contraseña para confirmar.</html>");

		formPanel.add(Box.createVerticalStrut(20));
		submitRegistrationButton = new JButton("Enviar Solicitud");
		submitRegistrationButton.setBackground(UIColors.ACCENT_COLOR);
		submitRegistrationButton.setForeground(UIColors.FG_SECONDARY_COLOR);
		submitRegistrationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		formPanel.add(submitRegistrationButton);

		add(formPanel, BorderLayout.CENTER);
	}

	private void addField(JPanel panel, String labelText, JComponent field, String helpText) {
		JLabel label = new JLabel(labelText);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setFont(new Font("Segoe UI", Font.BOLD, 15));
		panel.add(label);

		field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		panel.add(field);

		JLabel helpLabel = new JLabel(helpText);
		helpLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		helpLabel.setForeground(Color.GRAY);
		helpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(helpLabel);
		panel.add(Box.createVerticalStrut(10));
	}
}
