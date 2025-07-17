package View.Start;

import javax.swing.*;
import java.awt.*;
import View.Common.UIStyles;
import View.Common.UIElements;

public class LoginView extends JFrame {
	public static final int LOGO_HEIGHT = 40;
	public static final int LOGO_WIDTH = 40;
	
	public JTextField userIdField;
	public JPasswordField passwordField;
	public JButton loginButton;
	public JButton registerButton;	

	public LoginView() {
		createWindow();
	}

	private void createWindow() {
		setTitle("Login");
		setSize(UIStyles.WINDOW_WIDTH_LOGIN, UIStyles.WINDOW_HEIGHT_LOGIN);
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
			divider.setBackground(UIStyles.DEBUG_COLOR);
		}
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

	private JPanel createRightPanel() {
		JPanel rightPanel = new JPanel(new BorderLayout()) {
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
		rightPanel.setBackground(Color.RED);
		return rightPanel;
	}

	private JPanel createLeftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING));
		leftPanel.setBackground(UIStyles.BG_PRIMARY_COLOR);

		java.net.URL logoUrl = getClass().getResource("/Utils/ucvLogo.png");
		JLabel logoLabel = logoUrl != null ? new JLabel(new ImageIcon(logoUrl)) : new JLabel();
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		logoLabel.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));
		leftPanel.add(logoLabel);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_SMALL));

		JLabel appName = new JLabel("MiComedorUCV");
		appName.setFont(UIStyles.TITLE_FONT);
		appName.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftPanel.add(appName);

		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		JLabel userIdLabel = new JLabel("Cédula de Identidad");
		userIdLabel.setFont(UIStyles.MAIN_FONT);
		userIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftPanel.add(userIdLabel);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_SMALL));

		userIdField = (JTextField) UIElements.createInputField(false, evt -> loginButton.doClick());
		leftPanel.add(userIdField);

		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_SMALL));
		JLabel passwordLabel = new JLabel("Contraseña");
		passwordLabel.setFont(UIStyles.MAIN_FONT);
		passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftPanel.add(passwordLabel);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_SMALL));
		
		passwordField = (JPasswordField) UIElements.createInputField(true, evt -> loginButton.doClick());
		leftPanel.add(passwordField);

		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		loginButton = UIElements.createStyledButton(
			"Iniciar sesión",
			UIStyles.ACCENT_COLOR,
			Color.WHITE,
			false
		);
		leftPanel.add(loginButton);

		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_SMALL));
		registerButton = UIElements.createStyledButton(
			"Registrarse",
			UIStyles.FG_SECONDARY_COLOR,
			UIStyles.BG_SECONDARY_COLOR,
			false
		);
		leftPanel.add(registerButton);

		return leftPanel;
	}
}