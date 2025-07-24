package View.Admin;

import javax.swing.*;
import java.awt.*;
import View.Common.*;
import Controller.Admin.AdminController;

public class AdminView extends JFrame {
	private JPanel leftPanel;
	private JPanel rightPanel;
	private CardLayout cardLayout;
	private JButton shiftsManagementButton;
	private JButton userManagementButton;


	private JPanel createLeftPanel() {
		JPanel leftPanel = CRElements.createBasePanel(CRStyles.BG_DARK_COLOR, BoxLayout.Y_AXIS);

		shiftsManagementButton = CRElements.createButton(
			"Costos Operacionales",
			CRStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			CRStyles.BUTTON_WIDTH_MEDIUM);
		shiftsManagementButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		shiftsManagementButton.addActionListener(e -> showCard("shiftsManagement"));
		
		userManagementButton = CRElements.createButton(
			"Gestión de Usuarios",
			CRStyles.BG_LIGHT_COLOR,
			CRStyles.FG_LIGHT_COLOR,
			false,
			CRStyles.BUTTON_WIDTH_MEDIUM);
		userManagementButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		userManagementButton.addActionListener(e -> showCard("userManagement"));

		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(shiftsManagementButton);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(userManagementButton);
		leftPanel.add(Box.createVerticalGlue());

		return leftPanel;
	}


	private JPanel createRightPanel() {
		cardLayout = new CardLayout();
		JPanel panel = new JPanel(cardLayout);
		panel.setBackground(CRStyles.BG_LIGHT_COLOR);

		JPanel shiftsPanel = createShiftsPanel();
		JPanel userPanel = createUserManagementPanel();

		panel.add(shiftsPanel, "shiftsManagement");
		panel.add(userPanel, "userManagement");

		return panel;
	}


	private JPanel createUserManagementPanel() {
		JPanel userManagementPanel = CRElements.createBasePanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);

		JLabel titleLabel = new JLabel("Gestión de Usuarios");
		titleLabel.setFont(CRStyles.TITLE_FONT);
		titleLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		userManagementPanel.add(titleLabel);
		userManagementPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.setOpaque(false);

		JButton actualUsersButton = CRElements.createButton("Usuarios", CRStyles.ACCENT_COLOR, Color.WHITE, false, 120);
		JButton incomingUsersButton = CRElements.createButton("Solicitudes", CRStyles.ACCENT_COLOR, Color.WHITE, false, 120);
		actualUsersButton.addActionListener(e -> showCard("actualUsers"));
		incomingUsersButton.addActionListener(e -> showCard("incomingUsers"));

