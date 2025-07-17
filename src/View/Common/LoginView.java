package View.Common;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
	// Global style constants
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 500;
	private static final int FIELD_HEIGHT = 30;
	private static final int FIELD_COLUMNS = 20;
	private static final int PANEL_PADDING = 30;
	private static final int LOGO_HEIGHT = 40;
	private static final int LOGO_WIDTH = 40;
	private static final int VERTICAL_STRUT_SMALL = 10;
	private static final int VERTICAL_STRUT_MEDIUM = 20;

	// Global fonts
	private static final Font MAIN_FONT = new Font("Segoe UI", Font.BOLD, 14);
	private static final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 14);
	private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 26);

	public JTextField userIdField;
	public JPasswordField passwordField;
	public JButton loginButton;
	public JButton registerButton;	

	public LoginView() {
		createWindow();
	}

	private void createWindow() {
		setTitle("Login");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel leftPanel = createLeftPanel();
		JPanel rightPanel = createRightPanel();

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setEnabled(false);
		splitPane.setDividerSize(0);
		splitPane.setBorder(BorderFactory.createEmptyBorder());
		javax.swing.plaf.basic.BasicSplitPaneDivider divider = ((javax.swing.plaf.basic.BasicSplitPaneUI) splitPane.getUI()).getDivider();
		if (divider != null) {
			divider.setBackground(new Color(0xff00ff));
		}
		add(splitPane);

		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				int w = getWidth();
				double relation = 0.40; // 60% width for left panel
				int divider = (int) (w * relation);
				splitPane.setDividerLocation(divider);
			}
		});
	}

	private JPanel createRightPanel() {
		JPanel leftPanel = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				java.net.URL imgUrl = getClass().getResource("/Utils/loginImage.jpg");
				if (imgUrl != null) {
					ImageIcon icon = new ImageIcon(imgUrl);
					Image img = icon.getImage();
					int panelWidth = getWidth();
					int panelHeight = getHeight();
					int imgWidth = img.getWidth(null);
					int imgHeight = img.getHeight(null);

					if (imgWidth > 0 && imgHeight > 0) {
						// Scale the image to fit the panel while maintaining aspect ratio
						float scale = Math.max((float) panelWidth / imgWidth, (float) panelHeight / imgHeight);
						int newImgWidth = (int) (imgWidth * scale);
						int newImgHeight = (int) (imgHeight * scale);
						int x = (panelWidth - newImgWidth) / 2;
						int y = (panelHeight - newImgHeight) / 2;
						g.drawImage(img, x, y, newImgWidth, newImgHeight, this);
					}
				}
			}
		};
		leftPanel.setBackground(Color.WHITE);
		return leftPanel;
	}

	private JPanel createLeftPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));
		rightPanel.setBackground(UIColors.BG_PRIMARY_COLOR);

		java.net.URL logoUrl = getClass().getResource("/Utils/ucvLogo.png");
		JLabel logoLabel = logoUrl != null ? new JLabel(new ImageIcon(logoUrl)) : new JLabel();
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		logoLabel.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));
		rightPanel.add(logoLabel);
		rightPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_SMALL));

		JLabel appName = new JLabel("MiComedorUCV");
		appName.setFont(TITLE_FONT);
		appName.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(appName);

		rightPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_MEDIUM));
		JLabel userIdLabel = new JLabel("Cédula de Identidad");
		userIdLabel.setFont(MAIN_FONT);
		userIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(userIdLabel);
		userIdField = new JTextField(FIELD_COLUMNS);
		userIdField.setFont(FIELD_FONT);
		userIdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, FIELD_HEIGHT));
		userIdField.addActionListener(e -> loginButton.doClick());
		rightPanel.add(userIdField);

		rightPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_SMALL));
		JLabel passwordLabel = new JLabel("Contraseña");
		passwordLabel.setFont(MAIN_FONT);
		passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(passwordLabel);
		passwordField = new JPasswordField(FIELD_COLUMNS);
		passwordField.setFont(FIELD_FONT);
		passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, FIELD_HEIGHT));
		passwordField.setEchoChar('*');
		passwordField.addActionListener(e -> loginButton.doClick());
		rightPanel.add(passwordField);

		rightPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_MEDIUM));
		loginButton = new JButton("Iniciar sesión");
		loginButton.setFont(MAIN_FONT);
		loginButton.setBackground(UIColors.ACCENT_COLOR);
		loginButton.setForeground(Color.WHITE);
		loginButton.setFocusPainted(false);
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(loginButton);

		rightPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_SMALL));
		registerButton = new JButton("Registrarse");
		registerButton.setFont(MAIN_FONT);
		registerButton.setBackground(UIColors.BG_SECONDARY_COLOR);
		registerButton.setForeground(UIColors.FG_SECONDARY_COLOR);
		registerButton.setFocusPainted(false);
		registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(registerButton);

		return rightPanel;
	}
}