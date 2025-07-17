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


	private JPanel createLeftPanel() {
		// Create the left panel with a vertical box layout
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING));
		leftPanel.setBackground(UIStyles.BG_SECONDARY_COLOR);
			
		// Add title for the registration view
		JLabel registerTitle = new JLabel("Solicitud de Registro");
		registerTitle.setFont(UIStyles.TITLE_FONT);
		registerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		registerTitle.setForeground(UIStyles.FG_SECONDARY_COLOR);
		
		// Add information label
		JLabel infoLabel = new JLabel("<html>Esta es una solicitud para crear una cuenta en el sistema. Una vez enviada, será revisada por el departamento de registro. Si es aprobada, recibirás un correo con la hora de tu cita para completar el registro físico.</html>");
		infoLabel.setFont(UIStyles.FIELD_FONT);
		infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoLabel.setForeground(UIStyles.FG_SECONDARY_COLOR);

		// Add components to the left panel
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		leftPanel.add(registerTitle);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		leftPanel.add(infoLabel);

		return leftPanel;
	}


	private JPanel createRightPanel() {
		// Create the right panel with a vertical box layout
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(
			UIStyles.PANEL_PADDING, 
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING));
		rightPanel.setBackground(UIStyles.BG_PRIMARY_COLOR);
		
		// Add fields for registration
		emailField = (JTextField) UIElements.createInputField(false, null);
		userIdField = (JTextField) UIElements.createInputField(false, null);
		passwordField = (JPasswordField) UIElements.createInputField(true, null);
		confirmPasswordField = (JPasswordField) UIElements.createInputField(true, null);

		// Add submit button
		submitRegistrationButton = UIElements.createButton(
			"Enviar Solicitud",
			UIStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			120
		);
		submitRegistrationButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Add fields and labels to the right panel
		rightPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		createRegistrationField(rightPanel, "Correo Electrónico", emailField, "<html>Debe ser un correo @gmail.com, institucional (@ucv.ve) o de facultad (@ciens.ucv.ve, etc.)</html>");
		createRegistrationField(rightPanel, "Cédula de Identidad", userIdField, "<html>Ingrese su número de cédula sin puntos ni guiones. Ejemplo: 12345678</html>");
		createRegistrationField(rightPanel, "Contraseña", passwordField, "<html>Debe tener al menos 8 caracteres, incluir letras y números.</html>");
		createRegistrationField(rightPanel, "Confirmar Contraseña", confirmPasswordField, "<html>Repita la contraseña para confirmar.</html>");
		rightPanel.add(submitRegistrationButton);

		return rightPanel;
	}


	private void createRegistrationField(JPanel panel, String labelText, JComponent field, String helpText) {
		JLabel label = new JLabel(labelText);
		label.setFont(UIStyles.MAIN_FONT);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel helpLabel = new JLabel(helpText);
		helpLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		helpLabel.setForeground(Color.GRAY);
		helpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(label);
		panel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_SMALL));
		panel.add(field);
		panel.add(helpLabel);
		panel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
	}


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


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			RegisterView registerView = new RegisterView();
			registerView.setVisible(true);
		});
	}
}
