package View.Start;

import javax.swing.*;
import java.awt.*;
import View.Common.*;

public class RegisterView extends JFrame {
	public JTextField firstNameField;
	public JTextField lastNameField;
	public JTextField emailField;
	public JTextField userIdField;
	public JButton submitRegistrationButton;


	private JPanel createLeftPanel() {
		// Create the left panel with a vertical box layout
		JPanel leftPanel = CRElements.createPanel(CRStyles.BG_DARK_COLOR, BoxLayout.Y_AXIS);
			
		// Add title for the registration view
		JLabel registerTitle = new JLabel("<html>Solicitud de<br>Registro</html>");
		registerTitle.setFont(CRStyles.TITLE_FONT);
		registerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		registerTitle.setForeground(CRStyles.FG_DARK_COLOR);
		
		// Add information label
		JLabel infoLabel = new JLabel("<html>Esta es una solicitud para crear una cuenta en el sistema. Una vez enviada, será revisada por el departamento de registro. Si es aprobada, recibirás un correo con la hora de tu cita para completar el registro físico.</html>");
		infoLabel.setFont(CRStyles.FIELD_FONT);
		infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoLabel.setForeground(CRStyles.FG_DARK_COLOR);

		// Add components to the left panel
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(registerTitle);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(infoLabel);

		return leftPanel;
	}


	private JPanel createRightPanel() {
		// Create the right panel with a vertical box layout
		JPanel rightPanel = CRElements.createPanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);
		
		// Add fields for registration
		firstNameField = (JTextField) CRElements.createInputField(null);
		lastNameField = (JTextField) CRElements.createInputField(null);
		emailField = (JTextField) CRElements.createInputField(null);
		userIdField = (JTextField) CRElements.createInputField(null);

		// Add submit button
		submitRegistrationButton = CRElements.createButton(
			"Enviar Solicitud",
			CRStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			120
		);
		submitRegistrationButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Add fields and labels to the right panel
		rightPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));

		// Panel for name and surname in the same row
		JPanel nameRowPanel = new JPanel();
		nameRowPanel.setLayout(new BoxLayout(nameRowPanel, BoxLayout.X_AXIS));
		nameRowPanel.setOpaque(false);
		nameRowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
		nameRowPanel.setPreferredSize(new Dimension(nameRowPanel.getPreferredSize().width, 100));

		// Panel for the first name field
		JPanel firstNamePanel = new JPanel();
		firstNamePanel.setLayout(new BoxLayout(firstNamePanel, BoxLayout.Y_AXIS));
		firstNamePanel.setOpaque(false);
		firstNamePanel.setBackground(CRStyles.BG_DARK_COLOR);
		createRegistrationField(firstNamePanel, "Primer Nombre", firstNameField, "<html>Ingresa tu primer nombre. Ejemplo Marcos.</html>");

		// Panel for the last name field
		JPanel lastNamePanel = new JPanel();
		lastNamePanel.setLayout(new BoxLayout(lastNamePanel, BoxLayout.Y_AXIS));
		lastNamePanel.setOpaque(false);
		createRegistrationField(lastNamePanel, "Primer Apellido", lastNameField, "<html>Ingresa tu primer apellido. Ejemplo: Pérez.</html>");

		nameRowPanel.add(firstNamePanel);
		nameRowPanel.add(Box.createHorizontalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		nameRowPanel.add(lastNamePanel);

		rightPanel.add(nameRowPanel);
		rightPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));
		createRegistrationField(rightPanel, "Correo Electrónico", emailField, "<html>Debe ser un correo @gmail.com, institucional (@ucv.ve) o de facultad (@ciens.ucv.ve, etc.)</html>");
		createRegistrationField(rightPanel, "Cédula de Identidad", userIdField, "<html>Ingrese su número de cédula sin puntos ni guiones. Ejemplo: 12345678</html>");
		rightPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		rightPanel.add(submitRegistrationButton);

		return rightPanel;
	}


	private void createRegistrationField(JPanel panel, String labelText, JComponent field, String helpText) {
		JLabel label = new JLabel(labelText);
		label.setFont(CRStyles.MAIN_FONT);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel helpLabel = new JLabel(helpText);
		helpLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		helpLabel.setForeground(Color.GRAY);
		helpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(label);
		panel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));
		panel.add(field);
		panel.add(helpLabel);
		panel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
	}


	public RegisterView() {
		setTitle("Registro de Usuario");
		setSize(CRStyles.WINDOW_WIDTH_LOGIN, CRStyles.WINDOW_HEIGHT_LOGIN);
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
