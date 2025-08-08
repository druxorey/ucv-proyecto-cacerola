package View.User;

import javax.swing.*;
import java.awt.*;
import View.Common.*;
import Model.Services.WalletService;
import Model.Entities.WalletMovement;
import java.util.List;
import java.text.SimpleDateFormat;
import Model.Services.MenuService;
import Model.Services.UserService;
import Model.Services.MenuPriceService;
import org.json.simple.JSONObject;
import java.time.LocalDate;
import java.time.DayOfWeek;
import Model.Services.FaceRecognitionService;

public class UserDashboardView extends JFrame {
	private JPanel leftPanel;
	private JPanel rightPanel;
	private CardLayout cardLayout;
	private JButton walletButton;
	private JButton menuButton;
	private WalletService walletService = new WalletService();
	public JButton logOutButton;
	private Controller.User.UserDashboardController controller;

	public void setController(Controller.User.UserDashboardController controller) {
		this.controller = controller;
	}
	
	public UserDashboardView(String userId) {
		setTitle("Panel de Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(CRStyles.WINDOW_WIDTH_INTERFACE, CRStyles.WINDOW_HEIGHT_INTERFACE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		leftPanel = createLeftPanel(userId);
		rightPanel = createRightPanel(userId);

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


	private JPanel createLeftPanel(String userId) {
		JPanel leftPanel = CRElements.createImagePanel(CRStyles.PANEL_PADDING_LARGE, BoxLayout.Y_AXIS, "/Utils/background_02.jpg");

		String fullName = UserService.getUserFullName(userId);
		JLabel titleLabel = CRElements.createTitleLabel("Bienvenido, " + fullName, CRStyles.FG_DARK_COLOR);

		walletButton = CRElements.createButton(
			"Monedero Virtual",
			CRStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			CRStyles.BUTTON_WIDTH_MEDIUM);
		walletButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		walletButton.addActionListener(_ -> showCard("monedero"));

		menuButton = CRElements.createButton(
			"Menú",
			CRStyles.BG_LIGHT_COLOR,
			CRStyles.FG_LIGHT_COLOR,
			false,
			CRStyles.BUTTON_WIDTH_MEDIUM);
		menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		menuButton.addActionListener(_ -> showCard("menu"));

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
		leftPanel.add(walletButton);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(menuButton);
		leftPanel.add(Box.createVerticalGlue());
		leftPanel.add(logOutButton);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));

		return leftPanel;
	}


	public JPanel createRightPanel(String userId) {
		cardLayout = new CardLayout();
		JPanel panel = new JPanel(cardLayout);

		JPanel walletPanel = createWalletPanel(userId);
		JPanel menuPanel = createMenuPanel(userId);

		panel.add(walletPanel, "monedero");
		panel.add(menuPanel, "menu");

		return panel;
	}

	@SuppressWarnings("unchecked")
	public JPanel createWalletPanel(String userId) {
		JPanel walletPanel = CRElements.createBasePanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);
		walletPanel.add(CRElements.createTitleLabel("Monedero Virtual", CRStyles.FG_LIGHT_COLOR));

		double saldo = walletService.getBalance(userId);
		JLabel saldoLabel = new JLabel("Saldo actual: Bs." + String.format("%.2f", saldo));
		saldoLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
		saldoLabel.setForeground(CRStyles.ACCENT_COLOR);
		saldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		walletPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		walletPanel.add(saldoLabel);

		walletPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		JLabel movTitle = new JLabel("Últimos movimientos");
		movTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
		movTitle.setForeground(CRStyles.FG_LIGHT_COLOR);
		movTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		walletPanel.add(movTitle);

