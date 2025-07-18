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
		// Create the left panel with a vertical box layout
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING));
		leftPanel.setBackground(UIStyles.BG_SECONDARY_COLOR);

		// Add button to select shifts management
		shiftsManagementButton = UIElements.createButton(
			"Costos Operacionales",
			UIStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			UIStyles.BUTTON_WIDTH_MEDIUM);
		shiftsManagementButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		shiftsManagementButton.addActionListener(e -> showCard("costos"));
		
		// Add button to select user management
		userManagementButton = UIElements.createButton(
			"Gestión de Usuarios",
			UIStyles.BG_PRIMARY_COLOR,
			UIStyles.FG_PRIMARY_COLOR,
			false,
			UIStyles.BUTTON_WIDTH_MEDIUM);
		userManagementButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		userManagementButton.addActionListener(e -> showCard("usuarios"));

		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		leftPanel.add(shiftsManagementButton);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		leftPanel.add(userManagementButton);
		leftPanel.add(Box.createVerticalGlue());

		return leftPanel;
	}


	private JPanel createRightPanel() {
		cardLayout = new CardLayout();
		JPanel panel = new JPanel(cardLayout);
		panel.setBackground(UIStyles.BG_PRIMARY_COLOR);

		JPanel shiftsPanel = createShiftsPanel();
		JPanel userPanel = createUserPanel();

		panel.add(shiftsPanel, "costos");
		panel.add(userPanel, "usuarios");

		return panel;
	}

	private JPanel createShiftsPanel() {
		JPanel shiftsPanel = new JPanel();
		shiftsPanel.setBackground(UIStyles.BG_PRIMARY_COLOR);
		shiftsPanel.setLayout(new BoxLayout(shiftsPanel, BoxLayout.Y_AXIS));
		shiftsPanel.setBorder(BorderFactory.createEmptyBorder(
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING * 8,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING * 8));

		JLabel shiftsLabel = new JLabel("Costos Operacionales");
		shiftsLabel.setFont(UIStyles.TITLE_FONT);
		shiftsLabel.setForeground(UIStyles.FG_PRIMARY_COLOR);
		shiftsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		shiftsPanel.add(shiftsLabel);
		shiftsPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));

		JTextField totalFixedCostsField = (JTextField) UIElements.createInputField(false, null);
		JTextField variableCostsField = (JTextField) UIElements.createInputField(false, null);
		JTextField numberOfTraysField = (JTextField) UIElements.createInputField(false, null);
		JTextField wastePercentageField = (JTextField) UIElements.createInputField(false, null);

		addField(shiftsPanel, "Costos Fijos Totales", totalFixedCostsField, "<html>Costos fijos mensuales del comedor.</html>");
		addField(shiftsPanel, "Costos Variables", variableCostsField, "<html>Costos variables mensuales del comedor.</html>");
		addField(shiftsPanel, "Cantidad de Bandejas", numberOfTraysField, "<html>Cantidad de bandejas servidas en el mes.</html>");
		addField(shiftsPanel, "Porcentaje de Merma", wastePercentageField, "<html>Porcentaje estimado de merma en la producción.</html>");

		JButton saveButton = UIElements.createButton("Guardar", UIStyles.ACCENT_COLOR, Color.WHITE, false, 120);
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		shiftsPanel.add(saveButton);

		// Cargar valores al iniciar
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
		// Create a panel for user management
		JPanel userPanel = new JPanel();
		userPanel.setBackground(UIStyles.BG_PRIMARY_COLOR);
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
		userPanel.setBorder(BorderFactory.createEmptyBorder(
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING * 8,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING * 8));

		JLabel userLabel = new JLabel("Gestión de Usuarios");
		userLabel.setFont(UIStyles.TITLE_FONT);
		userLabel.setForeground(UIStyles.FG_PRIMARY_COLOR);
		userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JTextField firstNameField = (JTextField) UIElements.createInputField(false, null);
		JTextField lastNameField = (JTextField) UIElements.createInputField(false, null);
		JTextField userIdField = (JTextField) UIElements.createInputField(false, null);
		JPasswordField passwordField = (JPasswordField) UIElements.createInputField(true, null);
		JTextField emailField = (JTextField) UIElements.createInputField(false, null);

		String[] userTypes = {"Estudiante", "Profesor/Personal"};
		JComboBox<String> userTypeDropdown = new JComboBox<>(userTypes);
		userTypeDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, UIStyles.FIELD_HEIGHT));
		userTypeDropdown.setBackground(UIStyles.BG_PRIMARY_COLOR);
		userTypeDropdown.setFont(UIStyles.FIELD_FONT);
		userTypeDropdown.setBorder(BorderFactory.createLineBorder(UIStyles.FG_SECONDARY_COLOR, 2));

		JButton addUserButton = UIElements.createButton("Agregar", UIStyles.ACCENT_COLOR, Color.WHITE, false, 120);

		AdminController adminController = new AdminController(this);
		addUserButton.addActionListener(e -> {
			adminController.handleAddUser(firstNameField, lastNameField, userIdField, passwordField, emailField, userTypeDropdown);
		});

		userPanel.add(userLabel);
		userPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		addField(userPanel, "Nombre", firstNameField, "<html>Nombre del usuario a registrar.</html>");
		addField(userPanel, "Apellido", lastNameField, "<html>Apellido del usuario a registrar.</html>");
		addField(userPanel, "Usuario", userIdField, "<html>Cédula o identificador único.</html>");
		addField(userPanel, "Email", emailField, "<html>Correo institucional o personal válido.</html>");
		addField(userPanel, "Contraseña", passwordField, "<html>Debe tener al menos 8 caracteres, incluir letras y números.</html>");
		addField(userPanel, "Tipo de Usuario", userTypeDropdown, "<html>Selecciona el tipo de usuario.</html>");
		userPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
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

		if (name.equals("turnos")) {
			shiftsManagementButton.setBackground(UIStyles.ACCENT_COLOR);
			shiftsManagementButton.setForeground(Color.WHITE);
			userManagementButton.setBackground(UIStyles.BG_PRIMARY_COLOR);
			userManagementButton.setForeground(UIStyles.FG_PRIMARY_COLOR);
		} else if (name.equals("usuarios")) {
			userManagementButton.setBackground(UIStyles.ACCENT_COLOR);
			userManagementButton.setForeground(Color.WHITE);
			shiftsManagementButton.setBackground(UIStyles.BG_PRIMARY_COLOR);
			shiftsManagementButton.setForeground(UIStyles.FG_PRIMARY_COLOR);
		}
	}


	public AdminView() {
		setTitle("Panel de Administrador");
		setSize(UIStyles.WINDOW_WIDTH_INTERFACE, UIStyles.WINDOW_HEIGHT_INTERFACE);
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
