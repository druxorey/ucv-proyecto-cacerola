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
	public JButton loginActionButton;
	public JButton registerActionButton;	


	private JPanel createLeftPanel() {
		// Create the left panel with a vertical box layout
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING));
		leftPanel.setBackground(UIStyles.BG_PRIMARY_COLOR);

		// Add logo
		java.net.URL logoPath = getClass().getResource("/Utils/ucvLogo.png");
		JLabel logoLabel = logoPath != null ? new JLabel(new ImageIcon(logoPath)) : new JLabel();
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		logoLabel.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));

		// Add login title
		JLabel loginTitle = new JLabel("MiComedorUCV");
		loginTitle.setFont(UIStyles.TITLE_FONT);
		loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Add user ID field
		JLabel loginUserIdLabel = new JLabel("Cédula de Identidad");
		loginUserIdLabel.setFont(UIStyles.MAIN_FONT);
		loginUserIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		userIdField = (JTextField) UIElements.createInputField(false, evt -> loginActionButton.doClick());
		
		// Add password field
		JLabel loginPasswordLabel = new JLabel("Contraseña");
		loginPasswordLabel.setFont(UIStyles.MAIN_FONT);
		loginPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordField = (JPasswordField) UIElements.createInputField(true, evt -> loginActionButton.doClick());
		
		// Add login and register buttons
		loginActionButton = UIElements.createButton(
			"Iniciar sesión",
			UIStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			UIStyles.BUTTON_WIDTH_SMALL
		);
		
		registerActionButton = UIElements.createButton(
			"Registrarse",
			UIStyles.FG_SECONDARY_COLOR,
			UIStyles.BG_SECONDARY_COLOR,
			false,
			UIStyles.BUTTON_WIDTH_SMALL
		);
		
		// Add components to the left panel
		leftPanel.add(logoLabel);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_SMALL));
		leftPanel.add(loginTitle);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		leftPanel.add(loginUserIdLabel);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_SMALL));
		leftPanel.add(userIdField);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		leftPanel.add(loginPasswordLabel);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_SMALL));
		leftPanel.add(passwordField);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
		leftPanel.add(loginActionButton);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_SMALL));
		leftPanel.add(registerActionButton);
		leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_SMALL));

		return leftPanel;
	}


	private JPanel createRightPanel() {
		// Create the right panel with a background image
		return new JPanel(new BorderLayout()) {
			@Override
			// Override the paintComponent method to draw the background image
			protected void paintComponent(Graphics canvasGraphics) {
				super.paintComponent(canvasGraphics);
				java.net.URL backgroundImagePath = getClass().getResource("/Utils/loginBackground.jpg");

				if (backgroundImagePath != null) {
					ImageIcon backgroundImageIcon = new ImageIcon(backgroundImagePath);
					Image img = backgroundImageIcon.getImage();
					
					int panelWidth = getWidth();
					int panelHeight = getHeight();
					int imgWidth = img.getWidth(null);
					int imgHeight = img.getHeight(null);

					// If the image dimensions are valid, scale it to fit the panel
					if (imgWidth > 0 && imgHeight > 0) {
						// Scale the image to fit the panel while maintaining aspect ratio
						float scale = Math.max((float) panelWidth / imgWidth, (float) panelHeight / imgHeight);
						int newImgWidth = (int) (imgWidth * scale);
						int newImgHeight = (int) (imgHeight * scale);
						int x = (panelWidth - newImgWidth) / 2;
						int y = (panelHeight - newImgHeight) / 2;
						canvasGraphics.drawImage(img, x, y, newImgWidth, newImgHeight, this);
					}

				} else {
					System.err.println("[LoginView] Background image not found at path: " + backgroundImagePath);
					canvasGraphics.setColor(UIStyles.BG_PRIMARY_COLOR);
				}
			}
		};
	}


	public LoginView() {
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
		javax.swing.SwingUtilities.invokeLater(() -> {
			LoginView view = new LoginView();
			view.setVisible(true);
		});
	}
}