package View.Start;

import javax.swing.*;
import java.awt.*;
import View.Common.UIStyles;
import View.Common.UIElements;

public class RegisterView extends JFrame {
	public static final int WINDOW_WIDTH = 500;
    public static final int WINDOW_HEIGHT = 700;

	public JTextField emailField;
	public JTextField userIdField;
	public JPasswordField passwordField;
	public JPasswordField confirmPasswordField;
	public JButton submitRegistrationButton;

	public RegisterView() {
		setTitle("Registro de Usuario");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setBorder(BorderFactory.createEmptyBorder(UIStyles.PANEL_PADDING, UIStyles.PANEL_PADDING * 2, UIStyles.PANEL_PADDING, UIStyles.PANEL_PADDING * 2));
		formPanel.setBackground(UIStyles.BG_PRIMARY_COLOR);
		
		formPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		JLabel registerTitle = new JLabel("Solicitud de Registro");
		registerTitle.setFont(UIStyles.TITLE_FONT);
		registerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		formPanel.add(registerTitle);
		formPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));

		JLabel infoLabel = new JLabel("<html>Esta es una solicitud para crear una cuenta en el sistema. Una vez enviada, será revisada por el departamento de registro. Si es aprobada, recibirás un correo con la hora de tu cita para completar el registro físico.</html>");
		infoLabel.setFont(UIStyles.FIELD_FONT);
		infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoLabel.setForeground(Color.DARK_GRAY);
		formPanel.add(infoLabel);
		formPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM + 10));

		emailField = (JTextField) UIElements.createInputField(false, null);
		userIdField = (JTextField) UIElements.createInputField(false, null);
		passwordField = (JPasswordField) UIElements.createInputField(true, null);
		confirmPasswordField = (JPasswordField) UIElements.createInputField(true, null);

		addField(formPanel, "Correo Electrónico", emailField,
			"<html>Debe ser un correo @gmail.com, institucional (@ucv.ve) o de facultad (@ciens.ucv.ve, etc.)</html>");
		addField(formPanel, "Cédula de Identidad", userIdField,
			"<html>Ingrese su número de cédula sin puntos ni guiones. Ejemplo: 12345678</html>");
		addField(formPanel, "Contraseña", passwordField,
			"<html>Debe tener al menos 8 caracteres, incluir letras y números.</html>");
		addField(formPanel, "Confirmar Contraseña", confirmPasswordField,
			"<html>Repita la contraseña para confirmar.</html>");

		formPanel.add(Box.createVerticalStrut(20));
		submitRegistrationButton = UIElements.createStyledButton(
			"Enviar Solicitud",
			UIStyles.ACCENT_COLOR,
			Color.WHITE,
			false
		);
		submitRegistrationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		formPanel.add(submitRegistrationButton);

		add(formPanel, BorderLayout.CENTER);
	}

	private void addField(JPanel panel, String labelText, JComponent field, String helpText) {
		JLabel label = new JLabel(labelText);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setFont(new Font("Segoe UI", Font.BOLD, 15));
		panel.add(label);

		panel.add(field);

		JLabel helpLabel = new JLabel(helpText);
		helpLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		helpLabel.setForeground(Color.GRAY);
		helpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(helpLabel);
		panel.add(Box.createVerticalStrut(10));
	}
}
