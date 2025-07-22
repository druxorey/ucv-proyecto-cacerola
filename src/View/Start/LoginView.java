package View.Start;

import javax.swing.*;
import java.awt.*;
import View.Common.CRStyles;
import View.Common.CRElements;

public class LoginView extends JFrame {
	public static final int LOGO_HEIGHT = 40;
	public static final int LOGO_WIDTH = 40;
	
	public JTextField userIdField;
	public JLabel registerActionLabel;
	public JPasswordField passwordField;
	public JButton loginActionButton;


	private JPanel createLeftPanel() {
		JPanel leftPanel = CRElements.createPanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);

		java.net.URL logoPath = getClass().getResource("/Utils/ucvLogo.png");
		JLabel logoLabel = logoPath != null ? new JLabel(new ImageIcon(logoPath)) : new JLabel();
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		logoLabel.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));

		JLabel loginTitle = CRElements.createTitleLabel("MiComedorUCV", CRStyles.FG_LIGHT_COLOR);
		
		JLabel loginUserIdLabel = new JLabel("Cédula de Identidad");
		loginUserIdLabel.setFont(CRStyles.MAIN_FONT);
		loginUserIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		userIdField = (JTextField) CRElements.createInputField(e -> loginActionButton.doClick());
		
		JLabel loginPasswordLabel = new JLabel("Contraseña");
		loginPasswordLabel.setFont(CRStyles.MAIN_FONT);
		loginPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordField = (JPasswordField) CRElements.createInputField(e -> loginActionButton.doClick(), true);
		
		loginActionButton = CRElements.createButton(
			"Iniciar sesión",
			CRStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			CRStyles.BUTTON_WIDTH_SMALL
		);

		registerActionLabel = new JLabel("<html>¿No tienes una cuenta? <a href='#' style='color:#eb5e28;'>Solicítala aquí</a></html>");
		registerActionLabel.setFont(CRStyles.MAIN_FONT);
		registerActionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		leftPanel.add(logoLabel);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));
		leftPanel.add(loginTitle);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(loginUserIdLabel);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));
		leftPanel.add(userIdField);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(loginPasswordLabel);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));
		leftPanel.add(passwordField);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(loginActionButton);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(registerActionLabel);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));

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
					canvasGraphics.setColor(CRStyles.BG_LIGHT_COLOR);
				}
			}
		};
	}


	public LoginView() {
		setTitle("Login");
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
		javax.swing.SwingUtilities.invokeLater(() -> {
			LoginView view = new LoginView();
			view.setVisible(true);
		});
	}
}