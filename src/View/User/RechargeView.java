package View.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class RechargeView extends JFrame {
	private JTextField transactionField;
	private JButton confirmButton;
	private JButton cancelButton;
	private JLabel infoLabel;

	public RechargeView(String userId, java.util.function.Consumer<Double> onSuccess) {
		setTitle("Confirmar Recarga");
		setSize(400, 220);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.setBackground(View.Common.CRStyles.BG_LIGHT_COLOR);

		JLabel label = new JLabel("Ingrese el número de transacción (16 dígitos):");
		label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);

		transactionField = new JTextField();
		transactionField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		transactionField.setHorizontalAlignment(JTextField.CENTER);

		infoLabel = new JLabel("");
		infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
		infoLabel.setForeground(Color.RED);
		infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		confirmButton = new JButton("Confirmar");
		confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		cancelButton = new JButton("Cancelar");
		cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(label);
		panel.add(Box.createVerticalStrut(10));
		panel.add(transactionField);
		panel.add(Box.createVerticalStrut(10));
		panel.add(infoLabel);
		panel.add(Box.createVerticalStrut(10));
		panel.add(confirmButton);
		panel.add(Box.createVerticalStrut(5));
		panel.add(cancelButton);

		add(panel);

		confirmButton.addActionListener(_ -> {
			String tx = transactionField.getText().trim();
			if (!tx.matches("\\d{16}")) {
				infoLabel.setText("El número debe tener 16 dígitos.");
				return;
			}
			// Busca el archivo en la carpeta de recibos
			File receipt = new File("Model/Data/Receipts/" + tx + ".txt");
			if (!receipt.exists()) {
				JOptionPane.showMessageDialog(this, "No se encontró un recibo con ese número.", "Recarga no confirmada", JOptionPane.ERROR_MESSAGE);
				dispose();
				return;
			}
			try (BufferedReader br = new BufferedReader(new FileReader(receipt))) {
				String line = br.readLine();
				double amount = Double.parseDouble(line.trim());
				onSuccess.accept(amount);
				JOptionPane.showMessageDialog(this, "Recarga exitosa. Monto: Bs. " + String.format("%.2f", amount), "Recarga confirmada", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al leer el recibo.", "Error", JOptionPane.ERROR_MESSAGE);
				dispose();
			}
		});

		cancelButton.addActionListener(_ -> dispose());
	}
}