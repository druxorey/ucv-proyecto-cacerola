package View.Admin;

import javax.swing.*;
import java.awt.*;
import View.Common.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class UserRegisterView extends JFrame {
	public JTextField firstNameField;
	public JTextField lastNameField;
	public JTextField userIdField;
	public JTextField emailField;
	public JPasswordField passwordField;
	public JComboBox<String> userTypeComboBox;
	public JButton addUserButton;
	public JButton uploadPhotoButton;
	public JLabel photoFileLabel;
	public File selectedPhotoFile;


	public UserRegisterView(String firstName, String lastName, String userId, String email) {
		setTitle("Registrar Usuario");
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = CRElements.createBasePanel(CRStyles.BG_LIGHT_COLOR, BoxLayout.Y_AXIS);

		JLabel userLabel = new JLabel("Gestión de Usuarios");
		userLabel.setFont(CRStyles.TITLE_FONT);
		userLabel.setForeground(CRStyles.FG_LIGHT_COLOR);
		userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		firstNameField = (JTextField) CRElements.createInputField(null);
		lastNameField = (JTextField) CRElements.createInputField(null);
		userIdField = (JTextField) CRElements.createInputField(null);
		emailField = (JTextField) CRElements.createInputField(null);
		passwordField = (JPasswordField) CRElements.createPasswordField(null);

		firstNameField.setText(firstName);
		lastNameField.setText(lastName);
		userIdField.setText(userId);
		emailField.setText(email);

		String[] userTypes = {"Administrador", "Estudiante", "Profesor", "Empleado"};
		userTypeComboBox = new JComboBox<>(userTypes);
		userTypeComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, CRStyles.FIELD_HEIGHT));
		userTypeComboBox.setBackground(CRStyles.BG_LIGHT_COLOR);
		userTypeComboBox.setFont(CRStyles.FIELD_FONT);
		userTypeComboBox.setBorder(BorderFactory.createLineBorder(CRStyles.FG_DARK_COLOR, 2));

		addUserButton = CRElements.createButton("Agregar", CRStyles.ACCENT_COLOR, Color.WHITE, false, 120);

		panel.add(userLabel);
		panel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		addField(panel, "Nombre", firstNameField, "<html>Nombre del usuario a registrar.</html>");
		addField(panel, "Apellido", lastNameField, "<html>Apellido del usuario a registrar.</html>");
		addField(panel, "Cédula de Identidad", userIdField, "<html>Cédula o identificador único.</html>");
		addField(panel, "Email", emailField, "<html>Correo institucional o personal válido.</html>");
		addField(panel, "Contraseña", passwordField, "<html>Debe tener al menos 8 caracteres, incluir letras y números.</html>");
		addField(panel, "Tipo de Usuario", userTypeComboBox, "<html>Selecciona el tipo de usuario.</html>");
		panel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
		addUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		uploadPhotoButton = CRElements.createButton("Subir Foto", CRStyles.BG_DARK_COLOR, Color.WHITE, false, 120);
		uploadPhotoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		photoFileLabel = new JLabel("Ningún archivo seleccionado");
		photoFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		uploadPhotoButton.addActionListener(_ -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes JPG y PNG", "jpg", "jpeg", "png"));
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				selectedPhotoFile = fileChooser.getSelectedFile();
				photoFileLabel.setText(selectedPhotoFile.getName());
			}
		});

		addField(panel, "Foto de Rostro", uploadPhotoButton, "");
		panel.add(photoFileLabel);
		panel.add(Box.createVerticalStrut(30)); 
		panel.add(addUserButton);
		add(panel);
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
}