		buttonsPanel.add(actualUsersButton);
		buttonsPanel.add(Box.createHorizontalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		buttonsPanel.add(incomingUsersButton);

		buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		userManagementPanel.add(buttonsPanel);
		userManagementPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));

		return userManagementPanel;
	}

	
	private JPanel createShiftsPanel() {
		JPanel shiftsPanel = CRElements.createBasePanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);

		JLabel shiftsLabel = new JLabel("Costos Operacionales");
		shiftsLabel.setFont(CRStyles.TITLE_FONT);
		shiftsLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
		shiftsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		shiftsPanel.add(shiftsLabel);
		shiftsPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));

		JTextField totalFixedCostsField = (JTextField) CRElements.createInputField(null);
		JTextField variableCostsField = (JTextField) CRElements.createInputField(null);
		JTextField numberOfTraysField = (JTextField) CRElements.createInputField(null);
		JTextField wastePercentageField = (JTextField) CRElements.createInputField(null);

		addField(shiftsPanel, "Costos Fijos Totales", totalFixedCostsField, "<html>Costos fijos mensuales del comedor.</html>");
		addField(shiftsPanel, "Costos Variables", variableCostsField, "<html>Costos variables mensuales del comedor.</html>");
		addField(shiftsPanel, "Cantidad de Bandejas", numberOfTraysField, "<html>Cantidad de bandejas servidas en el mes.</html>");
		addField(shiftsPanel, "Porcentaje de Merma", wastePercentageField, "<html>Porcentaje estimado de merma en la producción.</html>");

		JButton saveButton = CRElements.createButton("Guardar", CRStyles.ACCENT_COLOR, Color.WHITE, false, 120);
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		shiftsPanel.add(saveButton);

		Model.Services.OperationalCostsService operationalCostsService = new Model.Services.OperationalCostsService();
		org.json.JSONObject operationalCosts = operationalCostsService.loadOperationalCosts();
		totalFixedCostsField.setText(String.valueOf(operationalCosts.optDouble("totalFixedCosts", 0)));
		variableCostsField.setText(String.valueOf(operationalCosts.optDouble("variableCosts", 0)));
		numberOfTraysField.setText(String.valueOf(operationalCosts.optInt("numberOfTrays", 0)));
		wastePercentageField.setText(String.valueOf(operationalCosts.optDouble("wastePercentage", 0)));

		saveButton.addActionListener(e -> {
			try {
				double totalFixedCosts = Double.parseDouble(totalFixedCostsField.getText());
				double variableCosts = Double.parseDouble(variableCostsField.getText());
				int numberOfTrays = Integer.parseInt(numberOfTraysField.getText());
				double wastePercentage = Double.parseDouble(wastePercentageField.getText());

				boolean ok = operationalCostsService.saveOperationalCosts(totalFixedCosts, variableCosts, numberOfTrays, wastePercentage);
				if (ok) {
					JOptionPane.showMessageDialog(this, "Costos guardados correctamente.");
				} else {
					JOptionPane.showMessageDialog(this, "Error al guardar los costos.");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Verifica los valores ingresados.");
			}
		});

		return shiftsPanel;
	}


	private JPanel createUserPanel() {
		JPanel userPanel = CRElements.createBasePanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);

		JLabel userLabel = new JLabel("Gestión de Usuarios");
		userLabel.setFont(CRStyles.TITLE_FONT);
		userLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
		userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JTextField firstNameField = (JTextField) CRElements.createInputField(null);
		JTextField lastNameField = (JTextField) CRElements.createInputField(null);
		JTextField userIdField = (JTextField) CRElements.createInputField(null);
		JTextField emailField = (JTextField) CRElements.createInputField(null);
		JPasswordField passwordField = (JPasswordField) CRElements.createPasswordField(null);

		String[] userTypes = {"Estudiante", "Profesor/Personal"};
		JComboBox<String> userTypeDropdown = new JComboBox<>(userTypes);
		userTypeDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, CRStyles.FIELD_HEIGHT));
		userTypeDropdown.setBackground(CRStyles.BG_LIGHT_COLOR);
		userTypeDropdown.setFont(CRStyles.FIELD_FONT);
		userTypeDropdown.setBorder(BorderFactory.createLineBorder(CRStyles.FG_DARK_COLOR, 2));

		JButton addUserButton = CRElements.createButton("Agregar", CRStyles.ACCENT_COLOR, Color.WHITE, false, 120);

		AdminController adminController = new AdminController(this);
		addUserButton.addActionListener(e -> {
			adminController.handleAddUser(firstNameField, lastNameField, userIdField, passwordField, emailField, userTypeDropdown);
		});

		userPanel.add(userLabel);
		userPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		addField(userPanel, "Nombre", firstNameField, "<html>Nombre del usuario a registrar.</html>");
		addField(userPanel, "Apellido", lastNameField, "<html>Apellido del usuario a registrar.</html>");
		addField(userPanel, "Cédula de Identidad", userIdField, "<html>Cédula o identificador único.</html>");
		addField(userPanel, "Email", emailField, "<html>Correo institucional o personal válido.</html>");
		addField(userPanel, "Contraseña", passwordField, "<html>Debe tener al menos 8 caracteres, incluir letras y números.</html>");
		addField(userPanel, "Tipo de Usuario", userTypeDropdown, "<html>Selecciona el tipo de usuario.</html>");
		userPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		addUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		userPanel.add(addUserButton);

		return userPanel;
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


	private void showCard(String name) {
		cardLayout.show(rightPanel, name);

		if (name.equals("costos")) {
			shiftsManagementButton.setBackground(CRStyles.ACCENT_COLOR);
			shiftsManagementButton.setForeground(Color.WHITE);
			userManagementButton.setBackground(CRStyles.BG_LIGHT_COLOR);
			userManagementButton.setForeground(CRStyles.FG_LIGHT_COLOR);
		} else if (name.equals("usuarios")) {
			userManagementButton.setBackground(CRStyles.ACCENT_COLOR);
			userManagementButton.setForeground(Color.WHITE);
			shiftsManagementButton.setBackground(CRStyles.BG_LIGHT_COLOR);
			shiftsManagementButton.setForeground(CRStyles.FG_LIGHT_COLOR);
		}
	}


	public AdminView() {
		setTitle("Panel de Administrador");
		setSize(CRStyles.WINDOW_WIDTH_INTERFACE, CRStyles.WINDOW_HEIGHT_INTERFACE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		leftPanel = createLeftPanel();
		rightPanel = createRightPanel();

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setEnabled(false);
		splitPane.setDividerSize(0);
		splitPane.setBorder(BorderFactory.createEmptyBorder());
		add(splitPane);

		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				int w = getWidth();
				double relation = 0.25; // 25% width for left panel
				int divider = (int) (w * relation);
				splitPane.setDividerLocation(divider);
			}
		});
	}


	public static void main(String[] args) {
		AdminView adminView = new AdminView();
		adminView.setVisible(true);
	}
}