		List<WalletMovement> movimientos = walletService.getMovements(userId);
		if (movimientos.isEmpty()) {
			JLabel noMov = new JLabel("No hay movimientos recientes.");
			noMov.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			noMov.setForeground(CRStyles.FG_DARK_COLOR);
			noMov.setAlignmentX(Component.CENTER_ALIGNMENT);
			walletPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));
			walletPanel.add(noMov);
		} else {
			JPanel movListPanel = new JPanel();
			movListPanel.setLayout(new BoxLayout(movListPanel, BoxLayout.Y_AXIS));
			movListPanel.setBackground(CRStyles.BG_LIGHT_COLOR);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			for (WalletMovement mov : movimientos) {
				String text = String.format(
					"<html><div style='text-align:center;'>%s<br>%s<br><b>%sBs.%.2f</b></div></html>",
					sdf.format(mov.getDate()),
					mov.getDescription(),
					mov.getAmount() >= 0 ? "+" : "",
					mov.getAmount()
				);
				JLabel movLabel = new JLabel(text, SwingConstants.CENTER);
				movLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				movLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
				movLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				movLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
				movListPanel.add(movLabel);
			}
			JScrollPane scroll = new JScrollPane(movListPanel);
			scroll.setPreferredSize(new Dimension(400, 180));
			scroll.setBorder(BorderFactory.createEmptyBorder());
			walletPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));
			walletPanel.add(scroll);

		}
		walletPanel.add(Box.createVerticalGlue());
		JButton rechargeButton = new JButton("Confirmar Recarga");
		rechargeButton.setBackground(CRStyles.ACCENT_COLOR);
		rechargeButton.setForeground(Color.WHITE);
		rechargeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		rechargeButton.addActionListener(_ -> {
			RechargeView rechargeView = new RechargeView(userId, amount -> {
				double saldoActual = walletService.getBalance(userId);
				double nuevoSaldo = saldoActual + amount;
				try {
					org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
					obj.put("wallet", nuevoSaldo);
					java.io.File walletFile = new java.io.File("Model/Data/Wallets/" + userId + "_wallet.json");
					try (java.io.FileWriter writer = new java.io.FileWriter(walletFile)) {
						writer.write(obj.toJSONString());
					}
					walletService.updateTransactionHistory(userId, amount, "Recarga", java.time.LocalDate.now(), "-");
					refreshPanels(userId);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Error al actualizar el saldo.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			});
			rechargeView.setVisible(true);
		});
		walletPanel.add(rechargeButton);
		walletPanel.add(Box.createVerticalStrut(20));
		return walletPanel;
	}


	private JPanel createMenuPanel(String userId) {
		new FaceRecognitionService();
		JPanel menuPanel = CRElements.createBasePanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);
		menuPanel.add(CRElements.createTitleLabel("Menú de la Semana", CRStyles.FG_LIGHT_COLOR));

		MenuService menuService = new MenuService();
		MenuPriceService priceService = new MenuPriceService();

		LocalDate today = LocalDate.now();
		LocalDate sunday = today.with(DayOfWeek.SUNDAY);

		JPanel weekPanel = new JPanel();
		weekPanel.setLayout(new BoxLayout(weekPanel, BoxLayout.X_AXIS));
		weekPanel.setBackground(CRStyles.BG_LIGHT_COLOR);

		for (int i = 0; i < 5; i++) {
			LocalDate date = sunday.plusDays(i);

			JPanel dayPanel = new JPanel();
			dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));
			dayPanel.setBackground(CRStyles.FG_DARK_COLOR);
			dayPanel.setAlignmentY(Component.TOP_ALIGNMENT);
			dayPanel.setMaximumSize(new Dimension(200, 350));
			dayPanel.setPreferredSize(new Dimension(200, 350));
			dayPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createLineBorder(CRStyles.FG_DARK_COLOR, 1)
			));

			JLabel dateLabel = new JLabel(date.getDayOfWeek().toString() + " " + date.toString());
			dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
			dateLabel.setForeground(CRStyles.ACCENT_COLOR);
			dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			dayPanel.add(dateLabel);
			dayPanel.add(Box.createVerticalStrut(8));

			String[] shifts = {"AM", "PM"};
			boolean anyMenu = false;
			for (String shift : shifts) {
				JSONObject menu = menuService.loadMenu(date, shift);

				JLabel shiftLabel = new JLabel(shift.equals("AM") ? "Almuerzo" : "Tarde");
				shiftLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
				shiftLabel.setForeground(CRStyles.ACCENT_COLOR);
				shiftLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				dayPanel.add(shiftLabel);

				if (menu != null) {
					anyMenu = true;
					JLabel platoLabel = new JLabel("Plato: " + menu.get("plato"));
					platoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
					platoLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
					platoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
					dayPanel.add(platoLabel);

					JLabel bebidaLabel = new JLabel("Bebida: " + menu.get("bebida"));
					bebidaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
					bebidaLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
					bebidaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
					dayPanel.add(bebidaLabel);

					JLabel postreLabel = new JLabel("Postre: " + menu.get("postre"));
					postreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
					postreLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
					postreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
					dayPanel.add(postreLabel);

					double monto = priceService.calculateMenuPrice(userId, date, shift);
					JLabel montoLabel = new JLabel(String.format("Monto: Bs. %.2f", monto));
					montoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
					montoLabel.setForeground(CRStyles.ACCENT_COLOR);
					montoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
					dayPanel.add(Box.createVerticalStrut(4));
					dayPanel.add(montoLabel);

					boolean yaPagado = walletService.hasPaidMenu(userId, date, shift);
					dayPanel.add(Box.createVerticalStrut(4));
				} else {
					JLabel noMenuLabel = new JLabel("<html><center>No hay menú<br>para este turno</center></html>");
					noMenuLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
					noMenuLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
					noMenuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
					dayPanel.add(noMenuLabel);

					dayPanel.add(Box.createVerticalStrut(8));
				}
				dayPanel.add(Box.createVerticalStrut(8));
			}
			weekPanel.add(dayPanel);
			if (i < 4) weekPanel.add(Box.createHorizontalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		}

		menuPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		menuPanel.add(weekPanel);
		menuPanel.add(Box.createVerticalGlue());

		return menuPanel;
	}


	public void refreshPanels(String userId) {
		JPanel newWalletPanel = createWalletPanel(userId);
		JPanel newMenuPanel = createMenuPanel(userId);

		rightPanel.removeAll();

		rightPanel.add(newWalletPanel, "monedero");
		rightPanel.add(newMenuPanel, "menu");

		rightPanel.revalidate();
		rightPanel.repaint();
	}


	public void showCard(String name) {
		cardLayout.show(rightPanel, name);
		setTitle(name);

		if (name.equals("monedero")) {
			walletButton.setBackground(CRStyles.ACCENT_COLOR);
			walletButton.setForeground(Color.WHITE);
			menuButton.setBackground(CRStyles.BG_LIGHT_COLOR);
			menuButton.setForeground(CRStyles.FG_LIGHT_COLOR);
		} else if (name.equals("menu")) {
			menuButton.setBackground(CRStyles.ACCENT_COLOR);
			menuButton.setForeground(Color.WHITE);
			walletButton.setBackground(CRStyles.BG_LIGHT_COLOR);
			walletButton.setForeground(CRStyles.FG_LIGHT_COLOR);
		}
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new UserDashboardView("").setVisible(true);
		});
	}
}
