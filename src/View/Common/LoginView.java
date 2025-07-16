package View.Common;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
	public JTextField userIdField;
	public JPasswordField passwordField;
	public JButton loginButton;
	public JButton registerButton;

	public LoginView() {
		createWindow();
	}

	private void createWindow() {
		setTitle("Login");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel leftPanel = createLeftPanel();
		JPanel rightPanel = createRightPanel();

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setEnabled(false);
		add(splitPane);

		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				int w = getWidth();
				double relation = 0.40; // 40% of the width for the left panel
				int divider = (int)(w * relation);
				splitPane.setDividerLocation(divider);
			}
		});
	}

	private JPanel createLeftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.WHITE);
		ImageIcon icon = new ImageIcon("Model/Utils/image.png");
		JLabel imageLabel = new JLabel(icon);
		leftPanel.add(imageLabel);
		return leftPanel;
	}

	private JPanel createRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		rightPanel.setBackground(Color.WHITE);

		JLabel appName = new JLabel("MiComedorUCV");
		appName.setFont(new Font("Arial", Font.BOLD, 22));
		appName.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(appName);

		rightPanel.add(Box.createVerticalStrut(20));
		JLabel userIdLabel = new JLabel("Cédula de Identidad:");
		userIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(userIdLabel);
		userIdField = new JTextField(20);
		userIdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		rightPanel.add(userIdField);

		rightPanel.add(Box.createVerticalStrut(10));
		JLabel passwordLabel = new JLabel("Contraseña:");
		passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(passwordLabel);
		passwordField = new JPasswordField(20);
		passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		passwordField.setEchoChar('*');
		rightPanel.add(passwordField);

		rightPanel.add(Box.createVerticalStrut(20));
		loginButton = new JButton("Iniciar sesión");
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(loginButton);

		rightPanel.add(Box.createVerticalStrut(10));
		registerButton = new JButton("Registrarse");
		registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(registerButton);

		return rightPanel;
	}
}