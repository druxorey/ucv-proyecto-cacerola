package View.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Model.Entities.User;
import Model.Services.UserService;
import View.Common.ButtonRenderer;
import View.Common.ButtonEditor;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

import View.Common.*;

public class AdminView extends JFrame {
	private JPanel leftPanel;
	private JPanel rightPanel;
	private CardLayout cardLayout;
	private JButton shiftsManagementButton;
	private JButton userManagementButton;
	
	public JButton logOutButton;
	public JButton addUserButton;
	public JButton saveButton;
	public JTextField totalFixedCostsField;
	public JTextField variableCostsField;
	public JTextField numberOfTraysField;
	public JTextField wastePercentageField;


	private JPanel createLeftPanel() {
		JPanel leftPanel = CRElements.createImagePanel(CRStyles.PANEL_PADDING_LARGE, BoxLayout.Y_AXIS, "/Utils/background_02.jpg");

		shiftsManagementButton = CRElements.createButton(
			"Costos Operacionales",
			CRStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			CRStyles.BUTTON_WIDTH_MEDIUM);
		shiftsManagementButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		shiftsManagementButton.addActionListener(_ -> showCard("shiftsManagement"));
		
		userManagementButton = CRElements.createButton(
			"Gestión de Usuarios",
			CRStyles.BG_LIGHT_COLOR,
			CRStyles.FG_LIGHT_COLOR,
			false,
			CRStyles.BUTTON_WIDTH_MEDIUM);
		userManagementButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		userManagementButton.addActionListener(_ -> showCard("userManagement"));

		logOutButton = CRElements.createButton(
			"Cerrar Sesión",
			CRStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			CRStyles.BUTTON_WIDTH_SMALL);
		logOutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(shiftsManagementButton);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(userManagementButton);
		leftPanel.add(Box.createVerticalGlue());
		leftPanel.add(logOutButton);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));

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

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.setOpaque(false);

		JButton actualUsersButton = CRElements.createButton("Usuarios", CRStyles.ACCENT_COLOR, Color.WHITE, false, 120);
		JButton incomingUsersButton = CRElements.createButton("Solicitudes", CRStyles.ACCENT_COLOR, Color.WHITE, false, 120);

		buttonsPanel.add(actualUsersButton);
		buttonsPanel.add(Box.createHorizontalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		buttonsPanel.add(incomingUsersButton);
		buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		CardLayout userCardLayout = new CardLayout();
		JPanel userCardsPanel = new JPanel(userCardLayout);
		userCardsPanel.setBackground(CRStyles.BG_LIGHT_COLOR);

		JPanel actualUsersPanel = createActualUsersPanel();
		JPanel incomingUsersPanel = createIncomingUsersPanel();

		userCardsPanel.add(actualUsersPanel, "actualUsers");
		userCardsPanel.add(incomingUsersPanel, "incomingUsers");
		actualUsersButton.addActionListener(_ -> userCardLayout.show(userCardsPanel, "actualUsers"));
		incomingUsersButton.addActionListener(_ -> userCardLayout.show(userCardsPanel, "incomingUsers"));
		
		userManagementPanel.add(titleLabel);
		userManagementPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		userManagementPanel.add(buttonsPanel);
		userManagementPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		userManagementPanel.add(userCardsPanel);

		return userManagementPanel;
	}


	private JPanel createActualUsersPanel() {
		JPanel panel = CRElements.createBasePanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);
		panel.setOpaque(false);

		UserService userService = new UserService();
		List<User> users = userService.getAllUsers();
		users.sort(Comparator.comparing(User::getUserId));

		String[] columnNames = {"Cédula", "Nombre", "Apellido", "Correo", "Tipo de Usuario"};
		String[][] data = new String[users.size()][5];

		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			data[i][0] = u.getUserId();
			data[i][1] = u.getUserFirstName();
			data[i][2] = u.getUserLastName();
			data[i][3] = u.getUserEmail();
			data[i][4] = (u.getUserType() == 0) ? "Estudiante" : "Profesor/Personal";
		}

		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return true;
			}
		};

		JScrollPane scrollPane = CRElements.createStyledTableScrollPane(model);

		panel.add(Box.createVerticalStrut(20));
		panel.add(scrollPane);

		return panel;
	}


	private JPanel createIncomingUsersPanel() {
		JPanel panel = CRElements.createBasePanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);
		panel.setOpaque(false);

		UserService userService = new UserService();
		List<UserService.IncomingUserRequest> requests = userService.getIncomingUserRequests();

		String[] columnNames = {"Cédula", "Correo", "Acción"};
		Object[][] data = new Object[requests.size()][3];

		for (int i = 0; i < requests.size(); i++) {
			UserService.IncomingUserRequest req = requests.get(i);
			data[i][0] = req.userId;
			data[i][1] = req.email;
			data[i][2] = "Registrar";
		}

		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 2;
			}
		};

		JScrollPane scrollPane = CRElements.createStyledTableScrollPane(model);
		JTable table = (JTable) scrollPane.getViewport().getView();
		table.getColumn("Acción").setCellRenderer(new ButtonRenderer());
		table.getColumn("Acción").setCellEditor(new ButtonEditor(new JCheckBox(), this));

		panel.add(Box.createVerticalStrut(20));
		panel.add(scrollPane);

		return panel;
	}
	

	@SuppressWarnings("unchecked")
	private JPanel createShiftsPanel() {
		JPanel shiftsPanel = CRElements.createBasePanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);

		JLabel shiftsLabel = new JLabel("Costos Operacionales");
		shiftsLabel.setFont(CRStyles.TITLE_FONT);
		shiftsLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
		shiftsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		shiftsPanel.add(shiftsLabel);
		shiftsPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));

		totalFixedCostsField = (JTextField) CRElements.createInputField(null);
		variableCostsField = (JTextField) CRElements.createInputField(null);
		numberOfTraysField = (JTextField) CRElements.createInputField(null);
		wastePercentageField = (JTextField) CRElements.createInputField(null);

		addField(shiftsPanel, "Costos Fijos Totales", totalFixedCostsField, "<html>Costos fijos mensuales del comedor.</html>");
		addField(shiftsPanel, "Costos Variables", variableCostsField, "<html>Costos variables mensuales del comedor.</html>");
		addField(shiftsPanel, "Cantidad de Bandejas", numberOfTraysField, "<html>Cantidad de bandejas servidas en el mes.</html>");
		addField(shiftsPanel, "Porcentaje de Merma", wastePercentageField, "<html>Porcentaje estimado de merma en la producción.</html>");

		saveButton = CRElements.createButton("Guardar", CRStyles.ACCENT_COLOR, Color.WHITE, false, 120);
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		shiftsPanel.add(saveButton);

		Model.Services.OperationalCostsService operationalCostsService = new Model.Services.OperationalCostsService();
		org.json.simple.JSONObject operationalCosts = operationalCostsService.loadOperationalCosts();
		totalFixedCostsField.setText(String.valueOf(operationalCosts.getOrDefault("totalFixedCosts", 0)));
		variableCostsField.setText(String.valueOf(operationalCosts.getOrDefault("variableCosts", 0)));
		numberOfTraysField.setText(String.valueOf(operationalCosts.getOrDefault("numberOfTrays", 0)));
		wastePercentageField.setText(String.valueOf(operationalCosts.getOrDefault("wastePercentage", 0)));

		return shiftsPanel;
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

		if (name.equals("shiftsManagement")) {
			shiftsManagementButton.setBackground(CRStyles.ACCENT_COLOR);
			shiftsManagementButton.setForeground(Color.WHITE);
			userManagementButton.setBackground(CRStyles.BG_LIGHT_COLOR);
			userManagementButton.setForeground(CRStyles.FG_LIGHT_COLOR);
		} else if (name.equals("userManagement")) {
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