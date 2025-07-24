package View.Start;

import javax.swing.*;
import java.awt.*;
import View.Common.*;

public class LoginView extends JFrame {
	public static final short LOGO_HEIGHT = 40;
	public static final short LOGO_WIDTH = 40;

	public JTextField userIdField;
	public JPasswordField passwordField;
	public JLabel registerLabel;
	public JButton loginSubmitAction;


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

	
	private JPanel createLeftPanel() {
		JPanel leftPanel = CRElements.createBasePanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);

		java.net.URL logoPath = getClass().getResource("/Utils/logo_ucv.png");
		JLabel logoLabel = logoPath != null ? new JLabel(new ImageIcon(logoPath)) : new JLabel();
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		logoLabel.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));

		JLabel loginTitle = CRElements.createTitleLabel("MiComedorUCV", CRStyles.FG_LIGHT_COLOR);
		
		JLabel loginUserIdLabel = new JLabel("Cédula de Identidad");
		loginUserIdLabel.setFont(CRStyles.MAIN_FONT);
		loginUserIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		userIdField = (JTextField) CRElements.createInputField(_ -> loginSubmitAction.doClick());
		
		JLabel loginPasswordLabel = new JLabel("Contraseña");
		loginPasswordLabel.setFont(CRStyles.MAIN_FONT);
		loginPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordField = (JPasswordField) CRElements.createPasswordField(_ -> loginSubmitAction.doClick());
		
		loginSubmitAction = CRElements.createButton(
			"Iniciar sesión",
			CRStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			CRStyles.BUTTON_WIDTH_SMALL
		);

		registerLabel = new JLabel("<html>¿No tienes una cuenta? <a href='#' style='color:#eb5e28;'>Solicítala aquí</a></html>");
		registerLabel.setFont(CRStyles.MAIN_FONT);
		registerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
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
		leftPanel.add(loginSubmitAction);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(registerLabel);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));

		return leftPanel;
	}


	private JPanel createRightPanel() {
		return CRElements.createImagePanel(CRStyles.PANEL_PADDING_LARGE, BoxLayout.Y_AXIS, "/Utils/background_01.jpg");
	}


	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			LoginView view = new LoginView();
			view.setVisible(true);
		});
	}
}