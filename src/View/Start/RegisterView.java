package View.Start;

import javax.swing.*;
import java.awt.*;
import View.Common.*;

public class RegisterView extends JFrame {
	public JTextField firstNameField;
	public JTextField lastNameField;
	public JTextField emailField;
	public JTextField userIdField;
	public JButton returnButton;
	public JButton submitRegistrationButton;


	public RegisterView() {
		setTitle("Registro de Usuario");
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
		JPanel leftPanel = CRElements.createImagePanel(CRStyles.PANEL_PADDING_LARGE, BoxLayout.Y_AXIS, "/Utils/background_02.jpg");

		JLabel registerTitle = new JLabel("<html>Solicitud de<br>Registro</html>");
		registerTitle.setFont(CRStyles.TITLE_FONT);
		registerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		registerTitle.setForeground(CRStyles.FG_DARK_COLOR);

		JLabel infoLabel = new JLabel("<html>Esta es una solicitud para crear una cuenta en el sistema. Una vez enviada, será revisada por el departamento de registro. Si es aprobada, recibirás un correo con la hora de tu cita para completar el registro físico.</html>");
		infoLabel.setFont(CRStyles.FIELD_FONT);
		infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoLabel.setForeground(CRStyles.FG_DARK_COLOR);

		returnButton = CRElements.createButton(
			"Regresar",
			CRStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			CRStyles.BUTTON_WIDTH_SMALL
		);
		returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(registerTitle);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		leftPanel.add(infoLabel);
		leftPanel.add(Box.createVerticalGlue());
		leftPanel.add(returnButton);
		leftPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));

		return leftPanel;
	}


	private JPanel createRightPanel() {
		JPanel rightPanel = CRElements.createBasePanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);
		
		firstNameField = (JTextField) CRElements.createInputField(null);
		lastNameField = (JTextField) CRElements.createInputField(null);
		emailField = (JTextField) CRElements.createInputField(null);
		userIdField = (JTextField) CRElements.createInputField(null);

		submitRegistrationButton = CRElements.createButton(
			"Enviar Solicitud",
			CRStyles.ACCENT_COLOR,
			Color.WHITE,
			false,
			CRStyles.BUTTON_WIDTH_SMALL
		);
		submitRegistrationButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel nameRowPanel = new JPanel();
		nameRowPanel.setLayout(new BoxLayout(nameRowPanel, BoxLayout.X_AXIS));
		nameRowPanel.setOpaque(false);
		nameRowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
		nameRowPanel.setPreferredSize(new Dimension(nameRowPanel.getPreferredSize().width, 100));

		JPanel firstNamePanel = new JPanel();
		firstNamePanel.setLayout(new BoxLayout(firstNamePanel, BoxLayout.Y_AXIS));
		firstNamePanel.setOpaque(false);
		
		JPanel lastNamePanel = new JPanel();
		lastNamePanel.setLayout(new BoxLayout(lastNamePanel, BoxLayout.Y_AXIS));
		lastNamePanel.setOpaque(false);
		
		rightPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		CRElements.createRegistrationField(firstNamePanel, "Primer Nombre", firstNameField, "<html>Ingresa tu primer nombre. Ejemplo Marcos.</html>");
		CRElements.createRegistrationField(lastNamePanel, "Primer Apellido", lastNameField, "<html>Ingresa tu primer apellido. Ejemplo: Pérez.</html>");
		nameRowPanel.add(firstNamePanel);
		nameRowPanel.add(Box.createHorizontalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		nameRowPanel.add(lastNamePanel);
		rightPanel.add(nameRowPanel);
		rightPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));
		CRElements.createRegistrationField(rightPanel, "Correo Electrónico", emailField, "<html>Debe ser un correo @gmail.com, institucional (@ucv.ve) o de facultad (@ciens.ucv.ve, etc.)</html>");
		CRElements.createRegistrationField(rightPanel, "Cédula de Identidad", userIdField, "<html>Ingrese su número de cédula sin puntos ni guiones. Ejemplo: 12345678</html>");
		rightPanel.add(Box.createVerticalGlue());
		rightPanel.add(submitRegistrationButton);
		rightPanel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));

		return rightPanel;
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			RegisterView registerView = new RegisterView();
			registerView.setVisible(true);
		});
	}
}
