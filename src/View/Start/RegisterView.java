package View.Start;

import javax.swing.*;
import java.awt.*;
import View.Common.*;

public class RegisterView extends JFrame {
	public JTextField emailField;
	public JTextField userIdField;
	public JPasswordField passwordField;
	public JPasswordField confirmPasswordField;
	public JButton submitRegistrationButton;

	public RegisterView() {
		setTitle("Registro de Usuario");
		setSize(UIStyles.WINDOW_WIDTH_LOGIN, UIStyles.WINDOW_HEIGHT_LOGIN);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel leftPanel = createLeftPanel();
		JPanel rightPanel = createRightPanel();

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setEnabled(false);
		splitPane.setDividerSize(0);
		splitPane.setBorder(BorderFactory.createEmptyBorder());
		add(splitPane);

		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				int w = getWidth();
				double relation = 0.40; // 40% width for left panel
				int divider = (int) (w * relation);
				splitPane.setDividerLocation(divider);
			}
		});
	}

	private JPanel createLeftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING));
		leftPanel.setBackground(UIStyles.BG_SECONDARY_COLOR);

		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		JLabel registerTitle = new JLabel("Solicitud de Registro");
		registerTitle.setFont(UIStyles.TITLE_FONT);
		registerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		registerTitle.setForeground(UIStyles.FG_SECONDARY_COLOR);
		leftPanel.add(registerTitle);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));

		JLabel infoLabel = new JLabel("<html>Esta es una solicitud para crear una cuenta en el sistema. Una vez enviada, será revisada por el departamento de registro. Si es aprobada, recibirás un correo con la hora de tu cita para completar el registro físico.</html>");
		infoLabel.setFont(UIStyles.FIELD_FONT);
		infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoLabel.setForeground(UIStyles.FG_SECONDARY_COLOR);
		leftPanel.add(infoLabel);

		return leftPanel;
	}

	private JPanel createRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(
			UIStyles.PANEL_PADDING, 
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING));
		rightPanel.setBackground(UIStyles.BG_PRIMARY_COLOR);

		emailField = (JTextField) UIElements.createInputField(false, null);
		userIdField = (JTextField) UIElements.createInputField(false, null);
		passwordField = (JPasswordField) UIElements.createInputField(true, null);
		confirmPasswordField = (JPasswordField) UIElements.createInputField(true, null);

		addField(rightPanel, "Correo Electrónico", emailField, "<html>Debe ser un correo @gmail.com, institucional (@ucv.ve) o de facultad (@ciens.ucv.ve, etc.)</html>");
		addField(rightPanel, "Cédula de Identidad", userIdField, "<html>Ingrese su número de cédula sin puntos ni guiones. Ejemplo: 12345678</html>");
		addField(rightPanel, "Contraseña", passwordField, "<html>Debe tener al menos 8 caracteres, incluir letras y números.</html>");
		addField(rightPanel, "Confirmar Contraseña", confirmPasswordField, "<html>Repita la contraseña para confirmar.</html>");

		rightPanel.add(Box.createVerticalStrut(20));
		submitRegistrationButton = UIElements.createStyledButton(
			"Enviar Solicitud",
			UIStyles.ACCENT_COLOR,
			Color.WHITE,
			false
		);
		submitRegistrationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(submitRegistrationButton);

		return rightPanel;
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
