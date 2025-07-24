package View.User;

import javax.swing.*;
import java.awt.*;
import View.Common.*;
import Model.Services.WalletService;
import Model.Entities.WalletMovement;
import java.util.List;
import java.text.SimpleDateFormat;

public class UserView extends JFrame {
	private JPanel leftPanel;
	private JPanel rightPanel;
	private CardLayout cardLayout;
	private JButton walletButton;
	private JButton menuButton;
	private WalletService walletService = new WalletService();


	private JPanel createLeftPanel() {
		JPanel leftPanel = CRElements.createImagePanel(CRStyles.PANEL_PADDING_LARGE, BoxLayout.Y_AXIS, "/Utils/background_02.jpg");

		walletButton = CRElements.createButton(
			"Monedero Virtual",
			CRStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			CRStyles.BUTTON_WIDTH_MEDIUM);
		walletButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		walletButton.addActionListener(e -> showCard("monedero"));

		menuButton = CRElements.createButton(
			"Menú",
			CRStyles.BG_LIGHT_COLOR,
			CRStyles.FG_LIGHT_COLOR,
			false,
			CRStyles.BUTTON_WIDTH_MEDIUM);
		menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		menuButton.addActionListener(e -> showCard("menu"));

		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(walletButton);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(menuButton);
		leftPanel.add(Box.createVerticalGlue());

		return leftPanel;
	}


	private JPanel createRightPanel(String userId) {
		cardLayout = new CardLayout();
		JPanel panel = new JPanel(cardLayout);

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

		List<WalletMovement> movimientos = walletService.getMovements();
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
				String text = String.format("%s | %s | %sBs.%.2f",
						sdf.format(mov.getDate()),
						mov.getDescription(),
						mov.getAmount() >= 0 ? "+" : "",
						mov.getAmount());
				JLabel movLabel = new JLabel(text);
				movLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				movLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
				movListPanel.add(movLabel);
			}
			JScrollPane scroll = new JScrollPane(movListPanel);
			scroll.setPreferredSize(new Dimension(400, 180));
			scroll.setBorder(BorderFactory.createEmptyBorder());
			walletPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));
			walletPanel.add(scroll);
		}

		JPanel menuPanel = CRElements.createBasePanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);
		menuPanel.add(CRElements.createTitleLabel("Menú", CRStyles.FG_LIGHT_COLOR));

		class Comida {
			String nombre, descripcion;
			double costo;
			Comida(String n, String d, double c) { nombre = n; descripcion = d; costo = c; }
		}

		Comida[] comidas = new Comida[] {
			new Comida("Arepa Reina Pepiada", "Arepa rellena de pollo y aguacate.", 45.00),
			new Comida("Pabellón Criollo", "Carne mechada, arroz, caraotas y plátano.", 80.00),
			new Comida("Empanada de Queso", "Empanada frita rellena de queso blanco.", 25.00),
			new Comida("Cachapa con Queso", "Tortilla de maíz dulce con queso de mano.", 60.00),
			new Comida("Pastelito Andino", "Masa rellena de carne o pollo, frita.", 30.00)
		};

		menuPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		for (Comida comida : comidas) {
			JPanel comidaPanel = new JPanel();
			comidaPanel.setLayout(new BoxLayout(comidaPanel, BoxLayout.Y_AXIS));
			comidaPanel.setBackground(CRStyles.FG_DARK_COLOR);
			comidaPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
			comidaPanel.setMaximumSize(new Dimension(300, 100));

			comidaPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createLineBorder(CRStyles.FG_DARK_COLOR, 1)
			));

			JLabel nombreLabel = new JLabel(comida.nombre);
			nombreLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
			nombreLabel.setForeground(CRStyles.ACCENT_COLOR);
			comidaPanel.add(nombreLabel);

			JLabel descLabel = new JLabel(comida.descripcion);
			descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			descLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
			comidaPanel.add(descLabel);

			JLabel costoLabel = new JLabel(String.format("Costo: Bs %.2f", comida.costo));
			costoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
			costoLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
			comidaPanel.add(costoLabel);

			comidaPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			menuPanel.add(comidaPanel);
			menuPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));
		}

		panel.add(walletPanel, "monedero");
		panel.add(menuPanel, "menu");

		return panel;
	}


	private void showCard(String name) {
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


	public UserView(String userId) {
		setTitle("Panel de Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(CRStyles.WINDOW_WIDTH_INTERFACE, CRStyles.WINDOW_HEIGHT_INTERFACE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		leftPanel = createLeftPanel();
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


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new UserView("").setVisible(true);
		});
	}
}
