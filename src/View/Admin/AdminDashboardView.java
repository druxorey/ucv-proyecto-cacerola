package View.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONObject;

import Controller.Admin.MenuManagementController;
import Model.Entities.User;
import Model.Services.OperationalCostsService;
import Model.Services.UserService;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import View.Common.*;

public class AdminDashboardView extends JFrame {
	private JPanel leftPanel;
	private JPanel rightPanel;
	private CardLayout cardLayout;
	private JButton shiftsManagementButton;
	private JButton userManagementButton;
	private JButton menuManagementButton;
	private JButton paymentServiceButton;
	
	public JButton logOutButton;
	public JButton addUserButton;
	public JButton saveButton;
	public JTextField totalFixedCostsField;
	public JTextField variableCostsField;
	public JTextField numberOfTraysField;
	public JTextField wastePercentageField;
	public JTextField studentPctField;
	public JTextField professorPctField;
	public JTextField employeePctField;

	private JComboBox<String> weekSelector;
    public LocalDate selectedWeekStart;
    public LocalDate selectedWeekEnd;
    private OperationalCostsService operationalCostsService = new OperationalCostsService();


	private JPanel createLeftPanel() {
		JPanel leftPanel = CRElements.createImagePanel(CRStyles.PANEL_PADDING_LARGE, BoxLayout.Y_AXIS, "/Utils/background_02.jpg");
		JLabel titleLabel = CRElements.createTitleLabel("Panel de Administrador", Color.WHITE);

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

		menuManagementButton = CRElements.createButton(
			"Gestión de Menú",
			CRStyles.BG_LIGHT_COLOR,
			CRStyles.FG_LIGHT_COLOR,
			false,
			CRStyles.BUTTON_WIDTH_MEDIUM);
		menuManagementButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		menuManagementButton.addActionListener(_ -> showCard("menuManagement"));

		paymentServiceButton = CRElements.createButton(
			"Servicio de Pago",
			CRStyles.BG_LIGHT_COLOR,
			CRStyles.FG_LIGHT_COLOR,
			false,
			CRStyles.BUTTON_WIDTH_MEDIUM
		);
		paymentServiceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		paymentServiceButton.addActionListener(_ -> showCard("paymentService"));

		logOutButton = CRElements.createButton(
			"Cerrar Sesión",
			CRStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			CRStyles.BUTTON_WIDTH_SMALL);
		logOutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(titleLabel);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_LARGE));
		leftPanel.add(shiftsManagementButton);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(userManagementButton);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(menuManagementButton);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
    	leftPanel.add(paymentServiceButton);
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
		JPanel menuPanel = createMenuManagementPanel();
		JPanel paymentServicePanel = createPaymentServicePanel();
		
		panel.add(shiftsPanel, "shiftsManagement");
		panel.add(userPanel, "userManagement");
		panel.add(menuPanel, "menuManagement");
		panel.add(paymentServicePanel, "paymentService");

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


	private JPanel createPaymentServicePanel() {
		JPanel panel = CRElements.createBasePanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);

		JLabel title = CRElements.createTitleLabel("Servicio de Pago", CRStyles.FG_LIGHT_COLOR);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton openPrepayButton = CRElements.createButton(
			"Abrir ventana de Pre-pago",
			CRStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			CRStyles.BUTTON_WIDTH_MEDIUM
		);
		openPrepayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		openPrepayButton.addActionListener(_ -> {
			new Controller.Admin.AdminPaymentServiceController().showPrepayDialog();
		});

		panel.add(Box.createVerticalGlue());
		panel.add(title);
		panel.add(Box.createVerticalStrut(30));
		panel.add(openPrepayButton);
		panel.add(Box.createVerticalGlue());

		return panel;
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
	
	private void updateWeekSelector() {
		weekSelector.removeAllItems();
		var weeks = operationalCostsService.getAllWeeks();
		for (String w : weeks) weekSelector.addItem(w);
		LocalDate today = LocalDate.now();
		LocalDate sunday = today.with(java.time.DayOfWeek.SUNDAY);
		LocalDate friday = sunday.plusDays(5);
		String current = sunday + "->" + friday;
		if (weeks.contains(current)) weekSelector.setSelectedItem(current);
		else if (!weeks.isEmpty()) weekSelector.setSelectedIndex(0);
	}

	@SuppressWarnings("unchecked")
	private void loadOperationalCostsForSelectedWeek() {
		JSONObject operationalCosts = operationalCostsService.loadOperationalCostsForWeek(selectedWeekStart);
		if (operationalCosts == null) {
			totalFixedCostsField.setText("0");
			variableCostsField.setText("0");
			numberOfTraysField.setText("0");
			wastePercentageField.setText("0");
			studentPctField.setText("0");
			professorPctField.setText("0");
			employeePctField.setText("0");
		} else {
			totalFixedCostsField.setText(String.valueOf(operationalCosts.getOrDefault("totalFixedCosts", 0)));
			variableCostsField.setText(String.valueOf(operationalCosts.getOrDefault("variableCosts", 0)));
			numberOfTraysField.setText(String.valueOf(operationalCosts.getOrDefault("numberOfTrays", 0)));
			wastePercentageField.setText(String.valueOf(operationalCosts.getOrDefault("wastePercentage", 0)));
			studentPctField.setText(String.valueOf(operationalCosts.getOrDefault("studentPct", 0)));
			professorPctField.setText(String.valueOf(operationalCosts.getOrDefault("professorPct", 0)));
			employeePctField.setText(String.valueOf(operationalCosts.getOrDefault("employeePct", 0)));
		}
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

		weekSelector = new JComboBox<>();
		weekSelector.setFont(CRStyles.MAIN_FONT);
		weekSelector.setForeground(CRStyles.FG_LIGHT_COLOR);
		weekSelector.setBackground(CRStyles.BG_LIGHT_COLOR);
		weekSelector.setAlignmentX(Component.CENTER_ALIGNMENT);
		updateWeekSelector();
		weekSelector.setMaximumSize(new Dimension(300, 30));
		weekSelector.addActionListener(_ -> {
			String selected = (String) weekSelector.getSelectedItem();
			if (selected != null) {
				String[] parts = selected.split("->");
				selectedWeekStart = LocalDate.parse(parts[0].trim());
				selectedWeekEnd = LocalDate.parse(parts[1].trim());
				loadOperationalCostsForSelectedWeek();
			}
		});

		totalFixedCostsField = (JTextField) CRElements.createInputField(null);
		variableCostsField = (JTextField) CRElements.createInputField(null);
		numberOfTraysField = (JTextField) CRElements.createInputField(null);
		wastePercentageField = (JTextField) CRElements.createInputField(null);

		studentPctField = (JTextField) CRElements.createInputField(null);
		professorPctField = (JTextField) CRElements.createInputField(null);
		employeePctField = (JTextField) CRElements.createInputField(null);

		LocalDate today = LocalDate.now();
		LocalDate sunday = today.with(java.time.DayOfWeek.SUNDAY);
		LocalDate friday = sunday.plusDays(5);
		String current = sunday + "->" + friday;
		if (weekSelector.getItemCount() > 0) {
			for (int i = 0; i < weekSelector.getItemCount(); i++) {
				if (weekSelector.getItemAt(i).equals(current)) {
					weekSelector.setSelectedIndex(i);
					selectedWeekStart = sunday;
					selectedWeekEnd = friday;
					loadOperationalCostsForSelectedWeek();
					break;
				}
			}
		}

		JLabel weekLabel = new JLabel("Seleccionar Semana:");
		weekLabel.setFont(CRStyles.MAIN_FONT);
		weekLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
		weekLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		shiftsPanel.add(weekLabel);
		shiftsPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));
		shiftsPanel.add(weekSelector);
		shiftsPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));

		JPanel pctPanel = new JPanel();
		pctPanel.setLayout(new BoxLayout(pctPanel, BoxLayout.X_AXIS));
		pctPanel.setOpaque(false);

		pctPanel.add(new JLabel("Estudiante %:"));
		pctPanel.add(studentPctField);
		pctPanel.add(Box.createHorizontalStrut(10));
		pctPanel.add(new JLabel("Profesor %:"));
		pctPanel.add(professorPctField);
		pctPanel.add(Box.createHorizontalStrut(10));
		pctPanel.add(new JLabel("Empleado %:"));
		pctPanel.add(employeePctField);

		addField(shiftsPanel, "Costos Fijos Totales", totalFixedCostsField, "<html>Costos fijos mensuales del comedor.</html>");
		addField(shiftsPanel, "Costos Variables", variableCostsField, "<html>Costos variables mensuales del comedor.</html>");
		addField(shiftsPanel, "Cantidad de Bandejas", numberOfTraysField, "<html>Cantidad de bandejas servidas en el mes.</html>");
		addField(shiftsPanel, "Porcentaje de Merma", wastePercentageField, "<html>Porcentaje estimado de merma en la producción.</html>");
		
		JLabel pctLabel = new JLabel("Porcentajes a cobrar por tipo de usuario:");
		pctLabel.setFont(CRStyles.MAIN_FONT);
		pctLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
		pctLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		shiftsPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_LARGE));
		shiftsPanel.add(pctLabel);
		shiftsPanel.add(pctPanel);

		JLabel ccbLabel = new JLabel();
		JLabel studentAmountLabel = new JLabel();
		JLabel professorAmountLabel = new JLabel();
		JLabel employeeAmountLabel = new JLabel();

		Dimension labelSize = new Dimension(170, 25); // Ajusta el ancho según lo que necesites
		ccbLabel.setPreferredSize(labelSize);
		ccbLabel.setMinimumSize(labelSize);
		ccbLabel.setMaximumSize(labelSize);
		ccbLabel.setHorizontalAlignment(SwingConstants.CENTER);

		studentAmountLabel.setPreferredSize(labelSize);
		studentAmountLabel.setMinimumSize(labelSize);
		studentAmountLabel.setMaximumSize(labelSize);
		studentAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);

		professorAmountLabel.setPreferredSize(labelSize);
		professorAmountLabel.setMinimumSize(labelSize);
		professorAmountLabel.setMaximumSize(labelSize);
		professorAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);

		employeeAmountLabel.setPreferredSize(labelSize);
		employeeAmountLabel.setMinimumSize(labelSize);
		employeeAmountLabel.setMaximumSize(labelSize);
		employeeAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel amountsPanel = new JPanel();
		amountsPanel.setLayout(new BoxLayout(amountsPanel, BoxLayout.X_AXIS));
		amountsPanel.setOpaque(false);
		amountsPanel.add(ccbLabel);
		amountsPanel.add(Box.createHorizontalStrut(20));
		amountsPanel.add(studentAmountLabel);
		amountsPanel.add(Box.createHorizontalStrut(20));
		amountsPanel.add(professorAmountLabel);
		amountsPanel.add(Box.createHorizontalStrut(20));
		amountsPanel.add(employeeAmountLabel);

		shiftsPanel.add(Box.createVerticalStrut(10));
		shiftsPanel.add(amountsPanel);

		saveButton = CRElements.createButton("Guardar", CRStyles.ACCENT_COLOR, Color.WHITE, false, 120);
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		shiftsPanel.add(saveButton);

		Runnable updateAmounts = () -> {
			try {
				double totalFixed = Double.parseDouble(totalFixedCostsField.getText());
				double variable = Double.parseDouble(variableCostsField.getText());
				int trays = Integer.parseInt(numberOfTraysField.getText());
				double waste = Double.parseDouble(wastePercentageField.getText());
				double studentPct = Double.parseDouble(studentPctField.getText());
				double professorPct = Double.parseDouble(professorPctField.getText());
				double employeePct = Double.parseDouble(employeePctField.getText());

				if (studentPct < 20 || studentPct > 30 ||
					professorPct < 70 || professorPct > 90 ||
					employeePct < 90 || employeePct > 110) {
					ccbLabel.setText("CCB: -");
					studentAmountLabel.setText("Estudiante: -");
					professorAmountLabel.setText("Profesor: -");
					employeeAmountLabel.setText("Empleado: -");
					return;
				}

				org.json.simple.JSONObject temp = new org.json.simple.JSONObject();
				temp.put("totalFixedCosts", totalFixed);
				temp.put("variableCosts", variable);
				temp.put("numberOfTrays", trays);
				temp.put("wastePercentage", waste);
				temp.put("studentPct", studentPct);
				temp.put("professorPct", professorPct);
				temp.put("employeePct", employeePct);

				org.json.simple.JSONObject result = operationalCostsService.calculateCCBAndAmounts(temp);
				ccbLabel.setText(String.format("CCB: Bs. %.2f", result.get("ccb")));
				studentAmountLabel.setText(String.format("Estudiante: Bs. %.2f", result.get("studentAmount")));
				professorAmountLabel.setText(String.format("Profesor: Bs. %.2f", result.get("professorAmount")));
				employeeAmountLabel.setText(String.format("Empleado: Bs. %.2f", result.get("employeeAmount")));
			} catch (Exception ex) {
				ccbLabel.setText("CCB: -");
				studentAmountLabel.setText("Estudiante: -");
				professorAmountLabel.setText("Profesor: -");
				employeeAmountLabel.setText("Empleado: -");
			}
		};

		totalFixedCostsField.getDocument().addDocumentListener(new SimpleDocumentListener(updateAmounts));
		variableCostsField.getDocument().addDocumentListener(new SimpleDocumentListener(updateAmounts));
		numberOfTraysField.getDocument().addDocumentListener(new SimpleDocumentListener(updateAmounts));
		wastePercentageField.getDocument().addDocumentListener(new SimpleDocumentListener(updateAmounts));
		studentPctField.getDocument().addDocumentListener(new SimpleDocumentListener(updateAmounts));
		professorPctField.getDocument().addDocumentListener(new SimpleDocumentListener(updateAmounts));
		employeePctField.getDocument().addDocumentListener(new SimpleDocumentListener(updateAmounts));
		updateAmounts.run();

		return shiftsPanel;
	}


	private JPanel createMenuManagementPanel() {
		MenuManagementPanel menuPanel = new MenuManagementPanel();
		MenuManagementController controller = new MenuManagementController(menuPanel);
		menuPanel.setController(controller);
		return menuPanel;
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

		JButton[] buttons = {shiftsManagementButton, userManagementButton, menuManagementButton, paymentServiceButton};
		String[] names = {"shiftsManagement", "userManagement", "menuManagement", "paymentService"};

		for (int i = 0; i < buttons.length; i++) {
			if (names[i].equals(name)) {
				buttons[i].setBackground(CRStyles.ACCENT_COLOR);
				buttons[i].setForeground(Color.WHITE);
			} else {
				buttons[i].setBackground(CRStyles.BG_LIGHT_COLOR);
				buttons[i].setForeground(CRStyles.FG_LIGHT_COLOR);
			}
		}
	}


	public AdminDashboardView() {
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
				double relation = 0.30; // 30% width for left panel
				int divider = (int) (w * relation);
				splitPane.setDividerLocation(divider);
			}
		});
	}


	public static void main(String[] args) {
		AdminDashboardView adminView = new AdminDashboardView();
		adminView.setVisible(true);
	}
}


class SimpleDocumentListener implements DocumentListener {
    private Runnable onChange;
    public SimpleDocumentListener(Runnable onChange) { this.onChange = onChange; }
    public void insertUpdate(DocumentEvent e) { onChange.run(); }
    public void removeUpdate(DocumentEvent e) { onChange.run(); }
    public void changedUpdate(DocumentEvent e) { onChange.run(); }
}