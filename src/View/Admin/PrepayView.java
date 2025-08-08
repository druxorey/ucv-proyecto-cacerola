package View.Admin;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import View.Common.*;


public class PrepayView extends JFrame {
	private JButton uploadButton;
	private JButton confirmButton;
	private JLabel fileLabel;
	private File selectedFile;
	private JTextField userIdField;

	public PrepayView() {
		setTitle("Pre-pago");
		setSize(400, 300); // Ajusta el tamaño para el nuevo campo
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.setBackground(CRStyles.BG_LIGHT_COLOR);

		JLabel label = CRElements.createTitleLabel("Sube tu foto para reconocimiento facial", CRStyles.FG_LIGHT_COLOR);
		label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setForeground(CRStyles.FG_LIGHT_COLOR);

		JLabel userIdLabel = new JLabel("Cédula de Identidad:");
		userIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		userIdField = new JTextField();
		userIdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		userIdField.setHorizontalAlignment(JTextField.CENTER);

		fileLabel = new JLabel("Ningún archivo seleccionado");
		fileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		uploadButton = CRElements.createButton("Subir una foto", CRStyles.BG_DARK_COLOR, Color.WHITE, false, CRStyles.BUTTON_WIDTH_MEDIUM);
		uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		uploadButton.addActionListener(_ -> {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				selectedFile = fileChooser.getSelectedFile();
				fileLabel.setText(selectedFile.getName());
			}
		});

		confirmButton = CRElements.createButton("Confirmar", CRStyles.ACCENT_COLOR, Color.WHITE, false, CRStyles.BUTTON_WIDTH_MEDIUM);
		confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(label);
		panel.add(Box.createVerticalStrut(10));
		panel.add(userIdLabel);
		panel.add(userIdField);
		panel.add(Box.createVerticalStrut(10));
		panel.add(uploadButton);
		panel.add(Box.createVerticalStrut(10));
		panel.add(fileLabel);
		panel.add(Box.createVerticalStrut(20));
		panel.add(confirmButton);

		add(panel);
	}

	public String getUserIdInput() {
		return userIdField.getText().trim();
	}

	public JButton getConfirmButton() {
		return confirmButton;
	}

	public File getSelectedFile() {
		return selectedFile;
	}
}